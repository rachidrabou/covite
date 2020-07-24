import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandes } from 'app/shared/model/commandes.model';
import { CommandesService } from './commandes.service';

@Component({
  templateUrl: './commandes-delete-dialog.component.html'
})
export class CommandesDeleteDialogComponent {
  commandes?: ICommandes;

  constructor(protected commandesService: CommandesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandesListModification');
      this.activeModal.close();
    });
  }
}
