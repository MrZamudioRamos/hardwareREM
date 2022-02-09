

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
  tipoContrato?: string;
  comision?: number | null;
  activo?: boolean;
  contrasena?: string;
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
    public tipoContrato?: string,
    public comision?: number | null,
    public activo?: boolean,
    public contrasena?: string,
  ) {
    this.activo = this.activo ?? false;
  }
}

export function getEmpleadoIdentifier(empleado: IEmpleado): number | undefined {
  return empleado.id;
}
