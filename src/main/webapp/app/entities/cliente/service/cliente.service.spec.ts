import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICliente, Cliente } from '../cliente.model';

import { ClienteService } from './cliente.service';

describe('Cliente Service', () => {
  let service: ClienteService;
  let httpMock: HttpTestingController;
  let elemDefault: ICliente;
  let expectedResult: ICliente | ICliente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClienteService);
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
      fidelizado: false,
      activo: false,
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

    it('should create a Cliente', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Cliente()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cliente', () => {
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
          fidelizado: true,
          activo: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cliente', () => {
      const patchObject = Object.assign(
        {
          dni: 'BBBBBB',
          mail: 'BBBBBB',
          pais: 'BBBBBB',
          activo: true,
        },
        new Cliente()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cliente', () => {
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
          fidelizado: true,
          activo: true,
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

    it('should delete a Cliente', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addClienteToCollectionIfMissing', () => {
      it('should add a Cliente to an empty array', () => {
        const cliente: ICliente = { id: 123 };
        expectedResult = service.addClienteToCollectionIfMissing([], cliente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cliente);
      });

      it('should not add a Cliente to an array that contains it', () => {
        const cliente: ICliente = { id: 123 };
        const clienteCollection: ICliente[] = [
          {
            ...cliente,
          },
          { id: 456 },
        ];
        expectedResult = service.addClienteToCollectionIfMissing(clienteCollection, cliente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cliente to an array that doesn't contain it", () => {
        const cliente: ICliente = { id: 123 };
        const clienteCollection: ICliente[] = [{ id: 456 }];
        expectedResult = service.addClienteToCollectionIfMissing(clienteCollection, cliente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cliente);
      });

      it('should add only unique Cliente to an array', () => {
        const clienteArray: ICliente[] = [{ id: 123 }, { id: 456 }, { id: 88013 }];
        const clienteCollection: ICliente[] = [{ id: 123 }];
        expectedResult = service.addClienteToCollectionIfMissing(clienteCollection, ...clienteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cliente: ICliente = { id: 123 };
        const cliente2: ICliente = { id: 456 };
        expectedResult = service.addClienteToCollectionIfMissing([], cliente, cliente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cliente);
        expect(expectedResult).toContain(cliente2);
      });

      it('should accept null and undefined values', () => {
        const cliente: ICliente = { id: 123 };
        expectedResult = service.addClienteToCollectionIfMissing([], null, cliente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cliente);
      });

      it('should return initial array if no Cliente is added', () => {
        const clienteCollection: ICliente[] = [{ id: 123 }];
        expectedResult = service.addClienteToCollectionIfMissing(clienteCollection, undefined, null);
        expect(expectedResult).toEqual(clienteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
