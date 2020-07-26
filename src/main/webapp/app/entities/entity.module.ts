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
      },
      {
        path: 'commande-transport',
        loadChildren: () => import('./commande-transport/commande-transport.module').then(m => m.CoviteCommandeTransportModule)
      },
      {
        path: 'commande-livraison',
        loadChildren: () => import('./commande-livraison/commande-livraison.module').then(m => m.CoviteCommandeLivraisonModule)
      },
      {
        path: 'commande-livraison-animal',
        loadChildren: () =>
          import('./commande-livraison-animal/commande-livraison-animal.module').then(m => m.CoviteCommandeLivraisonAnimalModule)
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.CoviteNotificationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CoviteEntityModule {}
