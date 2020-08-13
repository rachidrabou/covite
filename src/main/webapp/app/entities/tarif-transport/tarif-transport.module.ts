import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { TarifTransportComponent } from './tarif-transport.component';
import { TarifTransportDetailComponent } from './tarif-transport-detail.component';
import { TarifTransportUpdateComponent } from './tarif-transport-update.component';
import { TarifTransportDeleteDialogComponent } from './tarif-transport-delete-dialog.component';
import { tarifTransportRoute } from './tarif-transport.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(tarifTransportRoute)],
  declarations: [
    TarifTransportComponent,
    TarifTransportDetailComponent,
    TarifTransportUpdateComponent,
    TarifTransportDeleteDialogComponent
  ],
  entryComponents: [TarifTransportDeleteDialogComponent]
})
export class CoviteTarifTransportModule {}
