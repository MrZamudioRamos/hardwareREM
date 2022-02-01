import dayjs from 'dayjs/esm';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IFactura {
  id?: number;
  fechaFacturacion?: dayjs.Dayjs;
  fechaVencimiento?: dayjs.Dayjs;
  cliente?: ICliente | null;
  empresa?: IEmpresa | null;
}

export class Factura implements IFactura {
  constructor(
    public id?: number,
    public fechaFacturacion?: dayjs.Dayjs,
    public fechaVencimiento?: dayjs.Dayjs,
    public cliente?: ICliente | null,
    public empresa?: IEmpresa | null
  ) {}
}

export function getFacturaIdentifier(factura: IFactura): number | undefined {
  return factura.id;
}
