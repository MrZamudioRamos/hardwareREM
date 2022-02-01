import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmpresa, Empresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';

@Injectable({ providedIn: 'root' })
export class EmpresaRoutingResolveService implements Resolve<IEmpresa> {
  constructor(protected service: EmpresaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmpresa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((empresa: HttpResponse<Empresa>) => {
          if (empresa.body) {
            return of(empresa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Empresa());
  }
}
