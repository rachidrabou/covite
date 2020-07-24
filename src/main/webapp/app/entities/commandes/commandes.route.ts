import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandes, Commandes } from 'app/shared/model/commandes.model';
import { CommandesService } from './commandes.service';
import { CommandesComponent } from './commandes.component';
import { CommandesDetailComponent } from './commandes-detail.component';
import { CommandesUpdateComponent } from './commandes-update.component';

@Injectable({ providedIn: 'root' })
export class CommandesResolve implements Resolve<ICommandes> {
  constructor(private service: CommandesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandes: HttpResponse<Commandes>) => {
          if (commandes.body) {
            return of(commandes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Commandes());
  }
}

export const commandesRoute: Routes = [
  {
    path: '',
    component: CommandesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'coviteApp.commandes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandesDetailComponent,
    resolve: {
      commandes: CommandesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.commandes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandesUpdateComponent,
    resolve: {
      commandes: CommandesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.commandes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandesUpdateComponent,
    resolve: {
      commandes: CommandesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.commandes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
