import { IPedido } from 'app/entities/pedido/pedido.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IUser } from 'app/entities/user/user.model';

export interface IEmpleado {
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
  numeroPedidos?: number | null;
  tipoContrato?: string;
  comision?: number | null;
  activo?: boolean;
  contrasena?: string;
  pedidos?: IPedido[] | null;
  empresa?: IEmpresa | null;
  user?: IUser | null;
}

export class Empleado implements IEmpleado {
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
    public numeroPedidos?: number | null,
    public tipoContrato?: string,
    public comision?: number | null,
    public activo?: boolean,
    public contrasena?: string,
    public pedidos?: IPedido[] | null,
    public empresa?: IEmpresa | null,
    public user?: IUser | null
  ) {
    this.activo = this.activo ?? false;
  }
}

export function getEmpleadoIdentifier(empleado: IEmpleado): number | undefined {
  return empleado.id;
}
