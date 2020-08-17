import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { DotoSharedModule } from 'app/shared/shared.module';
import { DotoCoreModule } from 'app/core/core.module';
import { DotoAppRoutingModule } from './app-routing.module';
import { DotoHomeModule } from './home/home.module';
import { DotoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    DotoSharedModule,
    DotoCoreModule,
    DotoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DotoEntityModule,
    DotoAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class DotoAppModule {}
