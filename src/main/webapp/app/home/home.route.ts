import { Route } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [Authority.USER, Authority.ADMIN],
    pageTitle: 'home.title',
  },
  canActivate: [UserRouteAccessService],
};
