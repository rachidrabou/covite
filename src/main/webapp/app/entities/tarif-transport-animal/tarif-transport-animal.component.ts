import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifTransportAnimalService } from './tarif-transport-animal.service';
import { TarifTransportAnimalDeleteDialogComponent } from './tarif-transport-animal-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-transport-animal',
  templateUrl: './tarif-transport-animal.component.html'
})
export class TarifTransportAnimalComponent implements OnInit, OnDestroy {
  tarifTransportAnimals: ITarifTransportAnimal[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tarifTransportAnimalService: TarifTransportAnimalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifTransportAnimals = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tarifTransportAnimalService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifTransportAnimal[]>) => this.paginateTarifTransportAnimals(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tarifTransportAnimals = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTarifTransportAnimals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITarifTransportAnimal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTarifTransportAnimals(): void {
    this.eventSubscriber = this.eventManager.subscribe('tarifTransportAnimalListModification', () => this.reset());
  }

  delete(tarifTransportAnimal: ITarifTransportAnimal): void {
    const modalRef = this.modalService.open(TarifTransportAnimalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifTransportAnimal = tarifTransportAnimal;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifTransportAnimals(data: ITarifTransportAnimal[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tarifTransportAnimals.push(data[i]);
      }
    }
  }
}
