import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeLivraisonAnimal, CommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { CommandeLivraisonAnimalService } from './commande-livraison-animal.service';
import { CommandeLivraisonAnimalComponent } from './commande-livraison-animal.component';
import { CommandeLivraisonAnimalDetailComponent } from './commande-livraison-animal-detail.component';
import { CommandeLivraisonAnimalUpdateComponent } from './commande-livraison-animal-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeLivraisonAnimalResolve implements Resolve<ICommandeLivraisonAnimal> {
  constructor(private service: CommandeLivraisonAnimalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeLivraisonAnimal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeLivraisonAnimal: HttpResponse<CommandeLivraisonAnimal>) => {
          if (commandeLivraisonAnimal.body) {
            return of(commandeLivraisonAnimal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeLivraisonAnimal());
  }
}

export const commandeLivraisonAnimalRoute: Routes = [
  {
    path: '',
    component: CommandeLivraisonAnimalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'coviteApp.commandeLivraisonAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeLivraisonAnimalDetailComponent,
    resolve: {
      commandeLivraisonAnimal: CommandeLivraisonAnimalResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN, Authority.USER],
      pageTitle: 'coviteApp.commandeLivraisonAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeLivraisonAnimalUpdateComponent,
    resolve: {
      commandeLivraisonAnimal: CommandeLivraisonAnimalResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.commandeLivraisonAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeLivraisonAnimalUpdateComponent,
    resolve: {
      commandeLivraisonAnimal: CommandeLivraisonAnimalResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.commandeLivraisonAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
