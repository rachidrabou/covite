import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeLivraison, CommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { CommandeLivraisonService } from './commande-livraison.service';
import { CommandeLivraisonComponent } from './commande-livraison.component';
import { CommandeLivraisonDetailComponent } from './commande-livraison-detail.component';
import { CommandeLivraisonUpdateComponent } from './commande-livraison-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeLivraisonResolve implements Resolve<ICommandeLivraison> {
  constructor(private service: CommandeLivraisonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeLivraison> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeLivraison: HttpResponse<CommandeLivraison>) => {
          if (commandeLivraison.body) {
            return of(commandeLivraison.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeLivraison());
  }
}

export const commandeLivraisonRoute: Routes = [
  {
    path: '',
    component: CommandeLivraisonComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'coviteApp.commandeLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeLivraisonDetailComponent,
    resolve: {
      commandeLivraison: CommandeLivraisonResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN, Authority.USER],
      pageTitle: 'coviteApp.commandeLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeLivraisonUpdateComponent,
    resolve: {
      commandeLivraison: CommandeLivraisonResolve
    },
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
      pageTitle: 'coviteApp.commandeLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeLivraisonUpdateComponent,
    resolve: {
      commandeLivraison: CommandeLivraisonResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.commandeLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
