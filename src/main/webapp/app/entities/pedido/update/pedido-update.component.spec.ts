import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PedidoService } from '../service/pedido.service';
import { IPedido, Pedido } from '../pedido.model';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { EmpleadoService } from 'app/entities/empleado/service/empleado.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IAlmacen } from 'app/entities/almacen/almacen.model';
import { AlmacenService } from 'app/entities/almacen/service/almacen.service';

import { PedidoUpdateComponent } from './pedido-update.component';

describe('Pedido Management Update Component', () => {
  let comp: PedidoUpdateComponent;
  let fixture: ComponentFixture<PedidoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pedidoService: PedidoService;
  let facturaService: FacturaService;
  let empleadoService: EmpleadoService;
  let empresaService: EmpresaService;
  let almacenService: AlmacenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PedidoUpdateComponent],
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
      .overrideTemplate(PedidoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PedidoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pedidoService = TestBed.inject(PedidoService);
    facturaService = TestBed.inject(FacturaService);
    empleadoService = TestBed.inject(EmpleadoService);
    empresaService = TestBed.inject(EmpresaService);
    almacenService = TestBed.inject(AlmacenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call factura query and add missing value', () => {
      const pedido: IPedido = { id: 456 };
      const factura: IFactura = { id: 66001 };
      pedido.factura = factura;

      const facturaCollection: IFactura[] = [{ id: 42629 }];
      jest.spyOn(facturaService, 'query').mockReturnValue(of(new HttpResponse({ body: facturaCollection })));
      const expectedCollection: IFactura[] = [factura, ...facturaCollection];
      jest.spyOn(facturaService, 'addFacturaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      expect(facturaService.query).toHaveBeenCalled();
      expect(facturaService.addFacturaToCollectionIfMissing).toHaveBeenCalledWith(facturaCollection, factura);
      expect(comp.facturasCollection).toEqual(expectedCollection);
    });

    it('Should call Empleado query and add missing value', () => {
      const pedido: IPedido = { id: 456 };
      const empleado: IEmpleado = { id: 25983 };
      pedido.empleado = empleado;

      const empleadoCollection: IEmpleado[] = [{ id: 59154 }];
      jest.spyOn(empleadoService, 'query').mockReturnValue(of(new HttpResponse({ body: empleadoCollection })));
      const additionalEmpleados = [empleado];
      const expectedCollection: IEmpleado[] = [...additionalEmpleados, ...empleadoCollection];
      jest.spyOn(empleadoService, 'addEmpleadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      expect(empleadoService.query).toHaveBeenCalled();
      expect(empleadoService.addEmpleadoToCollectionIfMissing).toHaveBeenCalledWith(empleadoCollection, ...additionalEmpleados);
      expect(comp.empleadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const pedido: IPedido = { id: 456 };
      const empresa: IEmpresa = { id: 81068 };
      pedido.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 78675 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Almacen query and add missing value', () => {
      const pedido: IPedido = { id: 456 };
      const almacen: IAlmacen = { id: 33678 };
      pedido.almacen = almacen;

      const almacenCollection: IAlmacen[] = [{ id: 65003 }];
      jest.spyOn(almacenService, 'query').mockReturnValue(of(new HttpResponse({ body: almacenCollection })));
      const additionalAlmacens = [almacen];
      const expectedCollection: IAlmacen[] = [...additionalAlmacens, ...almacenCollection];
      jest.spyOn(almacenService, 'addAlmacenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      expect(almacenService.query).toHaveBeenCalled();
      expect(almacenService.addAlmacenToCollectionIfMissing).toHaveBeenCalledWith(almacenCollection, ...additionalAlmacens);
      expect(comp.almacensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pedido: IPedido = { id: 456 };
      const factura: IFactura = { id: 79933 };
      pedido.factura = factura;
      const empleado: IEmpleado = { id: 87473 };
      pedido.empleado = empleado;
      const empresa: IEmpresa = { id: 39203 };
      pedido.empresa = empresa;
      const almacen: IAlmacen = { id: 30048 };
      pedido.almacen = almacen;

      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pedido));
      expect(comp.facturasCollection).toContain(factura);
      expect(comp.empleadosSharedCollection).toContain(empleado);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.almacensSharedCollection).toContain(almacen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pedido>>();
      const pedido = { id: 123 };
      jest.spyOn(pedidoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pedido }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pedidoService.update).toHaveBeenCalledWith(pedido);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pedido>>();
      const pedido = new Pedido();
      jest.spyOn(pedidoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pedido }));
      saveSubject.complete();

      // THEN
      expect(pedidoService.create).toHaveBeenCalledWith(pedido);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Pedido>>();
      const pedido = { id: 123 };
      jest.spyOn(pedidoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pedido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pedidoService.update).toHaveBeenCalledWith(pedido);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFacturaById', () => {
      it('Should return tracked Factura primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFacturaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmpleadoById', () => {
      it('Should return tracked Empleado primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmpleadoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmpresaById', () => {
      it('Should return tracked Empresa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmpresaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAlmacenById', () => {
      it('Should return tracked Almacen primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAlmacenById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
