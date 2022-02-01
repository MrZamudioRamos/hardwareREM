import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComponente, Componente } from '../componente.model';
import { ComponenteService } from '../service/componente.service';

@Injectable({ providedIn: 'root' })
export class ComponenteRoutingResolveService implements Resolve<IComponente> {
  constructor(protected service: ComponenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComponente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((componente: HttpResponse<Componente>) => {
          if (componente.body) {
            return of(componente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Componente());
  }
}
