import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AlmacenService } from '../service/almacen.service';
import { IAlmacen, Almacen } from '../almacen.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

import { AlmacenUpdateComponent } from './almacen-update.component';

describe('Almacen Management Update Component', () => {
  let comp: AlmacenUpdateComponent;
  let fixture: ComponentFixture<AlmacenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let almacenService: AlmacenService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AlmacenUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AlmacenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AlmacenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    almacenService = TestBed.inject(AlmacenService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const almacen: IAlmacen = { id: 456 };
      const empresa: IEmpresa = { id: 69343 };
      almacen.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 62590 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ almacen });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const almacen: IAlmacen = { id: 456 };
      const empresa: IEmpresa = { id: 46511 };
      almacen.empresa = empresa;

      activatedRoute.data = of({ almacen });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(almacen));
      expect(comp.empresasSharedCollection).toContain(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Almacen>>();
      const almacen = { id: 123 };
      jest.spyOn(almacenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ almacen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: almacen }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(almacenService.update).toHaveBeenCalledWith(almacen);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Almacen>>();
      const almacen = new Almacen();
      jest.spyOn(almacenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ almacen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: almacen }));
      saveSubject.complete();

      // THEN
      expect(almacenService.create).toHaveBeenCalledWith(almacen);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Almacen>>();
      const almacen = { id: 123 };
      jest.spyOn(almacenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ almacen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(almacenService.update).toHaveBeenCalledWith(almacen);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEmpresaById', () => {
      it('Should return tracked Empresa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmpresaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
