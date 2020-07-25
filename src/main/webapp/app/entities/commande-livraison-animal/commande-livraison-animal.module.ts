import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { CommandeLivraisonAnimalComponent } from './commande-livraison-animal.component';
import { CommandeLivraisonAnimalDetailComponent } from './commande-livraison-animal-detail.component';
import { CommandeLivraisonAnimalUpdateComponent } from './commande-livraison-animal-update.component';
import { CommandeLivraisonAnimalDeleteDialogComponent } from './commande-livraison-animal-delete-dialog.component';
import { commandeLivraisonAnimalRoute } from './commande-livraison-animal.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(commandeLivraisonAnimalRoute)],
  declarations: [
    CommandeLivraisonAnimalComponent,
    CommandeLivraisonAnimalDetailComponent,
    CommandeLivraisonAnimalUpdateComponent,
    CommandeLivraisonAnimalDeleteDialogComponent
  ],
  entryComponents: [CommandeLivraisonAnimalDeleteDialogComponent]
})
export class CoviteCommandeLivraisonAnimalModule {}
