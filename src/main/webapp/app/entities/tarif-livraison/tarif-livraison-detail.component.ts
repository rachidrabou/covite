import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifLivraison } from 'app/shared/model/tarif-livraison.model';

@Component({
  selector: 'jhi-tarif-livraison-detail',
  templateUrl: './tarif-livraison-detail.component.html'
})
export class TarifLivraisonDetailComponent implements OnInit {
  tarifLivraison: ITarifLivraison | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifLivraison }) => (this.tarifLivraison = tarifLivraison));
  }

  previousState(): void {
    window.history.back();
  }
}
