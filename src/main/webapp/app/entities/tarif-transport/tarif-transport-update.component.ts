import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifTransport, TarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';

@Component({
  selector: 'jhi-tarif-transport-update',
  templateUrl: './tarif-transport-update.component.html'
})
export class TarifTransportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    service: [],
    vehicule: [],
    nombreDePersonne: [],
    distance: [],
    prix: []
  });

  constructor(protected tarifTransportService: TarifTransportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifTransport }) => {
      this.updateForm(tarifTransport);
    });
  }

  updateForm(tarifTransport: ITarifTransport): void {
    this.editForm.patchValue({
      id: tarifTransport.id,
      service: tarifTransport.service,
      vehicule: tarifTransport.vehicule,
      nombreDePersonne: tarifTransport.nombreDePersonne,
      distance: tarifTransport.distance,
      prix: tarifTransport.prix
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifTransport = this.createFromForm();
    if (tarifTransport.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifTransportService.update(tarifTransport));
    } else {
      this.subscribeToSaveResponse(this.tarifTransportService.create(tarifTransport));
    }
  }

  private createFromForm(): ITarifTransport {
    return {
      ...new TarifTransport(),
      id: this.editForm.get(['id'])!.value,
      service: this.editForm.get(['service'])!.value,
      vehicule: this.editForm.get(['vehicule'])!.value,
      nombreDePersonne: this.editForm.get(['nombreDePersonne'])!.value,
      distance: this.editForm.get(['distance'])!.value,
      prix: this.editForm.get(['prix'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifTransport>>): void {
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
