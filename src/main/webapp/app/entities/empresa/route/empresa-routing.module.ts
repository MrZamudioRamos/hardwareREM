import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmpresaComponent } from '../list/empresa.component';
import { EmpresaDetailComponent } from '../detail/empresa-detail.component';
import { EmpresaUpdateComponent } from '../update/empresa-update.component';
import { EmpresaRoutingResolveService } from './empresa-routing-resolve.service';

const empresaRoute: Routes = [
  {
    path: '',
    component: EmpresaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmpresaDetailComponent,
    resolve: {
      empresa: EmpresaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmpresaUpdateComponent,
    resolve: {
      empresa: EmpresaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(empresaRoute)],
  exports: [RouterModule],
})
export class EmpresaRoutingModule {}
