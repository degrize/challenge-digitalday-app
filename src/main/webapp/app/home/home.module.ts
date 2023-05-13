import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { HomeSolutionsComponent } from './home-solutions/home-solutions.component';
import { HeroPageComponent } from './hero-page/hero-page.component';
import { CarouselModule } from 'primeng/carousel';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), CarouselModule],
  declarations: [HomeComponent, HeroPageComponent, HomeSolutionsComponent],
})
export class HomeModule {}
