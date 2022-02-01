import { IProducto } from 'app/entities/producto/producto.model';

export interface IComponente {
  id?: number;
  nombre?: string;
  numeroSerie?: string;
  marca?: string;
  modelo?: string;
  descripcion?: string;
  peso?: number;
  producto?: IProducto | null;
}

export class Componente implements IComponente {
  constructor(
    public id?: number,
    public nombre?: string,
    public numeroSerie?: string,
    public marca?: string,
    public modelo?: string,
    public descripcion?: string,
    public peso?: number,
    public producto?: IProducto | null
  ) {}
}

export function getComponenteIdentifier(componente: IComponente): number | undefined {
  return componente.id;
}
