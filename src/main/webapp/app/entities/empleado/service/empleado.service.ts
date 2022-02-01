import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpleado, getEmpleadoIdentifier } from '../empleado.model';

export type EntityResponseType = HttpResponse<IEmpleado>;
export type EntityArrayResponseType = HttpResponse<IEmpleado[]>;

@Injectable({ providedIn: 'root' })
export class EmpleadoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/empleados');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(empleado: IEmpleado): Observable<EntityResponseType> {
    return this.http.post<IEmpleado>(this.resourceUrl, empleado, { observe: 'response' });
  }

  update(empleado: IEmpleado): Observable<EntityResponseType> {
    return this.http.put<IEmpleado>(`${this.resourceUrl}/${getEmpleadoIdentifier(empleado) as number}`, empleado, { observe: 'response' });
  }

  partialUpdate(empleado: IEmpleado): Observable<EntityResponseType> {
    return this.http.patch<IEmpleado>(`${this.resourceUrl}/${getEmpleadoIdentifier(empleado) as number}`, empleado, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpleado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpleado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEmpleadoToCollectionIfMissing(empleadoCollection: IEmpleado[], ...empleadosToCheck: (IEmpleado | null | undefined)[]): IEmpleado[] {
    const empleados: IEmpleado[] = empleadosToCheck.filter(isPresent);
    if (empleados.length > 0) {
      const empleadoCollectionIdentifiers = empleadoCollection.map(empleadoItem => getEmpleadoIdentifier(empleadoItem)!);
      const empleadosToAdd = empleados.filter(empleadoItem => {
        const empleadoIdentifier = getEmpleadoIdentifier(empleadoItem);
        if (empleadoIdentifier == null || empleadoCollectionIdentifiers.includes(empleadoIdentifier)) {
          return false;
        }
        empleadoCollectionIdentifiers.push(empleadoIdentifier);
        return true;
      });
      return [...empleadosToAdd, ...empleadoCollection];
    }
    return empleadoCollection;
  }
}
