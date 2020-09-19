import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifLivraison, TarifLivraison } from 'app/shared/model/tarif-livraison.model';
import { TarifLivraisonService } from './tarif-livraison.service';
import { TarifLivraisonComponent } from './tarif-livraison.component';
import { TarifLivraisonDetailComponent } from './tarif-livraison-detail.component';
import { TarifLivraisonUpdateComponent } from './tarif-livraison-update.component';

@Injectable({ providedIn: 'root' })
export class TarifLivraisonResolve implements Resolve<ITarifLivraison> {
  constructor(private service: TarifLivraisonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifLivraison> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifLivraison: HttpResponse<TarifLivraison>) => {
          if (tarifLivraison.body) {
            return of(tarifLivraison.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifLivraison());
  }
}

export const tarifLivraisonRoute: Routes = [
  {
    path: '',
    component: TarifLivraisonComponent,
    data: {
      authorities: [Authority.USER, Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.tarifLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifLivraisonDetailComponent,
    resolve: {
      tarifLivraison: TarifLivraisonResolve
    },
    data: {
      authorities: [Authority.USER, Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.tarifLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifLivraisonUpdateComponent,
    resolve: {
      tarifLivraison: TarifLivraisonResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.tarifLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifLivraisonUpdateComponent,
    resolve: {
      tarifLivraison: TarifLivraisonResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.tarifLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
