import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ComponenteComponent } from '../list/componente.component';
import { ComponenteDetailComponent } from '../detail/componente-detail.component';
import { ComponenteUpdateComponent } from '../update/componente-update.component';
import { ComponenteRoutingResolveService } from './componente-routing-resolve.service';

const componenteRoute: Routes = [
  {
    path: '',
    component: ComponenteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComponenteDetailComponent,
    resolve: {
      componente: ComponenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComponenteUpdateComponent,
    resolve: {
      componente: ComponenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComponenteUpdateComponent,
    resolve: {
      componente: ComponenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(componenteRoute)],
  exports: [RouterModule],
})
export class ComponenteRoutingModule {}
