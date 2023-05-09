import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CommanderComponent } from './list/commander.component';
import { CommanderDetailComponent } from './detail/commander-detail.component';
import { CommanderUpdateComponent } from './update/commander-update.component';
import { CommanderDeleteDialogComponent } from './delete/commander-delete-dialog.component';
import { CommanderRoutingModule } from './route/commander-routing.module';

@NgModule({
  imports: [SharedModule, CommanderRoutingModule],
  declarations: [CommanderComponent, CommanderDetailComponent, CommanderUpdateComponent, CommanderDeleteDialogComponent],
})
export class CommanderModule {}
