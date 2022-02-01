import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIva, Iva } from '../iva.model';
import { IvaService } from '../service/iva.service';

@Injectable({ providedIn: 'root' })
export class IvaRoutingResolveService implements Resolve<IIva> {
  constructor(protected service: IvaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIva> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((iva: HttpResponse<Iva>) => {
          if (iva.body) {
            return of(iva.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Iva());
  }
}
