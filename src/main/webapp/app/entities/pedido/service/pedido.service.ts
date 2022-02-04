import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPedido, getPedidoIdentifier } from '../pedido.model';
import { IEmpleado } from 'app/entities/empleado/empleado.model';

export type EntityResponseType = HttpResponse<IPedido>;
export type EntityArrayResponseType = HttpResponse<IPedido[]>;

@Injectable({ providedIn: 'root' })
export class PedidoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pedidos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pedido: IPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pedido);
    return this.http
      .post<IPedido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  buscarPorEmpleado(empleado: IEmpleado): Observable<EntityArrayResponseType> {
    return this.http
      .post<IPedido[]>(`${this.resourceUrl}/empleado`, empleado, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  update(pedido: IPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pedido);
    return this.http
      .put<IPedido>(`${this.resourceUrl}/${getPedidoIdentifier(pedido) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pedido: IPedido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pedido);
    return this.http
      .patch<IPedido>(`${this.resourceUrl}/${getPedidoIdentifier(pedido) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPedido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  simpleSearch(filtro: string, pageable: any): Observable<HttpResponse<any>> {
    const options = createRequestOption({ filtro, ...pageable });
    return this.http.get<IPedido[]>(`${this.resourceUrl}/searchingParam`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPedidoToCollectionIfMissing(pedidoCollection: IPedido[], ...pedidosToCheck: (IPedido | null | undefined)[]): IPedido[] {
    const pedidos: IPedido[] = pedidosToCheck.filter(isPresent);
    if (pedidos.length > 0) {
      const pedidoCollectionIdentifiers = pedidoCollection.map(pedidoItem => getPedidoIdentifier(pedidoItem)!);
      const pedidosToAdd = pedidos.filter(pedidoItem => {
        const pedidoIdentifier = getPedidoIdentifier(pedidoItem);
        if (pedidoIdentifier == null || pedidoCollectionIdentifiers.includes(pedidoIdentifier)) {
          return false;
        }
        pedidoCollectionIdentifiers.push(pedidoIdentifier);
        return true;
      });
      return [...pedidosToAdd, ...pedidoCollection];
    }
    return pedidoCollection;
  }

  protected convertDateFromClient(pedido: IPedido): IPedido {
    return Object.assign({}, pedido, {
      fechaPedido: pedido.fechaPedido?.isValid() ? pedido.fechaPedido.format(DATE_FORMAT) : undefined,
      fechaEntrega: pedido.fechaEntrega?.isValid() ? pedido.fechaEntrega.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPedido = res.body.fechaPedido ? dayjs(res.body.fechaPedido) : undefined;
      res.body.fechaEntrega = res.body.fechaEntrega ? dayjs(res.body.fechaEntrega) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pedido: IPedido) => {
        pedido.fechaPedido = pedido.fechaPedido ? dayjs(pedido.fechaPedido) : undefined;
        pedido.fechaEntrega = pedido.fechaEntrega ? dayjs(pedido.fechaEntrega) : undefined;
      });
    }
    return res;
  }
}
