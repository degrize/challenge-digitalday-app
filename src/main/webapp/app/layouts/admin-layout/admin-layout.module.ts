import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ClipboardModule } from 'ngx-clipboard';

import { AdminLayoutRoutes } from './admin-layout.routing';
import { IconsComponent } from '../../pages/icons/icons.component';
import { MapsComponent } from '../../pages/maps/maps.component';
import { TablesComponent } from '../../pages/tables/tables.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
// import { ToastrModule } from 'ngx-toastr';
import { OrderListModule } from 'primeng/orderlist';
import { PreoccupationComponent } from '../../preoccupation/preoccupation.component';
import { DividerModule } from 'primeng/divider';
import { TableModule } from 'primeng/table';
import { InplaceModule } from 'primeng/inplace';
import { RippleModule } from 'primeng/ripple';
import { DockModule } from 'primeng/dock';
import { MessagesModule } from 'primeng/messages';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule,
    OrderListModule,
    DividerModule,
    TableModule,
    InplaceModule,
    RippleModule,
    DockModule,
    MessagesModule,
  ],
  declarations: [DashboardComponent, UserProfileComponent, TablesComponent, IconsComponent, MapsComponent, PreoccupationComponent],
})
export class AdminLayoutModule {}
