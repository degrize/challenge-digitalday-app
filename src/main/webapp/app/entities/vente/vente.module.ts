import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VenteComponent } from './list/vente.component';
import { VenteDetailComponent } from './detail/vente-detail.component';
import { VenteUpdateComponent } from './update/vente-update.component';
import { VenteDeleteDialogComponent } from './delete/vente-delete-dialog.component';
import { VenteRoutingModule } from './route/vente-routing.module';
import { TagModule } from 'primeng/tag';
import { OrderListModule } from 'primeng/orderlist';
import { PickListModule } from 'primeng/picklist';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { FieldsetModule } from 'primeng/fieldset';
import { TabViewModule } from 'primeng/tabview';
import { DividerModule } from 'primeng/divider';
import { SelectButtonModule } from 'primeng/selectbutton';
import { MessagesModule } from 'primeng/messages';

@NgModule({
  imports: [
    SharedModule,
    VenteRoutingModule,
    TagModule,
    OrderListModule,
    PickListModule,
    InputNumberModule,
    InputTextModule,
    TabViewModule,
    DividerModule,
    SelectButtonModule,
    MessagesModule,
  ],
  declarations: [VenteComponent, VenteDetailComponent, VenteUpdateComponent, VenteDeleteDialogComponent],
})
export class VenteModule {}
