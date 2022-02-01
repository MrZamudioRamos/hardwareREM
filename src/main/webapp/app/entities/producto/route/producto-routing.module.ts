import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductoComponent } from '../list/producto.component';
import { ProductoDetailComponent } from '../detail/producto-detail.component';
import { ProductoUpdateComponent } from '../update/producto-update.component';
import { ProductoRoutingResolveService } from './producto-routing-resolve.service';

const productoRoute: Routes = [
  {
    path: '',
    component: ProductoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductoDetailComponent,
    resolve: {
      producto: ProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductoUpdateComponent,
    resolve: {
      producto: ProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductoUpdateComponent,
    resolve: {
      producto: ProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productoRoute)],
  exports: [RouterModule],
})
export class ProductoRoutingModule {}
