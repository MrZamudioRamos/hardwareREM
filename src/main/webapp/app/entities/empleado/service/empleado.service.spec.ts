import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmpleado, Empleado } from '../empleado.model';

import { EmpleadoService } from './empleado.service';

describe('Empleado Service', () => {
  let service: EmpleadoService;
  let httpMock: HttpTestingController;
  let elemDefault: IEmpleado;
  let expectedResult: IEmpleado | IEmpleado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmpleadoService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      dni: 'AAAAAAA',
      nombre: 'AAAAAAA',
      apellidos: 'AAAAAAA',
      telefono: 'AAAAAAA',
      mail: 'AAAAAAA',
      pais: 'AAAAAAA',
      provincia: 'AAAAAAA',
      codigoPostal: 'AAAAAAA',
      calle: 'AAAAAAA',
      numeroPedidos: 0,
      tipoContrato: 'AAAAAAA',
      comision: 0,
      activo: false,
      contrasena: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Empleado', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Empleado()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Empleado', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dni: 'BBBBBB',
          nombre: 'BBBBBB',
          apellidos: 'BBBBBB',
          telefono: 'BBBBBB',
          mail: 'BBBBBB',
          pais: 'BBBBBB',
          provincia: 'BBBBBB',
          codigoPostal: 'BBBBBB',
          calle: 'BBBBBB',
          numeroPedidos: 1,
          tipoContrato: 'BBBBBB',
          comision: 1,
          activo: true,
          contrasena: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Empleado', () => {
      const patchObject = Object.assign(
        {
          mail: 'BBBBBB',
          pais: 'BBBBBB',
          provincia: 'BBBBBB',
          tipoContrato: 'BBBBBB',
          comision: 1,
        },
        new Empleado()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Empleado', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dni: 'BBBBBB',
          nombre: 'BBBBBB',
          apellidos: 'BBBBBB',
          telefono: 'BBBBBB',
          mail: 'BBBBBB',
          pais: 'BBBBBB',
          provincia: 'BBBBBB',
          codigoPostal: 'BBBBBB',
          calle: 'BBBBBB',
          numeroPedidos: 1,
          tipoContrato: 'BBBBBB',
          comision: 1,
          activo: true,
          contrasena: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Empleado', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEmpleadoToCollectionIfMissing', () => {
      it('should add a Empleado to an empty array', () => {
        const empleado: IEmpleado = { id: 123 };
        expectedResult = service.addEmpleadoToCollectionIfMissing([], empleado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empleado);
      });

      it('should not add a Empleado to an array that contains it', () => {
        const empleado: IEmpleado = { id: 123 };
        const empleadoCollection: IEmpleado[] = [
          {
            ...empleado,
          },
          { id: 456 },
        ];
        expectedResult = service.addEmpleadoToCollectionIfMissing(empleadoCollection, empleado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Empleado to an array that doesn't contain it", () => {
        const empleado: IEmpleado = { id: 123 };
        const empleadoCollection: IEmpleado[] = [{ id: 456 }];
        expectedResult = service.addEmpleadoToCollectionIfMissing(empleadoCollection, empleado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empleado);
      });

      it('should add only unique Empleado to an array', () => {
        const empleadoArray: IEmpleado[] = [{ id: 123 }, { id: 456 }, { id: 95473 }];
        const empleadoCollection: IEmpleado[] = [{ id: 123 }];
        expectedResult = service.addEmpleadoToCollectionIfMissing(empleadoCollection, ...empleadoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const empleado: IEmpleado = { id: 123 };
        const empleado2: IEmpleado = { id: 456 };
        expectedResult = service.addEmpleadoToCollectionIfMissing([], empleado, empleado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(empleado);
        expect(expectedResult).toContain(empleado2);
      });

      it('should accept null and undefined values', () => {
        const empleado: IEmpleado = { id: 123 };
        expectedResult = service.addEmpleadoToCollectionIfMissing([], null, empleado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(empleado);
      });

      it('should return initial array if no Empleado is added', () => {
        const empleadoCollection: IEmpleado[] = [{ id: 123 }];
        expectedResult = service.addEmpleadoToCollectionIfMissing(empleadoCollection, undefined, null);
        expect(expectedResult).toEqual(empleadoCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
