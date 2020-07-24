import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandes, Commandes } from 'app/shared/model/commandes.model';
import { CommandesService } from './commandes.service';

@Component({
  selector: 'jhi-commandes-update',
  templateUrl: './commandes-update.component.html'
})
export class CommandesUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    prix: [],
    type: [],
    typeservice: []
  });

  constructor(protected commandesService: CommandesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandes }) => {
      this.updateForm(commandes);
    });
  }

  updateForm(commandes: ICommandes): void {
    this.editForm.patchValue({
      id: commandes.id,
      date: commandes.date,
      prix: commandes.prix,
      type: commandes.type,
      typeservice: commandes.typeservice
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandes = this.createFromForm();
    if (commandes.id !== undefined) {
      this.subscribeToSaveResponse(this.commandesService.update(commandes));
    } else {
      this.subscribeToSaveResponse(this.commandesService.create(commandes));
    }
  }

  private createFromForm(): ICommandes {
    return {
      ...new Commandes(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      type: this.editForm.get(['type'])!.value,
      typeservice: this.editForm.get(['typeservice'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandes>>): void {
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
