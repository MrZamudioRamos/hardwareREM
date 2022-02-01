import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AlmacenComponent } from './list/almacen.component';
import { AlmacenDetailComponent } from './detail/almacen-detail.component';
import { AlmacenUpdateComponent } from './update/almacen-update.component';
import { AlmacenDeleteDialogComponent } from './delete/almacen-delete-dialog.component';
import { AlmacenRoutingModule } from './route/almacen-routing.module';

@NgModule({
  imports: [SharedModule, AlmacenRoutingModule],
  declarations: [AlmacenComponent, AlmacenDetailComponent, AlmacenUpdateComponent, AlmacenDeleteDialogComponent],
  entryComponents: [AlmacenDeleteDialogComponent],
})
export class AlmacenModule {}
