import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAlmacen, Almacen } from '../almacen.model';

import { AlmacenService } from './almacen.service';

describe('Almacen Service', () => {
  let service: AlmacenService;
  let httpMock: HttpTestingController;
  let elemDefault: IAlmacen;
  let expectedResult: IAlmacen | IAlmacen[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AlmacenService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      telefono: 'AAAAAAA',
      pais: 'AAAAAAA',
      provincia: 'AAAAAAA',
      sucursal: 'AAAAAAA',
      codigoPostal: 'AAAAAAA',
      calle: 'AAAAAAA',
      albaran: 'AAAAAAA',
      stockProductos: 0,
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

    it('should create a Almacen', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Almacen()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Almacen', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          telefono: 'BBBBBB',
          pais: 'BBBBBB',
          provincia: 'BBBBBB',
          sucursal: 'BBBBBB',
          codigoPostal: 'BBBBBB',
          calle: 'BBBBBB',
          albaran: 'BBBBBB',
          stockProductos: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Almacen', () => {
      const patchObject = Object.assign(
        {
          telefono: 'BBBBBB',
          provincia: 'BBBBBB',
          calle: 'BBBBBB',
          stockProductos: 1,
        },
        new Almacen()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Almacen', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          telefono: 'BBBBBB',
          pais: 'BBBBBB',
          provincia: 'BBBBBB',
          sucursal: 'BBBBBB',
          codigoPostal: 'BBBBBB',
          calle: 'BBBBBB',
          albaran: 'BBBBBB',
          stockProductos: 1,
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

    it('should delete a Almacen', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAlmacenToCollectionIfMissing', () => {
      it('should add a Almacen to an empty array', () => {
        const almacen: IAlmacen = { id: 123 };
        expectedResult = service.addAlmacenToCollectionIfMissing([], almacen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(almacen);
      });

      it('should not add a Almacen to an array that contains it', () => {
        const almacen: IAlmacen = { id: 123 };
        const almacenCollection: IAlmacen[] = [
          {
            ...almacen,
          },
          { id: 456 },
        ];
        expectedResult = service.addAlmacenToCollectionIfMissing(almacenCollection, almacen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Almacen to an array that doesn't contain it", () => {
        const almacen: IAlmacen = { id: 123 };
        const almacenCollection: IAlmacen[] = [{ id: 456 }];
        expectedResult = service.addAlmacenToCollectionIfMissing(almacenCollection, almacen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(almacen);
      });

      it('should add only unique Almacen to an array', () => {
        const almacenArray: IAlmacen[] = [{ id: 123 }, { id: 456 }, { id: 66924 }];
        const almacenCollection: IAlmacen[] = [{ id: 123 }];
        expectedResult = service.addAlmacenToCollectionIfMissing(almacenCollection, ...almacenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const almacen: IAlmacen = { id: 123 };
        const almacen2: IAlmacen = { id: 456 };
        expectedResult = service.addAlmacenToCollectionIfMissing([], almacen, almacen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(almacen);
        expect(expectedResult).toContain(almacen2);
      });

      it('should accept null and undefined values', () => {
        const almacen: IAlmacen = { id: 123 };
        expectedResult = service.addAlmacenToCollectionIfMissing([], null, almacen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(almacen);
      });

      it('should return initial array if no Almacen is added', () => {
        const almacenCollection: IAlmacen[] = [{ id: 123 }];
        expectedResult = service.addAlmacenToCollectionIfMissing(almacenCollection, undefined, null);
        expect(expectedResult).toEqual(almacenCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
