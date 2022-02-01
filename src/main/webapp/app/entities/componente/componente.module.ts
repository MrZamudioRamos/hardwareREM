import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ComponenteComponent } from './list/componente.component';
import { ComponenteDetailComponent } from './detail/componente-detail.component';
import { ComponenteUpdateComponent } from './update/componente-update.component';
import { ComponenteDeleteDialogComponent } from './delete/componente-delete-dialog.component';
import { ComponenteRoutingModule } from './route/componente-routing.module';

@NgModule({
  imports: [SharedModule, ComponenteRoutingModule],
  declarations: [ComponenteComponent, ComponenteDetailComponent, ComponenteUpdateComponent, ComponenteDeleteDialogComponent],
  entryComponents: [ComponenteDeleteDialogComponent],
})
export class ComponenteModule {}
