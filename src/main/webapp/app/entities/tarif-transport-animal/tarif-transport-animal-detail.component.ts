import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

@Component({
  selector: 'jhi-tarif-transport-animal-detail',
  templateUrl: './tarif-transport-animal-detail.component.html'
})
export class TarifTransportAnimalDetailComponent implements OnInit {
  tarifTransportAnimal: ITarifTransportAnimal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransportAnimal }) => (this.tarifTransportAnimal = tarifTransportAnimal));
  }

  previousState(): void {
    window.history.back();
  }
}
