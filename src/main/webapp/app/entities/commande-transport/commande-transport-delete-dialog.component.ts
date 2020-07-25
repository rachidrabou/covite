import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from './commande-transport.service';

@Component({
  templateUrl: './commande-transport-delete-dialog.component.html'
})
export class CommandeTransportDeleteDialogComponent {
  commandeTransport?: ICommandeTransport;

  constructor(
    protected commandeTransportService: CommandeTransportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeTransportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandeTransportListModification');
      this.activeModal.close();
    });
  }
}
