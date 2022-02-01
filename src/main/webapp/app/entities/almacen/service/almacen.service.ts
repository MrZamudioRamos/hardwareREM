import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlmacen, getAlmacenIdentifier } from '../almacen.model';

export type EntityResponseType = HttpResponse<IAlmacen>;
export type EntityArrayResponseType = HttpResponse<IAlmacen[]>;

@Injectable({ providedIn: 'root' })
export class AlmacenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/almacens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(almacen: IAlmacen): Observable<EntityResponseType> {
    return this.http.post<IAlmacen>(this.resourceUrl, almacen, { observe: 'response' });
  }

  update(almacen: IAlmacen): Observable<EntityResponseType> {
    return this.http.put<IAlmacen>(`${this.resourceUrl}/${getAlmacenIdentifier(almacen) as number}`, almacen, { observe: 'response' });
  }

  partialUpdate(almacen: IAlmacen): Observable<EntityResponseType> {
    return this.http.patch<IAlmacen>(`${this.resourceUrl}/${getAlmacenIdentifier(almacen) as number}`, almacen, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlmacen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlmacen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAlmacenToCollectionIfMissing(almacenCollection: IAlmacen[], ...almacensToCheck: (IAlmacen | null | undefined)[]): IAlmacen[] {
    const almacens: IAlmacen[] = almacensToCheck.filter(isPresent);
    if (almacens.length > 0) {
      const almacenCollectionIdentifiers = almacenCollection.map(almacenItem => getAlmacenIdentifier(almacenItem)!);
      const almacensToAdd = almacens.filter(almacenItem => {
        const almacenIdentifier = getAlmacenIdentifier(almacenItem);
        if (almacenIdentifier == null || almacenCollectionIdentifiers.includes(almacenIdentifier)) {
          return false;
        }
        almacenCollectionIdentifiers.push(almacenIdentifier);
        return true;
      });
      return [...almacensToAdd, ...almacenCollection];
    }
    return almacenCollection;
  }
}
