import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProducto, getProductoIdentifier } from '../producto.model';

export type EntityResponseType = HttpResponse<IProducto>;
export type EntityArrayResponseType = HttpResponse<IProducto[]>;

@Injectable({ providedIn: 'root' })
export class ProductoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/productos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(producto: IProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producto);
    return this.http
      .post<IProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(producto: IProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producto);
    return this.http
      .put<IProducto>(`${this.resourceUrl}/${getProductoIdentifier(producto) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(producto: IProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(producto);
    return this.http
      .patch<IProducto>(`${this.resourceUrl}/${getProductoIdentifier(producto) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductoToCollectionIfMissing(productoCollection: IProducto[], ...productosToCheck: (IProducto | null | undefined)[]): IProducto[] {
    const productos: IProducto[] = productosToCheck.filter(isPresent);
    if (productos.length > 0) {
      const productoCollectionIdentifiers = productoCollection.map(productoItem => getProductoIdentifier(productoItem)!);
      const productosToAdd = productos.filter(productoItem => {
        const productoIdentifier = getProductoIdentifier(productoItem);
        if (productoIdentifier == null || productoCollectionIdentifiers.includes(productoIdentifier)) {
          return false;
        }
        productoCollectionIdentifiers.push(productoIdentifier);
        return true;
      });
      return [...productosToAdd, ...productoCollection];
    }
    return productoCollection;
  }

  protected convertDateFromClient(producto: IProducto): IProducto {
    return Object.assign({}, producto, {
      fechaVenta: producto.fechaVenta?.isValid() ? producto.fechaVenta.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaVenta = res.body.fechaVenta ? dayjs(res.body.fechaVenta) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((producto: IProducto) => {
        producto.fechaVenta = producto.fechaVenta ? dayjs(producto.fechaVenta) : undefined;
      });
    }
    return res;
  }
}
