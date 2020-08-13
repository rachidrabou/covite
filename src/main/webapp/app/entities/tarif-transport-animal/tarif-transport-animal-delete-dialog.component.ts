import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';
import { TarifTransportAnimalService } from './tarif-transport-animal.service';

@Component({
  templateUrl: './tarif-transport-animal-delete-dialog.component.html'
})
export class TarifTransportAnimalDeleteDialogComponent {
  tarifTransportAnimal?: ITarifTransportAnimal;

  constructor(
    protected tarifTransportAnimalService: TarifTransportAnimalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tarifTransportAnimalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tarifTransportAnimalListModification');
      this.activeModal.close();
    });
  }
}
