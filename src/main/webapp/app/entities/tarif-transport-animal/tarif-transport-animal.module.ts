import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { TarifTransportAnimalComponent } from './tarif-transport-animal.component';
import { TarifTransportAnimalDetailComponent } from './tarif-transport-animal-detail.component';
import { TarifTransportAnimalUpdateComponent } from './tarif-transport-animal-update.component';
import { TarifTransportAnimalDeleteDialogComponent } from './tarif-transport-animal-delete-dialog.component';
import { tarifTransportAnimalRoute } from './tarif-transport-animal.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(tarifTransportAnimalRoute)],
  declarations: [
    TarifTransportAnimalComponent,
    TarifTransportAnimalDetailComponent,
    TarifTransportAnimalUpdateComponent,
    TarifTransportAnimalDeleteDialogComponent
  ],
  entryComponents: [TarifTransportAnimalDeleteDialogComponent]
})
export class CoviteTarifTransportAnimalModule {}
