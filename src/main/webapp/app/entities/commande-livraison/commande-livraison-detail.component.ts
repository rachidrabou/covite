import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';

@Component({
  selector: 'jhi-commande-livraison-detail',
  templateUrl: './commande-livraison-detail.component.html'
})
export class CommandeLivraisonDetailComponent implements OnInit {
  commandeLivraison: ICommandeLivraison | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLivraison }) => (this.commandeLivraison = commandeLivraison));
  }

  previousState(): void {
    window.history.back();
  }
}
