import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

import { ICommandeTransport, CommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from './commande-transport.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-commande-transport-update',
  templateUrl: './commande-transport-update.component.html'
})
export class CommandeTransportUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  dateHeureDp: any;

  account!: Account;
  authSubscription?: Subscription;

  editForm = this.fb.group({
    id: [],
    adresseDepart: [null, [Validators.required]],
    adresseArrivee: [null, [Validators.required]],
    dateHeure: [null, [Validators.required]],
    moyenDeTransport: [],
    prix: [],
    nombreDePersonnes: [],
    numeroClient: [],
    validated: [],
    client: [],
    livreur: []
  });

  constructor(
    protected commandeTransportService: CommandeTransportService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeTransport }) => {
      this.updateForm(commandeTransport);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });

    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account as Account));
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
      numeroClient: commandeTransport.numeroClient,
      validated: commandeTransport.validated,
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

    commandeTransport.client = this.account;

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
      numeroClient: this.editForm.get(['numeroClient'])!.value,
      validated: this.editForm.get(['validated'])!.value,
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
