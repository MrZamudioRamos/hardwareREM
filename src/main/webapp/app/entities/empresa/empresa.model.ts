import dayjs from 'dayjs/esm';
import { IFactura } from 'app/entities/factura/factura.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { IProducto } from 'app/entities/producto/producto.model';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { IAlmacen } from 'app/entities/almacen/almacen.model';
import { IEmpleado } from 'app/entities/empleado/empleado.model';

export interface IEmpresa {
  id?: number;
  nombreSocial?: string;
  cif?: string;
  telefono?: string;
  mail?: string;
  pais?: string;
  provincia?: string;
  sucursal?: string;
  codigoPostal?: string;
  calle?: string;
  categoria?: string;
  fechaCreacion?: dayjs.Dayjs;
  facturas?: IFactura[] | null;
  clientes?: ICliente[] | null;
  productos?: IProducto[] | null;
  pedidos?: IPedido[] | null;
  almacens?: IAlmacen[] | null;
  empleados?: IEmpleado[] | null;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nombreSocial?: string,
    public cif?: string,
    public telefono?: string,
    public mail?: string,
    public pais?: string,
    public provincia?: string,
    public sucursal?: string,
    public codigoPostal?: string,
    public calle?: string,
    public categoria?: string,
    public fechaCreacion?: dayjs.Dayjs,
    public facturas?: IFactura[] | null,
    public clientes?: ICliente[] | null,
    public productos?: IProducto[] | null,
    public pedidos?: IPedido[] | null,
    public almacens?: IAlmacen[] | null,
    public empleados?: IEmpleado[] | null
  ) {}
}

export function getEmpresaIdentifier(empresa: IEmpresa): number | undefined {
  return empresa.id;
}
