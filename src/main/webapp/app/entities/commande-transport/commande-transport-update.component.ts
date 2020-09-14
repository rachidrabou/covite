import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommandeTransport, CommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from './commande-transport.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-commande-transport-update',
  templateUrl: './commande-transport-update.component.html'
})
export class CommandeTransportUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    adresseDepart: [null, [Validators.required]],
    adresseArrivee: [null, [Validators.required]],
    moyenDeTransport: [],
    prix: [],
    nombreDePersonnes: [],
    numeroClient: [],
    dateheure: [],
    client: [],
    livreur: []
  });

  constructor(
    protected commandeTransportService: CommandeTransportService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeTransport }) => {
      if (!commandeTransport.id) {
        const today = moment().startOf('day');
        commandeTransport.dateheure = today;
      }

      this.updateForm(commandeTransport);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(commandeTransport: ICommandeTransport): void {
    this.editForm.patchValue({
      id: commandeTransport.id,
      adresseDepart: commandeTransport.adresseDepart,
      adresseArrivee: commandeTransport.adresseArrivee,
      moyenDeTransport: commandeTransport.moyenDeTransport,
      prix: commandeTransport.prix,
      nombreDePersonnes: commandeTransport.nombreDePersonnes,
      numeroClient: commandeTransport.numeroClient,
      dateheure: commandeTransport.dateheure ? commandeTransport.dateheure.format(DATE_TIME_FORMAT) : null,
      client: commandeTransport.client,
      livreur: commandeTransport.livreur
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
      moyenDeTransport: this.editForm.get(['moyenDeTransport'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      nombreDePersonnes: this.editForm.get(['nombreDePersonnes'])!.value,
      numeroClient: this.editForm.get(['numeroClient'])!.value,
      dateheure: this.editForm.get(['dateheure'])!.value ? moment(this.editForm.get(['dateheure'])!.value, DATE_TIME_FORMAT) : undefined,
      client: this.editForm.get(['client'])!.value,
      livreur: this.editForm.get(['livreur'])!.value
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
