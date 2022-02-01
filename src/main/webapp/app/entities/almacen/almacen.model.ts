import { IPedido } from 'app/entities/pedido/pedido.model';
import { IProducto } from 'app/entities/producto/producto.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IAlmacen {
  id?: number;
  telefono?: string;
  pais?: string;
  provincia?: string;
  sucursal?: string;
  codigoPostal?: string;
  calle?: string;
  albaran?: string;
  stockProductos?: number | null;
  pedidos?: IPedido[] | null;
  productos?: IProducto[] | null;
  empresa?: IEmpresa | null;
}

export class Almacen implements IAlmacen {
  constructor(
    public id?: number,
    public telefono?: string,
    public pais?: string,
    public provincia?: string,
    public sucursal?: string,
    public codigoPostal?: string,
    public calle?: string,
    public albaran?: string,
    public stockProductos?: number | null,
    public pedidos?: IPedido[] | null,
    public productos?: IProducto[] | null,
    public empresa?: IEmpresa | null
  ) {}
}

export function getAlmacenIdentifier(almacen: IAlmacen): number | undefined {
  return almacen.id;
}
