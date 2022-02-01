import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAlmacen, Almacen } from '../almacen.model';
import { AlmacenService } from '../service/almacen.service';

@Injectable({ providedIn: 'root' })
export class AlmacenRoutingResolveService implements Resolve<IAlmacen> {
  constructor(protected service: AlmacenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlmacen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((almacen: HttpResponse<Almacen>) => {
          if (almacen.body) {
            return of(almacen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Almacen());
  }
}
