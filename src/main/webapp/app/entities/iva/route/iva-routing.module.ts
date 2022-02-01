import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IvaComponent } from '../list/iva.component';
import { IvaDetailComponent } from '../detail/iva-detail.component';
import { IvaUpdateComponent } from '../update/iva-update.component';
import { IvaRoutingResolveService } from './iva-routing-resolve.service';

const ivaRoute: Routes = [
  {
    path: '',
    component: IvaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IvaDetailComponent,
    resolve: {
      iva: IvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IvaUpdateComponent,
    resolve: {
      iva: IvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IvaUpdateComponent,
    resolve: {
      iva: IvaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ivaRoute)],
  exports: [RouterModule],
})
export class IvaRoutingModule {}
