import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeTransport } from 'app/shared/model/commande-transport.model';

@Component({
  selector: 'jhi-commande-transport-detail',
  templateUrl: './commande-transport-detail.component.html'
})
export class CommandeTransportDetailComponent implements OnInit {
  commandeTransport: ICommandeTransport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeTransport }) => (this.commandeTransport = commandeTransport));
  }

  previousState(): void {
    window.history.back();
  }
}
