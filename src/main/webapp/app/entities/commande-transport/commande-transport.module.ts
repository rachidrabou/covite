import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { CommandeTransportComponent } from './commande-transport.component';
import { CommandeTransportDetailComponent } from './commande-transport-detail.component';
import { CommandeTransportUpdateComponent } from './commande-transport-update.component';
import { CommandeTransportDeleteDialogComponent } from './commande-transport-delete-dialog.component';
import { commandeTransportRoute } from './commande-transport.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(commandeTransportRoute)],
  declarations: [
    CommandeTransportComponent,
    CommandeTransportDetailComponent,
    CommandeTransportUpdateComponent,
    CommandeTransportDeleteDialogComponent
  ],
  entryComponents: [CommandeTransportDeleteDialogComponent]
})
export class CoviteCommandeTransportModule {}
