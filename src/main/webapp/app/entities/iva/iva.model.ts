export interface IIva {
  id?: number;
  nombre?: string | null;
  tipo?: string | null;
  valor?: number | null;
}

export class Iva implements IIva {
  constructor(public id?: number, public nombre?: string | null, public tipo?: string | null, public valor?: number | null) {}
}

export function getIvaIdentifier(iva: IIva): number | undefined {
  return iva.id;
}
