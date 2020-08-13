import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { TarifLivraisonComponent } from './tarif-livraison.component';
import { TarifLivraisonDetailComponent } from './tarif-livraison-detail.component';
import { TarifLivraisonUpdateComponent } from './tarif-livraison-update.component';
import { TarifLivraisonDeleteDialogComponent } from './tarif-livraison-delete-dialog.component';
import { tarifLivraisonRoute } from './tarif-livraison.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(tarifLivraisonRoute)],
  declarations: [
    TarifLivraisonComponent,
    TarifLivraisonDetailComponent,
    TarifLivraisonUpdateComponent,
    TarifLivraisonDeleteDialogComponent
  ],
  entryComponents: [TarifLivraisonDeleteDialogComponent]
})
export class CoviteTarifLivraisonModule {}
