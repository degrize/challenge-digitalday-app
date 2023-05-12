import { Routes } from '@angular/router';
import { navbarRoute } from '../navbar/navbar.route';

export const FenStartLayoutRoutes: Routes = [
  {
    path: 'account',
    loadChildren: () => import('../../account/account.module').then(m => m.AccountModule),
  },
  {
    path: 'login',
    loadChildren: () => import('../../login/login.module').then(m => m.LoginModule),
  },
  navbarRoute,
];
