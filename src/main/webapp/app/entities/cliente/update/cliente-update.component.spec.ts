import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClienteService } from '../service/cliente.service';
import { ICliente, Cliente } from '../cliente.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

import { ClienteUpdateComponent } from './cliente-update.component';

describe('Cliente Management Update Component', () => {
  let comp: ClienteUpdateComponent;
  let fixture: ComponentFixture<ClienteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clienteService: ClienteService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClienteUpdateComponent],
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
      .overrideTemplate(ClienteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClienteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clienteService = TestBed.inject(ClienteService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const cliente: ICliente = { id: 456 };
      const empresa: IEmpresa = { id: 84730 };
      cliente.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 96487 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cliente });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cliente: ICliente = { id: 456 };
      const empresa: IEmpresa = { id: 74218 };
      cliente.empresa = empresa;

      activatedRoute.data = of({ cliente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(cliente));
      expect(comp.empresasSharedCollection).toContain(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cliente>>();
      const cliente = { id: 123 };
      jest.spyOn(clienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cliente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(clienteService.update).toHaveBeenCalledWith(cliente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cliente>>();
      const cliente = new Cliente();
      jest.spyOn(clienteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cliente }));
      saveSubject.complete();

      // THEN
      expect(clienteService.create).toHaveBeenCalledWith(cliente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cliente>>();
      const cliente = { id: 123 };
      jest.spyOn(clienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clienteService.update).toHaveBeenCalledWith(cliente);
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
