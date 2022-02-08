import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PedidoComponent } from './list/pedido.component';
import { PedidoDetailComponent } from './detail/pedido-detail.component';
import { PedidoUpdateComponent } from './update/pedido-update.component';
import { PedidoDeleteDialogComponent } from './delete/pedido-delete-dialog.component';
import { PedidoRoutingModule } from './route/pedido-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ModalProductComponent } from './update/modal/pedido-modal-productos-list.component';

@NgModule({
  imports: [SharedModule, PedidoRoutingModule, ReactiveFormsModule],
  declarations: [PedidoComponent, PedidoDetailComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, ModalProductComponent],
  entryComponents: [PedidoDeleteDialogComponent],
})
export class PedidoModule {}
