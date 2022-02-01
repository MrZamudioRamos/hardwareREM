import dayjs from 'dayjs/esm';
import { IFactura } from 'app/entities/factura/factura.model';
import { IProducto } from 'app/entities/producto/producto.model';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IAlmacen } from 'app/entities/almacen/almacen.model';

export interface IPedido {
  id?: number;
  direccionEntrega?: string;
  fechaPedido?: dayjs.Dayjs;
  fechaEntrega?: dayjs.Dayjs;
  descuento?: number | null;
  tipoPago?: string;
  precioTotal?: number;
  observaciones?: string | null;
  entregado?: boolean;
  enviado?: boolean;
  factura?: IFactura | null;
  productos?: IProducto[] | null;
  empleado?: IEmpleado | null;
  empresa?: IEmpresa | null;
  almacen?: IAlmacen | null;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public direccionEntrega?: string,
    public fechaPedido?: dayjs.Dayjs,
    public fechaEntrega?: dayjs.Dayjs,
    public descuento?: number | null,
    public tipoPago?: string,
    public precioTotal?: number,
    public observaciones?: string | null,
    public entregado?: boolean,
    public enviado?: boolean,
    public factura?: IFactura | null,
    public productos?: IProducto[] | null,
    public empleado?: IEmpleado | null,
    public empresa?: IEmpresa | null,
    public almacen?: IAlmacen | null
  ) {
    this.entregado = this.entregado ?? false;
    this.enviado = this.enviado ?? false;
  }
}

export function getPedidoIdentifier(pedido: IPedido): number | undefined {
  return pedido.id;
}
