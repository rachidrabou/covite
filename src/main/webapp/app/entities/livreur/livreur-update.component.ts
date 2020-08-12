import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILivreur, Livreur } from 'app/shared/model/livreur.model';
import { LivreurService } from './livreur.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-livreur-update',
  templateUrl: './livreur-update.component.html'
})
export class LivreurUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    telephone: [],
    solde: [],
    user: []
  });

  constructor(
    protected livreurService: LivreurService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livreur }) => {
      this.updateForm(livreur);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(livreur: ILivreur): void {
    this.editForm.patchValue({
      id: livreur.id,
      telephone: livreur.telephone,
      solde: livreur.solde,
      user: livreur.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livreur = this.createFromForm();
    if (livreur.id !== undefined) {
      this.subscribeToSaveResponse(this.livreurService.update(livreur));
    } else {
      this.subscribeToSaveResponse(this.livreurService.create(livreur));
    }
  }

  private createFromForm(): ILivreur {
    return {
      ...new Livreur(),
      id: this.editForm.get(['id'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      solde: this.editForm.get(['solde'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivreur>>): void {
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
