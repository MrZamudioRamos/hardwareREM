import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICliente, getClienteIdentifier } from '../cliente.model';

export type EntityResponseType = HttpResponse<ICliente>;
export type EntityArrayResponseType = HttpResponse<ICliente[]>;

@Injectable({ providedIn: 'root' })
export class ClienteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clientes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cliente: ICliente): Observable<EntityResponseType> {
    return this.http.post<ICliente>(this.resourceUrl, cliente, { observe: 'response' });
  }

  update(cliente: ICliente): Observable<EntityResponseType> {
    return this.http.put<ICliente>(`${this.resourceUrl}/${getClienteIdentifier(cliente) as number}`, cliente, { observe: 'response' });
  }

  partialUpdate(cliente: ICliente): Observable<EntityResponseType> {
    return this.http.patch<ICliente>(`${this.resourceUrl}/${getClienteIdentifier(cliente) as number}`, cliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICliente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICliente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addClienteToCollectionIfMissing(clienteCollection: ICliente[], ...clientesToCheck: (ICliente | null | undefined)[]): ICliente[] {
    const clientes: ICliente[] = clientesToCheck.filter(isPresent);
    if (clientes.length > 0) {
      const clienteCollectionIdentifiers = clienteCollection.map(clienteItem => getClienteIdentifier(clienteItem)!);
      const clientesToAdd = clientes.filter(clienteItem => {
        const clienteIdentifier = getClienteIdentifier(clienteItem);
        if (clienteIdentifier == null || clienteCollectionIdentifiers.includes(clienteIdentifier)) {
          return false;
        }
        clienteCollectionIdentifiers.push(clienteIdentifier);
        return true;
      });
      return [...clientesToAdd, ...clienteCollection];
    }
    return clienteCollection;
  }
}
