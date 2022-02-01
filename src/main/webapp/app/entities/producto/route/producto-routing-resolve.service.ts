import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProducto, Producto } from '../producto.model';
import { ProductoService } from '../service/producto.service';

@Injectable({ providedIn: 'root' })
export class ProductoRoutingResolveService implements Resolve<IProducto> {
  constructor(protected service: ProductoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProducto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((producto: HttpResponse<Producto>) => {
          if (producto.body) {
            return of(producto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Producto());
  }
}
