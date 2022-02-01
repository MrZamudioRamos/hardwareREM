import dayjs from 'dayjs/esm';
import { IIva } from 'app/entities/iva/iva.model';
import { IComponente } from 'app/entities/componente/componente.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { IAlmacen } from 'app/entities/almacen/almacen.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  numeroSerie?: string;
  marca?: string;
  modelo?: string;
  descripcion?: string;
  peso?: number;
  fechaVenta?: dayjs.Dayjs | null;
  precioCompra?: number;
  precioBruto?: number;
  precioIva?: number;
  vendido?: boolean;
  iva?: IIva | null;
  componentes?: IComponente[] | null;
  empresa?: IEmpresa | null;
  pedido?: IPedido | null;
  almacen?: IAlmacen | null;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public numeroSerie?: string,
    public marca?: string,
    public modelo?: string,
    public descripcion?: string,
    public peso?: number,
    public fechaVenta?: dayjs.Dayjs | null,
    public precioCompra?: number,
    public precioBruto?: number,
    public precioIva?: number,
    public vendido?: boolean,
    public iva?: IIva | null,
    public componentes?: IComponente[] | null,
    public empresa?: IEmpresa | null,
    public pedido?: IPedido | null,
    public almacen?: IAlmacen | null
  ) {
    this.vendido = this.vendido ?? false;
  }
}

export function getProductoIdentifier(producto: IProducto): number | undefined {
  return producto.id;
}
