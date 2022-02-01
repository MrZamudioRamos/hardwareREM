import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpresa, getEmpresaIdentifier } from '../empresa.model';

export type EntityResponseType = HttpResponse<IEmpresa>;
export type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/empresas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .post<IEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .put<IEmpresa>(`${this.resourceUrl}/${getEmpresaIdentifier(empresa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .patch<IEmpresa>(`${this.resourceUrl}/${getEmpresaIdentifier(empresa) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmpresaToCollectionIfMissing(empresaCollection: IEmpresa[], ...empresasToCheck: (IEmpresa | null | undefined)[]): IEmpresa[] {
    const empresas: IEmpresa[] = empresasToCheck.filter(isPresent);
    if (empresas.length > 0) {
      const empresaCollectionIdentifiers = empresaCollection.map(empresaItem => getEmpresaIdentifier(empresaItem)!);
      const empresasToAdd = empresas.filter(empresaItem => {
        const empresaIdentifier = getEmpresaIdentifier(empresaItem);
        if (empresaIdentifier == null || empresaCollectionIdentifiers.includes(empresaIdentifier)) {
          return false;
        }
        empresaCollectionIdentifiers.push(empresaIdentifier);
        return true;
      });
      return [...empresasToAdd, ...empresaCollection];
    }
    return empresaCollection;
  }

  protected convertDateFromClient(empresa: IEmpresa): IEmpresa {
    return Object.assign({}, empresa, {
      fechaCreacion: empresa.fechaCreacion?.isValid() ? empresa.fechaCreacion.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion ? dayjs(res.body.fechaCreacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((empresa: IEmpresa) => {
        empresa.fechaCreacion = empresa.fechaCreacion ? dayjs(empresa.fechaCreacion) : undefined;
      });
    }
    return res;
  }
}
