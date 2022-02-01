import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpleado, Empleado } from '../empleado.model';
import { EmpleadoService } from '../service/empleado.service';

@Injectable({ providedIn: 'root' })
export class EmpleadoRoutingResolveService implements Resolve<IEmpleado> {
  constructor(protected service: EmpleadoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpleado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((empleado: HttpResponse<Empleado>) => {
          if (empleado.body) {
            return of(empleado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Empleado());
  }
}
