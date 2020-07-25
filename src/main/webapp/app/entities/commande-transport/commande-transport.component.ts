import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeTransport } from 'app/shared/model/commande-transport.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommandeTransportService } from './commande-transport.service';
import { CommandeTransportDeleteDialogComponent } from './commande-transport-delete-dialog.component';

@Component({
  selector: 'jhi-commande-transport',
  templateUrl: './commande-transport.component.html'
})
export class CommandeTransportComponent implements OnInit, OnDestroy {
  commandeTransports?: ICommandeTransport[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected commandeTransportService: CommandeTransportService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.commandeTransportService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICommandeTransport[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInCommandeTransports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommandeTransport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommandeTransports(): void {
    this.eventSubscriber = this.eventManager.subscribe('commandeTransportListModification', () => this.loadPage());
  }

  delete(commandeTransport: ICommandeTransport): void {
    const modalRef = this.modalService.open(CommandeTransportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeTransport = commandeTransport;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICommandeTransport[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/commande-transport'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.commandeTransports = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
