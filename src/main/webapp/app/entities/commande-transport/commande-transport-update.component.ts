import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandeTransport, CommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from './commande-transport.service';

@Component({
  selector: 'jhi-commande-transport-update',
  templateUrl: './commande-transport-update.component.html'
})
export class CommandeTransportUpdateComponent implements OnInit {
  isSaving = false;
  dateHeureDp: any;

  editForm = this.fb.group({
    id: [],
    adresseDepart: [null, [Validators.required]],
    adresseArrivee: [null, [Validators.required]],
    dateHeure: [null, [Validators.required]],
    moyenDeTransport: [],
    prix: [],
    nombreDePersonnes: [],
    numeroClient: []
  });

  constructor(
    protected commandeTransportService: CommandeTransportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeTransport }) => {
      this.updateForm(commandeTransport);
    });
  }

  updateForm(commandeTransport: ICommandeTransport): void {
    this.editForm.patchValue({
      id: commandeTransport.id,
      adresseDepart: commandeTransport.adresseDepart,
      adresseArrivee: commandeTransport.adresseArrivee,
      dateHeure: commandeTransport.dateHeure,
      moyenDeTransport: commandeTransport.moyenDeTransport,
      prix: commandeTransport.prix,
      nombreDePersonnes: commandeTransport.nombreDePersonnes,
      numeroClient: commandeTransport.numeroClient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeTransport = this.createFromForm();
    if (commandeTransport.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeTransportService.update(commandeTransport));
    } else {
      this.subscribeToSaveResponse(this.commandeTransportService.create(commandeTransport));
    }
  }

  private createFromForm(): ICommandeTransport {
    return {
      ...new CommandeTransport(),
      id: this.editForm.get(['id'])!.value,
      adresseDepart: this.editForm.get(['adresseDepart'])!.value,
      adresseArrivee: this.editForm.get(['adresseArrivee'])!.value,
      dateHeure: this.editForm.get(['dateHeure'])!.value,
      moyenDeTransport: this.editForm.get(['moyenDeTransport'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      nombreDePersonnes: this.editForm.get(['nombreDePersonnes'])!.value,
      numeroClient: this.editForm.get(['numeroClient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeTransport>>): void {
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
