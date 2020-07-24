import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviteSharedModule } from 'app/shared/shared.module';
import { CommandesComponent } from './commandes.component';
import { CommandesDetailComponent } from './commandes-detail.component';
import { CommandesUpdateComponent } from './commandes-update.component';
import { CommandesDeleteDialogComponent } from './commandes-delete-dialog.component';
import { commandesRoute } from './commandes.route';

@NgModule({
  imports: [CoviteSharedModule, RouterModule.forChild(commandesRoute)],
  declarations: [CommandesComponent, CommandesDetailComponent, CommandesUpdateComponent, CommandesDeleteDialogComponent],
  entryComponents: [CommandesDeleteDialogComponent]
})
export class CoviteCommandesModule {}
