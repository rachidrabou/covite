import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandeLivraison, CommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from './commande-livraison.service';

@Component({
  selector: 'jhi-commande-livraison-update',
  templateUrl: './commande-livraison-update.component.html'
})
export class CommandeLivraisonUpdateComponent implements OnInit {
  isSaving = false;
  dateHeureDp: any;

  editForm = this.fb.group({
    id: [],
    adresseDepart: [null, [Validators.required]],
    adresseArrivee: [null, [Validators.required]],
    dateHeure: [null, [Validators.required]],
    prix: [],
    numeroClient: [],
    objet: [null, [Validators.required]]
  });

  constructor(
    protected commandeLivraisonService: CommandeLivraisonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLivraison }) => {
      this.updateForm(commandeLivraison);
    });
  }

  updateForm(commandeLivraison: ICommandeLivraison): void {
    this.editForm.patchValue({
      id: commandeLivraison.id,
      adresseDepart: commandeLivraison.adresseDepart,
      adresseArrivee: commandeLivraison.adresseArrivee,
      dateHeure: commandeLivraison.dateHeure,
      prix: commandeLivraison.prix,
      numeroClient: commandeLivraison.numeroClient,
      objet: commandeLivraison.objet
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeLivraison = this.createFromForm();
    if (commandeLivraison.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeLivraisonService.update(commandeLivraison));
    } else {
      this.subscribeToSaveResponse(this.commandeLivraisonService.create(commandeLivraison));
    }
  }

  private createFromForm(): ICommandeLivraison {
    return {
      ...new CommandeLivraison(),
      id: this.editForm.get(['id'])!.value,
      adresseDepart: this.editForm.get(['adresseDepart'])!.value,
      adresseArrivee: this.editForm.get(['adresseArrivee'])!.value,
      dateHeure: this.editForm.get(['dateHeure'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      numeroClient: this.editForm.get(['numeroClient'])!.value,
      objet: this.editForm.get(['objet'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeLivraison>>): void {
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
