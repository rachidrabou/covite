import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifLivraison } from 'app/shared/model/tarif-livraison.model';
import { TarifLivraisonService } from './tarif-livraison.service';

@Component({
  templateUrl: './tarif-livraison-delete-dialog.component.html'
})
export class TarifLivraisonDeleteDialogComponent {
  tarifLivraison?: ITarifLivraison;

  constructor(
    protected tarifLivraisonService: TarifLivraisonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifLivraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifLivraisonListModification');
      this.activeModal.close();
    });
  }
}
