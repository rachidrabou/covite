import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';

@Component({
  selector: 'jhi-commande-livraison-animal-detail',
  templateUrl: './commande-livraison-animal-detail.component.html'
})
export class CommandeLivraisonAnimalDetailComponent implements OnInit {
  commandeLivraisonAnimal: ICommandeLivraisonAnimal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLivraisonAnimal }) => (this.commandeLivraisonAnimal = commandeLivraisonAnimal));
  }

  previousState(): void {
    window.history.back();
  }
}
