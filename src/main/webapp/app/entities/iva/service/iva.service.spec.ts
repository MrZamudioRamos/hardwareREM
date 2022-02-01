import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIva, Iva } from '../iva.model';

import { IvaService } from './iva.service';

describe('Iva Service', () => {
  let service: IvaService;
  let httpMock: HttpTestingController;
  let elemDefault: IIva;
  let expectedResult: IIva | IIva[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IvaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nombre: 'AAAAAAA',
      tipo: 'AAAAAAA',
      valor: 0,
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

    it('should create a Iva', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Iva()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Iva', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
          tipo: 'BBBBBB',
          valor: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Iva', () => {
      const patchObject = Object.assign(
        {
          valor: 1,
        },
        new Iva()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Iva', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nombre: 'BBBBBB',
          tipo: 'BBBBBB',
          valor: 1,
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

    it('should delete a Iva', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addIvaToCollectionIfMissing', () => {
      it('should add a Iva to an empty array', () => {
        const iva: IIva = { id: 123 };
        expectedResult = service.addIvaToCollectionIfMissing([], iva);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(iva);
      });

      it('should not add a Iva to an array that contains it', () => {
        const iva: IIva = { id: 123 };
        const ivaCollection: IIva[] = [
          {
            ...iva,
          },
          { id: 456 },
        ];
        expectedResult = service.addIvaToCollectionIfMissing(ivaCollection, iva);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Iva to an array that doesn't contain it", () => {
        const iva: IIva = { id: 123 };
        const ivaCollection: IIva[] = [{ id: 456 }];
        expectedResult = service.addIvaToCollectionIfMissing(ivaCollection, iva);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(iva);
      });

      it('should add only unique Iva to an array', () => {
        const ivaArray: IIva[] = [{ id: 123 }, { id: 456 }, { id: 47646 }];
        const ivaCollection: IIva[] = [{ id: 123 }];
        expectedResult = service.addIvaToCollectionIfMissing(ivaCollection, ...ivaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const iva: IIva = { id: 123 };
        const iva2: IIva = { id: 456 };
        expectedResult = service.addIvaToCollectionIfMissing([], iva, iva2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(iva);
        expect(expectedResult).toContain(iva2);
      });

      it('should accept null and undefined values', () => {
        const iva: IIva = { id: 123 };
        expectedResult = service.addIvaToCollectionIfMissing([], null, iva, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(iva);
      });

      it('should return initial array if no Iva is added', () => {
        const ivaCollection: IIva[] = [{ id: 123 }];
        expectedResult = service.addIvaToCollectionIfMissing(ivaCollection, undefined, null);
        expect(expectedResult).toEqual(ivaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
