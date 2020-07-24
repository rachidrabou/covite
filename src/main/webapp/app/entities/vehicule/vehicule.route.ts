import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';
import { VehiculeComponent } from './vehicule.component';
import { VehiculeDetailComponent } from './vehicule-detail.component';
import { VehiculeUpdateComponent } from './vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class VehiculeResolve implements Resolve<IVehicule> {
  constructor(private service: VehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vehicule: HttpResponse<Vehicule>) => {
          if (vehicule.body) {
            return of(vehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vehicule());
  }
}

export const vehiculeRoute: Routes = [
  {
    path: '',
    component: VehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'coviteApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehiculeDetailComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehiculeUpdateComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehiculeUpdateComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
