import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivreur, Livreur } from 'app/shared/model/livreur.model';
import { LivreurService } from './livreur.service';
import { LivreurComponent } from './livreur.component';
import { LivreurDetailComponent } from './livreur-detail.component';
import { LivreurUpdateComponent } from './livreur-update.component';

@Injectable({ providedIn: 'root' })
export class LivreurResolve implements Resolve<ILivreur> {
  constructor(private service: LivreurService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivreur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livreur: HttpResponse<Livreur>) => {
          if (livreur.body) {
            return of(livreur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Livreur());
  }
}

export const livreurRoute: Routes = [
  {
    path: '',
    component: LivreurComponent,
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.livreur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivreurDetailComponent,
    resolve: {
      livreur: LivreurResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.livreur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivreurUpdateComponent,
    resolve: {
      livreur: LivreurResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.livreur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivreurUpdateComponent,
    resolve: {
      livreur: LivreurResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.livreur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
