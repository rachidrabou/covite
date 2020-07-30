import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';

type SelectableEntity = IUser | ICommandeLivraison;

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  commandelivraisons: ICommandeLivraison[] = [];

  editForm = this.fb.group({
    id: [],
    titre: [],
    client: [],
    commandeLivraison: []
  });

  constructor(
    protected notificationService: NotificationService,
    protected userService: UserService,
    protected commandeLivraisonService: CommandeLivraisonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
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
    });
  }

  updateForm(notification: INotification): void {
    this.editForm.patchValue({
      id: notification.id,
      titre: notification.titre,
      client: notification.client,
      commandeLivraison: notification.commandeLivraison
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notification = this.createFromForm();
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
      client: this.editForm.get(['client'])!.value,
      commandeLivraison: this.editForm.get(['commandeLivraison'])!.value
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
