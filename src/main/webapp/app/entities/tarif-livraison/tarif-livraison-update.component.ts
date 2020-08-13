import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarifLivraison, TarifLivraison } from 'app/shared/model/tarif-livraison.model';
import { TarifLivraisonService } from './tarif-livraison.service';

@Component({
  selector: 'jhi-tarif-livraison-update',
  templateUrl: './tarif-livraison-update.component.html'
})
export class TarifLivraisonUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    service: [],
    objet: [],
    distance: [],
    prix: []
  });

  constructor(protected tarifLivraisonService: TarifLivraisonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarifLivraison }) => {
      this.updateForm(tarifLivraison);
    });
  }

  updateForm(tarifLivraison: ITarifLivraison): void {
    this.editForm.patchValue({
      id: tarifLivraison.id,
      service: tarifLivraison.service,
      objet: tarifLivraison.objet,
      distance: tarifLivraison.distance,
      prix: tarifLivraison.prix
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarifLivraison = this.createFromForm();
    if (tarifLivraison.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifLivraisonService.update(tarifLivraison));
    } else {
      this.subscribeToSaveResponse(this.tarifLivraisonService.create(tarifLivraison));
    }
  }

  private createFromForm(): ITarifLivraison {
    return {
      ...new TarifLivraison(),
      id: this.editForm.get(['id'])!.value,
      service: this.editForm.get(['service'])!.value,
      objet: this.editForm.get(['objet'])!.value,
      distance: this.editForm.get(['distance'])!.value,
      prix: this.editForm.get(['prix'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarifLivraison>>): void {
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
