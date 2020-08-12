import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivreur } from 'app/shared/model/livreur.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LivreurService } from './livreur.service';
import { LivreurDeleteDialogComponent } from './livreur-delete-dialog.component';

@Component({
  selector: 'jhi-livreur',
  templateUrl: './livreur.component.html'
})
export class LivreurComponent implements OnInit, OnDestroy {
  livreurs: ILivreur[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected livreurService: LivreurService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.livreurs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.livreurService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILivreur[]>) => this.paginateLivreurs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.livreurs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLivreurs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILivreur): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLivreurs(): void {
    this.eventSubscriber = this.eventManager.subscribe('livreurListModification', () => this.reset());
  }

  delete(livreur: ILivreur): void {
    const modalRef = this.modalService.open(LivreurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livreur = livreur;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLivreurs(data: ILivreur[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.livreurs.push(data[i]);
      }
    }
  }
}
