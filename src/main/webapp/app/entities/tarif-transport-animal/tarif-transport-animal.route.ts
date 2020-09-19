import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarifTransportAnimal, TarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';
import { TarifTransportAnimalService } from './tarif-transport-animal.service';
import { TarifTransportAnimalComponent } from './tarif-transport-animal.component';
import { TarifTransportAnimalDetailComponent } from './tarif-transport-animal-detail.component';
import { TarifTransportAnimalUpdateComponent } from './tarif-transport-animal-update.component';

@Injectable({ providedIn: 'root' })
export class TarifTransportAnimalResolve implements Resolve<ITarifTransportAnimal> {
  constructor(private service: TarifTransportAnimalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarifTransportAnimal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tarifTransportAnimal: HttpResponse<TarifTransportAnimal>) => {
          if (tarifTransportAnimal.body) {
            return of(tarifTransportAnimal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TarifTransportAnimal());
  }
}

export const tarifTransportAnimalRoute: Routes = [
  {
    path: '',
    component: TarifTransportAnimalComponent,
    data: {
      authorities: [Authority.USER, Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.tarifTransportAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TarifTransportAnimalDetailComponent,
    resolve: {
      tarifTransportAnimal: TarifTransportAnimalResolve
    },
    data: {
      authorities: [Authority.USER, Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.tarifTransportAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TarifTransportAnimalUpdateComponent,
    resolve: {
      tarifTransportAnimal: TarifTransportAnimalResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.tarifTransportAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TarifTransportAnimalUpdateComponent,
    resolve: {
      tarifTransportAnimal: TarifTransportAnimalResolve
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'coviteApp.tarifTransportAnimal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
