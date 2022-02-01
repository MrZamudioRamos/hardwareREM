import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AlmacenComponent } from '../list/almacen.component';
import { AlmacenDetailComponent } from '../detail/almacen-detail.component';
import { AlmacenUpdateComponent } from '../update/almacen-update.component';
import { AlmacenRoutingResolveService } from './almacen-routing-resolve.service';

const almacenRoute: Routes = [
  {
    path: '',
    component: AlmacenComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlmacenDetailComponent,
    resolve: {
      almacen: AlmacenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlmacenUpdateComponent,
    resolve: {
      almacen: AlmacenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlmacenUpdateComponent,
    resolve: {
      almacen: AlmacenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(almacenRoute)],
  exports: [RouterModule],
})
export class AlmacenRoutingModule {}
