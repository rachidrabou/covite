import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifTransportAnimal, TarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';
import { TarifTransportAnimalService } from './tarif-transport-animal.service';

@Component({
  selector: 'jhi-tarif-transport-animal-update',
  templateUrl: './tarif-transport-animal-update.component.html'
})
export class TarifTransportAnimalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    service: [],
    animal: [],
    distance: [],
    prix: []
  });

  constructor(
    protected tarifTransportAnimalService: TarifTransportAnimalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransportAnimal }) => {
      this.updateForm(tarifTransportAnimal);
    });
  }

  updateForm(tarifTransportAnimal: ITarifTransportAnimal): void {
    this.editForm.patchValue({
      id: tarifTransportAnimal.id,
      service: tarifTransportAnimal.service,
      animal: tarifTransportAnimal.animal,
      distance: tarifTransportAnimal.distance,
      prix: tarifTransportAnimal.prix
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifTransportAnimal = this.createFromForm();
    if (tarifTransportAnimal.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifTransportAnimalService.update(tarifTransportAnimal));
    } else {
      this.subscribeToSaveResponse(this.tarifTransportAnimalService.create(tarifTransportAnimal));
    }
  }

  private createFromForm(): ITarifTransportAnimal {
    return {
      ...new TarifTransportAnimal(),
      id: this.editForm.get(['id'])!.value,
      service: this.editForm.get(['service'])!.value,
      animal: this.editForm.get(['animal'])!.value,
      distance: this.editForm.get(['distance'])!.value,
      prix: this.editForm.get(['prix'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifTransportAnimal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
