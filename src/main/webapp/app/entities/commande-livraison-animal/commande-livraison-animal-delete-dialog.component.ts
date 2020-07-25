import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { CommandeLivraisonAnimalService } from './commande-livraison-animal.service';

@Component({
  templateUrl: './commande-livraison-animal-delete-dialog.component.html'
})
export class CommandeLivraisonAnimalDeleteDialogComponent {
  commandeLivraisonAnimal?: ICommandeLivraisonAnimal;

  constructor(
    protected commandeLivraisonAnimalService: CommandeLivraisonAnimalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeLivraisonAnimalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandeLivraisonAnimalListModification');
      this.activeModal.close();
    });
  }
}
