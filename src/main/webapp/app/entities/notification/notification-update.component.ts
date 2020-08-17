import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { map } from 'rxjs/operators';

import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';
import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { CommandeLivraisonAnimalService } from 'app/entities/commande-livraison-animal/commande-livraison-animal.service';
import { ICommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from 'app/entities/commande-transport/commande-transport.service';

import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

type SelectableEntity = IUser | ICommandeLivraison | ICommandeLivraisonAnimal | ICommandeTransport;

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  commandelivraisons: ICommandeLivraison[] = [];
  commandelivraisonanimals: ICommandeLivraisonAnimal[] = [];
  commandetransports: ICommandeTransport[] = [];

  account!: Account;
  authSubscription?: Subscription;

  editForm = this.fb.group({
    id: [],
    titre: [],
    prix: [],
    prixValider: [],
    client: [],
    commandeLivraison: [],
    commandeLivraisonAnimal: [],
    commandeTransport: [],
    livreur: []
  });

  constructor(
    protected notificationService: NotificationService,
    protected userService: UserService,
    protected commandeLivraisonService: CommandeLivraisonService,
    protected commandeLivraisonAnimalService: CommandeLivraisonAnimalService,
    protected commandeTransportService: CommandeTransportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      this.updateForm(notification);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.commandeLivraisonService
        .query({ filter: 'notification-is-null' })
        .pipe(
          map((res: HttpResponse<ICommandeLivraison[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICommandeLivraison[]) => {
          if (!notification.commandeLivraison || !notification.commandeLivraison.id) {
            this.commandelivraisons = resBody;
          } else {
            this.commandeLivraisonService
              .find(notification.commandeLivraison.id)
              .pipe(
                map((subRes: HttpResponse<ICommandeLivraison>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICommandeLivraison[]) => (this.commandelivraisons = concatRes));
          }
        });

      this.commandeLivraisonAnimalService
        .query({ filter: 'notification-is-null' })
        .pipe(
          map((res: HttpResponse<ICommandeLivraisonAnimal[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICommandeLivraisonAnimal[]) => {
          if (!notification.commandeLivraisonAnimal || !notification.commandeLivraisonAnimal.id) {
            this.commandelivraisonanimals = resBody;
          } else {
            this.commandeLivraisonAnimalService
              .find(notification.commandeLivraisonAnimal.id)
              .pipe(
                map((subRes: HttpResponse<ICommandeLivraisonAnimal>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICommandeLivraisonAnimal[]) => (this.commandelivraisonanimals = concatRes));
          }
        });

      this.commandeTransportService
        .query({ filter: 'notification-is-null' })
        .pipe(
          map((res: HttpResponse<ICommandeTransport[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICommandeTransport[]) => {
          if (!notification.commandeTransport || !notification.commandeTransport.id) {
            this.commandetransports = resBody;
          } else {
            this.commandeTransportService
              .find(notification.commandeTransport.id)
              .pipe(
                map((subRes: HttpResponse<ICommandeTransport>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICommandeTransport[]) => (this.commandetransports = concatRes));
          }
        });
    });

    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account as Account));
  }

  updateForm(notification: INotification): void {
    this.editForm.patchValue({
      id: notification.id,
      titre: notification.titre,
      prix: notification.prix,
      prixValider: notification.prixValider,
      client: notification.client,
      commandeLivraison: notification.commandeLivraison,
      commandeLivraisonAnimal: notification.commandeLivraisonAnimal,
      commandeTransport: notification.commandeTransport,
      livreur: notification.livreur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notification = this.createFromForm();

    notification.livreur = this.account;

    if (notification.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  private createFromForm(): INotification {
    return {
      ...new Notification(),
      id: this.editForm.get(['id'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      prixValider: this.editForm.get(['prixValider'])!.value,
      client: this.editForm.get(['client'])!.value,
      commandeLivraison: this.editForm.get(['commandeLivraison'])!.value,
      commandeLivraisonAnimal: this.editForm.get(['commandeLivraisonAnimal'])!.value,
      commandeTransport: this.editForm.get(['commandeTransport'])!.value,
      livreur: this.editForm.get(['livreur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
