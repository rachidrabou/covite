import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'commandes',
        loadChildren: () => import('./commandes/commandes.module').then(m => m.CoviteCommandesModule)
      },
      {
        path: 'vehicule',
        loadChildren: () => import('./vehicule/vehicule.module').then(m => m.CoviteVehiculeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CoviteEntityModule {}
