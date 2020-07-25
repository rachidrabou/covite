import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { CommandeLivraisonComponent } from './commande-livraison.component';
import { CommandeLivraisonDetailComponent } from './commande-livraison-detail.component';
import { CommandeLivraisonUpdateComponent } from './commande-livraison-update.component';
import { CommandeLivraisonDeleteDialogComponent } from './commande-livraison-delete-dialog.component';
import { commandeLivraisonRoute } from './commande-livraison.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(commandeLivraisonRoute)],
  declarations: [
    CommandeLivraisonComponent,
    CommandeLivraisonDetailComponent,
    CommandeLivraisonUpdateComponent,
    CommandeLivraisonDeleteDialogComponent
  ],
  entryComponents: [CommandeLivraisonDeleteDialogComponent]
})
export class CoviteCommandeLivraisonModule {}
