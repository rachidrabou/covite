import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandeLivraisonAnimal, CommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { CommandeLivraisonAnimalService } from './commande-livraison-animal.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-commande-livraison-animal-update',
  templateUrl: './commande-livraison-animal-update.component.html'
})
export class CommandeLivraisonAnimalUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  dateHeureDp: any;

  editForm = this.fb.group({
    id: [],
    adresseDepart: [],
    adresseArrivee: [null, [Validators.required]],
    dateHeure: [null, [Validators.required]],
    animal: [null, [Validators.required]],
    moyenDeTransport: [],
    numeroClient: [],
    prix: [],
    validated: [],
    client: [],
    livreur: []
  });

  constructor(
    protected commandeLivraisonAnimalService: CommandeLivraisonAnimalService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeLivraisonAnimal }) => {
      this.updateForm(commandeLivraisonAnimal);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(commandeLivraisonAnimal: ICommandeLivraisonAnimal): void {
    this.editForm.patchValue({
      id: commandeLivraisonAnimal.id,
      adresseDepart: commandeLivraisonAnimal.adresseDepart,
      adresseArrivee: commandeLivraisonAnimal.adresseArrivee,
      dateHeure: commandeLivraisonAnimal.dateHeure,
      animal: commandeLivraisonAnimal.animal,
      moyenDeTransport: commandeLivraisonAnimal.moyenDeTransport,
      numeroClient: commandeLivraisonAnimal.numeroClient,
      prix: commandeLivraisonAnimal.prix,
      validated: commandeLivraisonAnimal.validated,
      client: commandeLivraisonAnimal.client,
      livreur: commandeLivraisonAnimal.livreur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeLivraisonAnimal = this.createFromForm();
    if (commandeLivraisonAnimal.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeLivraisonAnimalService.update(commandeLivraisonAnimal));
    } else {
      this.subscribeToSaveResponse(this.commandeLivraisonAnimalService.create(commandeLivraisonAnimal));
    }
  }

  private createFromForm(): ICommandeLivraisonAnimal {
    return {
      ...new CommandeLivraisonAnimal(),
      id: this.editForm.get(['id'])!.value,
      adresseDepart: this.editForm.get(['adresseDepart'])!.value,
      adresseArrivee: this.editForm.get(['adresseArrivee'])!.value,
      dateHeure: this.editForm.get(['dateHeure'])!.value,
      animal: this.editForm.get(['animal'])!.value,
      moyenDeTransport: this.editForm.get(['moyenDeTransport'])!.value,
      numeroClient: this.editForm.get(['numeroClient'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      validated: this.editForm.get(['validated'])!.value,
      client: this.editForm.get(['client'])!.value,
      livreur: this.editForm.get(['livreur'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeLivraisonAnimal>>): void {
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
