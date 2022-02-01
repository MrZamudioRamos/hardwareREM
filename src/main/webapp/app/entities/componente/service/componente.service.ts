import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComponente, getComponenteIdentifier } from '../componente.model';

export type EntityResponseType = HttpResponse<IComponente>;
export type EntityArrayResponseType = HttpResponse<IComponente[]>;

@Injectable({ providedIn: 'root' })
export class ComponenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/componentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(componente: IComponente): Observable<EntityResponseType> {
    return this.http.post<IComponente>(this.resourceUrl, componente, { observe: 'response' });
  }

  update(componente: IComponente): Observable<EntityResponseType> {
    return this.http.put<IComponente>(`${this.resourceUrl}/${getComponenteIdentifier(componente) as number}`, componente, {
      observe: 'response',
    });
  }

  partialUpdate(componente: IComponente): Observable<EntityResponseType> {
    return this.http.patch<IComponente>(`${this.resourceUrl}/${getComponenteIdentifier(componente) as number}`, componente, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComponente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComponente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  simpleSearch(filtro:string, pageable:any): Observable<HttpResponse<any>> {
    const options = createRequestOption({filtro, ...pageable});
    return this.http.get<IComponente[]>(`${this.resourceUrl}/searchingParam`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addComponenteToCollectionIfMissing(
    componenteCollection: IComponente[],
    ...componentesToCheck: (IComponente | null | undefined)[]
  ): IComponente[] {
    const componentes: IComponente[] = componentesToCheck.filter(isPresent);
    if (componentes.length > 0) {
      const componenteCollectionIdentifiers = componenteCollection.map(componenteItem => getComponenteIdentifier(componenteItem)!);
      const componentesToAdd = componentes.filter(componenteItem => {
        const componenteIdentifier = getComponenteIdentifier(componenteItem);
        if (componenteIdentifier == null || componenteCollectionIdentifiers.includes(componenteIdentifier)) {
          return false;
        }
        componenteCollectionIdentifiers.push(componenteIdentifier);
        return true;
      });
      return [...componentesToAdd, ...componenteCollection];
    }
    return componenteCollection;
  }
}
