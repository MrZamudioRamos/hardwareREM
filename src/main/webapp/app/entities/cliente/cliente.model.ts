import { IFactura } from 'app/entities/factura/factura.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface ICliente {
  id?: number;
  dni?: string;
  nombre?: string;
  apellidos?: string;
  telefono?: string;
  mail?: string;
  pais?: string;
  provincia?: string;
  codigoPostal?: string;
  calle?: string;
  fidelizado?: boolean;
  activo?: boolean;
  facturas?: IFactura[] | null;
  empresa?: IEmpresa | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public dni?: string,
    public nombre?: string,
    public apellidos?: string,
    public telefono?: string,
    public mail?: string,
    public pais?: string,
    public provincia?: string,
    public codigoPostal?: string,
    public calle?: string,
    public fidelizado?: boolean,
    public activo?: boolean,
    public facturas?: IFactura[] | null,
    public empresa?: IEmpresa | null
  ) {
    this.fidelizado = this.fidelizado ?? false;
    this.activo = this.activo ?? false;
  }
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
