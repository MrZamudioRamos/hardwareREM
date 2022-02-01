import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IvaComponent } from './list/iva.component';
import { IvaDetailComponent } from './detail/iva-detail.component';
import { IvaUpdateComponent } from './update/iva-update.component';
import { IvaDeleteDialogComponent } from './delete/iva-delete-dialog.component';
import { IvaRoutingModule } from './route/iva-routing.module';

@NgModule({
  imports: [SharedModule, IvaRoutingModule],
  declarations: [IvaComponent, IvaDetailComponent, IvaUpdateComponent, IvaDeleteDialogComponent],
  entryComponents: [IvaDeleteDialogComponent],
})
export class IvaModule {}
