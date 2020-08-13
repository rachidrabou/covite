import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifTransportService } from './tarif-transport.service';
import { TarifTransportDeleteDialogComponent } from './tarif-transport-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-transport',
  templateUrl: './tarif-transport.component.html'
})
export class TarifTransportComponent implements OnInit, OnDestroy {
  tarifTransports: ITarifTransport[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tarifTransportService: TarifTransportService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifTransports = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tarifTransportService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifTransport[]>) => this.paginateTarifTransports(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tarifTransports = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifTransports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifTransport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifTransports(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifTransportListModification', () => this.reset());
  }

  delete(tarifTransport: ITarifTransport): void {
    const modalRef = this.modalService.open(TarifTransportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifTransport = tarifTransport;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifTransports(data: ITarifTransport[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tarifTransports.push(data[i]);
      }
    }
  }
}
