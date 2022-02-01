import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductoService } from '../service/producto.service';
import { IProducto, Producto } from '../producto.model';
import { IIva } from 'app/entities/iva/iva.model';
import { IvaService } from 'app/entities/iva/service/iva.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';
import { IAlmacen } from 'app/entities/almacen/almacen.model';
import { AlmacenService } from 'app/entities/almacen/service/almacen.service';

import { ProductoUpdateComponent } from './producto-update.component';

describe('Producto Management Update Component', () => {
  let comp: ProductoUpdateComponent;
  let fixture: ComponentFixture<ProductoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productoService: ProductoService;
  let ivaService: IvaService;
  let empresaService: EmpresaService;
  let pedidoService: PedidoService;
  let almacenService: AlmacenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductoUpdateComponent],
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
      .overrideTemplate(ProductoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productoService = TestBed.inject(ProductoService);
    ivaService = TestBed.inject(IvaService);
    empresaService = TestBed.inject(EmpresaService);
    pedidoService = TestBed.inject(PedidoService);
    almacenService = TestBed.inject(AlmacenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call iva query and add missing value', () => {
      const producto: IProducto = { id: 456 };
      const iva: IIva = { id: 50371 };
      producto.iva = iva;

      const ivaCollection: IIva[] = [{ id: 37241 }];
      jest.spyOn(ivaService, 'query').mockReturnValue(of(new HttpResponse({ body: ivaCollection })));
      const expectedCollection: IIva[] = [iva, ...ivaCollection];
      jest.spyOn(ivaService, 'addIvaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      expect(ivaService.query).toHaveBeenCalled();
      expect(ivaService.addIvaToCollectionIfMissing).toHaveBeenCalledWith(ivaCollection, iva);
      expect(comp.ivasCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const producto: IProducto = { id: 456 };
      const empresa: IEmpresa = { id: 55737 };
      producto.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 26876 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pedido query and add missing value', () => {
      const producto: IProducto = { id: 456 };
      const pedido: IPedido = { id: 18326 };
      producto.pedido = pedido;

      const pedidoCollection: IPedido[] = [{ id: 38287 }];
      jest.spyOn(pedidoService, 'query').mockReturnValue(of(new HttpResponse({ body: pedidoCollection })));
      const additionalPedidos = [pedido];
      const expectedCollection: IPedido[] = [...additionalPedidos, ...pedidoCollection];
      jest.spyOn(pedidoService, 'addPedidoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      expect(pedidoService.query).toHaveBeenCalled();
      expect(pedidoService.addPedidoToCollectionIfMissing).toHaveBeenCalledWith(pedidoCollection, ...additionalPedidos);
      expect(comp.pedidosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Almacen query and add missing value', () => {
      const producto: IProducto = { id: 456 };
      const almacen: IAlmacen = { id: 9960 };
      producto.almacen = almacen;

      const almacenCollection: IAlmacen[] = [{ id: 49095 }];
      jest.spyOn(almacenService, 'query').mockReturnValue(of(new HttpResponse({ body: almacenCollection })));
      const additionalAlmacens = [almacen];
      const expectedCollection: IAlmacen[] = [...additionalAlmacens, ...almacenCollection];
      jest.spyOn(almacenService, 'addAlmacenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      expect(almacenService.query).toHaveBeenCalled();
      expect(almacenService.addAlmacenToCollectionIfMissing).toHaveBeenCalledWith(almacenCollection, ...additionalAlmacens);
      expect(comp.almacensSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const producto: IProducto = { id: 456 };
      const iva: IIva = { id: 89446 };
      producto.iva = iva;
      const empresa: IEmpresa = { id: 91202 };
      producto.empresa = empresa;
      const pedido: IPedido = { id: 75737 };
      producto.pedido = pedido;
      const almacen: IAlmacen = { id: 23447 };
      producto.almacen = almacen;

      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(producto));
      expect(comp.ivasCollection).toContain(iva);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.pedidosSharedCollection).toContain(pedido);
      expect(comp.almacensSharedCollection).toContain(almacen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producto>>();
      const producto = { id: 123 };
      jest.spyOn(productoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: producto }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productoService.update).toHaveBeenCalledWith(producto);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producto>>();
      const producto = new Producto();
      jest.spyOn(productoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: producto }));
      saveSubject.complete();

      // THEN
      expect(productoService.create).toHaveBeenCalledWith(producto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Producto>>();
      const producto = { id: 123 };
      jest.spyOn(productoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ producto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productoService.update).toHaveBeenCalledWith(producto);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackIvaById', () => {
      it('Should return tracked Iva primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackIvaById(0, entity);
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

    describe('trackPedidoById', () => {
      it('Should return tracked Pedido primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPedidoById(0, entity);
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
