import { Routes } from '@angular/router';

import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { Authority } from '../../config/authority.constants';
import { UserRouteAccessService } from '../../core/auth/user-route-access.service';
import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { PreoccupationComponent } from '../../preoccupation/preoccupation.component';
// import { EtatCompteComponent } from '../../etat-compte/etat-compte.component';
// import { MessageComponent } from '../../message/message.component';

export const AdminLayoutRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user-profile',
    component: UserProfileComponent,
    data: {
      authorities: [Authority.USER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: '',
    loadChildren: () => import(`../../entities/entity-routing.module`).then(m => m.EntityRoutingModule),
  },
  {
    path: 'account',
    data: {
      authorities: [Authority.USER],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('../../account/accountDashboard.module').then(m => m.AccountDashboardModule),
  },
  {
    path: 'admin',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('../../admin/admin-routing.module').then(m => m.AdminRoutingModule),
  },
  {
    path: 'preoccupation',
    component: PreoccupationComponent,
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
];
