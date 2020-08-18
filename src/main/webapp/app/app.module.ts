import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CoviteSharedModule } from 'app/shared/shared.module';
import { CoviteCoreModule } from 'app/core/core.module';
import { CoviteAppRoutingModule } from './app-routing.module';
import { CoviteHomeModule } from './home/home.module';
import { CoviteEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ServiceComponent } from './service/service.component';
import { LivreurComponent } from './livreur/livreur.component';
import { ChartsModule } from 'ng2-charts';
import { TransportComponent } from './transport/transport.component';
import { LivraisonComponent } from './livraison/livraison.component';
import { CommissionComponent } from './commission/commission.component';
import { MescommandesComponent } from './mescommandes/mescommandes.component';
import { TarifsComponent } from './tarifs/tarifs.component';

@NgModule({
  imports: [
    BrowserModule,
    CoviteSharedModule,
    CoviteCoreModule,
    CoviteHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CoviteEntityModule,
    CoviteAppRoutingModule,
    ChartsModule
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    ServiceComponent,
    LivreurComponent,
    TransportComponent,
    LivraisonComponent,
    CommissionComponent,
    MescommandesComponent,
    TarifsComponent
  ],
  bootstrap: [MainComponent]
})
export class CoviteAppModule {}
