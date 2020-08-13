import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifLivraison } from 'app/shared/model/tarif-livraison.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifLivraisonService } from './tarif-livraison.service';
import { TarifLivraisonDeleteDialogComponent } from './tarif-livraison-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-livraison',
  templateUrl: './tarif-livraison.component.html'
})
export class TarifLivraisonComponent implements OnInit, OnDestroy {
  tarifLivraisons: ITarifLivraison[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tarifLivraisonService: TarifLivraisonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifLivraisons = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tarifLivraisonService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifLivraison[]>) => this.paginateTarifLivraisons(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tarifLivraisons = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifLivraisons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifLivraison): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifLivraisons(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifLivraisonListModification', () => this.reset());
  }

  delete(tarifLivraison: ITarifLivraison): void {
    const modalRef = this.modalService.open(TarifLivraisonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifLivraison = tarifLivraison;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifLivraisons(data: ITarifLivraison[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tarifLivraisons.push(data[i]);
      }
    }
  }
}
