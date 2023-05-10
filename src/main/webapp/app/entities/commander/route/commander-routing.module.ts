import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CommanderComponent } from '../list/commander.component';
import { CommanderDetailComponent } from '../detail/commander-detail.component';
import { CommanderUpdateComponent } from '../update/commander-update.component';
import { CommanderRoutingResolveService } from './commander-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const commanderRoute: Routes = [
  {
    path: '',
    component: CommanderComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommanderDetailComponent,
    resolve: {
      commander: CommanderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommanderUpdateComponent,
    resolve: {
      commander: CommanderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommanderUpdateComponent,
    resolve: {
      commander: CommanderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(commanderRoute)],
  exports: [RouterModule],
})
export class CommanderRoutingModule {}
