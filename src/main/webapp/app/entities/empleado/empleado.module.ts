import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmpleadoComponent } from './list/empleado.component';
import { EmpleadoDetailComponent } from './detail/empleado-detail.component';
import { EmpleadoUpdateComponent } from './update/empleado-update.component';
import { EmpleadoDeleteDialogComponent } from './delete/empleado-delete-dialog.component';
import { EmpleadoRoutingModule } from './route/empleado-routing.module';
import { ModalComponent } from './list/modal/modal-pedidos-por-empleado-component';

@NgModule({
  imports: [SharedModule, EmpleadoRoutingModule],
  declarations: [EmpleadoComponent, EmpleadoDetailComponent, EmpleadoUpdateComponent, EmpleadoDeleteDialogComponent, ModalComponent],
  entryComponents: [EmpleadoDeleteDialogComponent],
})
export class EmpleadoModule {}
