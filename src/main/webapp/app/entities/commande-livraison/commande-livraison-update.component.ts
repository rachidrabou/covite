import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommandeLivraison, CommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from './commande-livraison.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-commande-livraison-update',
  templateUrl: './commande-livraison-update.component.html'
})
export class CommandeLivraisonUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    adresseDepart: [null, [Validators.required]],
    adresseArrivee: [null, [Validators.required]],
    prix: [],
    numeroClient: [],
    objet: [null, [Validators.required]],
    cin: [],
    dateheure: [],
    cvalider: [],
    client: [],
    livreur: []
  });

  constructor(
    protected commandeLivraisonService: CommandeLivraisonService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLivraison }) => {
      if (!commandeLivraison.id) {
        const today = moment().startOf('day');
        commandeLivraison.dateheure = today;
      }

      this.updateForm(commandeLivraison);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(commandeLivraison: ICommandeLivraison): void {
    this.editForm.patchValue({
      id: commandeLivraison.id,
      adresseDepart: commandeLivraison.adresseDepart,
      adresseArrivee: commandeLivraison.adresseArrivee,
      prix: commandeLivraison.prix,
      numeroClient: commandeLivraison.numeroClient,
      objet: commandeLivraison.objet,
      cin: commandeLivraison.cin,
      dateheure: commandeLivraison.dateheure ? commandeLivraison.dateheure.format(DATE_TIME_FORMAT) : null,
      cvalider: commandeLivraison.cvalider,
      client: commandeLivraison.client,
      livreur: commandeLivraison.livreur
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
      prix: this.editForm.get(['prix'])!.value,
      numeroClient: this.editForm.get(['numeroClient'])!.value,
      objet: this.editForm.get(['objet'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      dateheure: this.editForm.get(['dateheure'])!.value ? moment(this.editForm.get(['dateheure'])!.value, DATE_TIME_FORMAT) : undefined,
      cvalider: this.editForm.get(['cvalider'])!.value,
      client: this.editForm.get(['client'])!.value,
      livreur: this.editForm.get(['livreur'])!.value
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
