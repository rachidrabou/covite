import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivreur } from 'app/shared/model/livreur.model';
import { LivreurService } from './livreur.service';

@Component({
  templateUrl: './livreur-delete-dialog.component.html'
})
export class LivreurDeleteDialogComponent {
  livreur?: ILivreur;

  constructor(protected livreurService: LivreurService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livreurService.delete(id).subscribe(() => {
      this.eventManager.broadcast('livreurListModification');
      this.activeModal.close();
    });
  }
}
