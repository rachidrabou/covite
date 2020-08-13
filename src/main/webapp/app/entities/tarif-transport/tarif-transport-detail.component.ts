import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

@Component({
  selector: 'jhi-tarif-transport-detail',
  templateUrl: './tarif-transport-detail.component.html'
})
export class TarifTransportDetailComponent implements OnInit {
  tarifTransport: ITarifTransport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransport }) => (this.tarifTransport = tarifTransport));
  }

  previousState(): void {
    window.history.back();
  }
}
