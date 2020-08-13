import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifTransport, TarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';
import { TarifTransportComponent } from './tarif-transport.component';
import { TarifTransportDetailComponent } from './tarif-transport-detail.component';
import { TarifTransportUpdateComponent } from './tarif-transport-update.component';

@Injectable({ providedIn: 'root' })
export class TarifTransportResolve implements Resolve<ITarifTransport> {
  constructor(private service: TarifTransportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifTransport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifTransport: HttpResponse<TarifTransport>) => {
          if (tarifTransport.body) {
            return of(tarifTransport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifTransport());
  }
}

export const tarifTransportRoute: Routes = [
  {
    path: '',
    component: TarifTransportComponent,
    data: {
      authorities: [Authority.USER, Authority.LIVREUR],
      pageTitle: 'coviteApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifTransportDetailComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: [Authority.USER, Authority.LIVREUR],
      pageTitle: 'coviteApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifTransportUpdateComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifTransportUpdateComponent,
    resolve: {
      tarifTransport: TarifTransportResolve
    },
    data: {
      authorities: [Authority.USER, Authority.LIVREUR],
      pageTitle: 'coviteApp.tarifTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
