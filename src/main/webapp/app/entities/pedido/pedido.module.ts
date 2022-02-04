import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PedidoComponent } from './list/pedido.component';
import { PedidoDetailComponent } from './detail/pedido-detail.component';
import { PedidoUpdateComponent } from './update/pedido-update.component';
import { PedidoDeleteDialogComponent } from './delete/pedido-delete-dialog.component';
import { PedidoRoutingModule } from './route/pedido-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ModalComponent } from '../empleado/list/modal/modal-pedidos-por-empleado-component';

@NgModule({
  imports: [SharedModule, PedidoRoutingModule, ReactiveFormsModule],
  declarations: [PedidoComponent, PedidoDetailComponent, PedidoUpdateComponent, PedidoDeleteDialogComponent, ModalComponent],
  entryComponents: [PedidoDeleteDialogComponent],
})
export class PedidoModule {}
