import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIva, getIvaIdentifier } from '../iva.model';

export type EntityResponseType = HttpResponse<IIva>;
export type EntityArrayResponseType = HttpResponse<IIva[]>;

@Injectable({ providedIn: 'root' })
export class IvaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ivas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(iva: IIva): Observable<EntityResponseType> {
    return this.http.post<IIva>(this.resourceUrl, iva, { observe: 'response' });
  }

  update(iva: IIva): Observable<EntityResponseType> {
    return this.http.put<IIva>(`${this.resourceUrl}/${getIvaIdentifier(iva) as number}`, iva, { observe: 'response' });
  }

  partialUpdate(iva: IIva): Observable<EntityResponseType> {
    return this.http.patch<IIva>(`${this.resourceUrl}/${getIvaIdentifier(iva) as number}`, iva, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIva>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIva[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addIvaToCollectionIfMissing(ivaCollection: IIva[], ...ivasToCheck: (IIva | null | undefined)[]): IIva[] {
    const ivas: IIva[] = ivasToCheck.filter(isPresent);
    if (ivas.length > 0) {
      const ivaCollectionIdentifiers = ivaCollection.map(ivaItem => getIvaIdentifier(ivaItem)!);
      const ivasToAdd = ivas.filter(ivaItem => {
        const ivaIdentifier = getIvaIdentifier(ivaItem);
        if (ivaIdentifier == null || ivaCollectionIdentifiers.includes(ivaIdentifier)) {
          return false;
        }
        ivaCollectionIdentifiers.push(ivaIdentifier);
        return true;
      });
      return [...ivasToAdd, ...ivaCollection];
    }
    return ivaCollection;
  }
}
