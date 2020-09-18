import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeTransport, CommandeTransport } from 'app/shared/model/commande-transport.model';
import { CommandeTransportService } from './commande-transport.service';
import { CommandeTransportComponent } from './commande-transport.component';
import { CommandeTransportDetailComponent } from './commande-transport-detail.component';
import { CommandeTransportUpdateComponent } from './commande-transport-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeTransportResolve implements Resolve<ICommandeTransport> {
  constructor(private service: CommandeTransportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeTransport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeTransport: HttpResponse<CommandeTransport>) => {
          if (commandeTransport.body) {
            return of(commandeTransport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeTransport());
  }
}

export const commandeTransportRoute: Routes = [
  {
    path: '',
    component: CommandeTransportComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'coviteApp.commandeTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeTransportDetailComponent,
    resolve: {
      commandeTransport: CommandeTransportResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN, Authority.USER],
      pageTitle: 'coviteApp.commandeTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeTransportUpdateComponent,
    resolve: {
      commandeTransport: CommandeTransportResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'coviteApp.commandeTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeTransportUpdateComponent,
    resolve: {
      commandeTransport: CommandeTransportResolve
    },
    data: {
      authorities: [Authority.LIVREUR, Authority.ADMIN],
      pageTitle: 'coviteApp.commandeTransport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
