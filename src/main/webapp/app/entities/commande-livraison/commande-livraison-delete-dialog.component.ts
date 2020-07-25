import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from './commande-livraison.service';

@Component({
  templateUrl: './commande-livraison-delete-dialog.component.html'
})
export class CommandeLivraisonDeleteDialogComponent {
  commandeLivraison?: ICommandeLivraison;

  constructor(
    protected commandeLivraisonService: CommandeLivraisonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeLivraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandeLivraisonListModification');
      this.activeModal.close();
    });
  }
}
