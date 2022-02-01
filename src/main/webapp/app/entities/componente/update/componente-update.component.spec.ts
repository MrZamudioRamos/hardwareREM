import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ComponenteService } from '../service/componente.service';
import { IComponente, Componente } from '../componente.model';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';

import { ComponenteUpdateComponent } from './componente-update.component';

describe('Componente Management Update Component', () => {
  let comp: ComponenteUpdateComponent;
  let fixture: ComponentFixture<ComponenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let componenteService: ComponenteService;
  let productoService: ProductoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ComponenteUpdateComponent],
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
      .overrideTemplate(ComponenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComponenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    componenteService = TestBed.inject(ComponenteService);
    productoService = TestBed.inject(ProductoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Producto query and add missing value', () => {
      const componente: IComponente = { id: 456 };
      const producto: IProducto = { id: 68067 };
      componente.producto = producto;

      const productoCollection: IProducto[] = [{ id: 1613 }];
      jest.spyOn(productoService, 'query').mockReturnValue(of(new HttpResponse({ body: productoCollection })));
      const additionalProductos = [producto];
      const expectedCollection: IProducto[] = [...additionalProductos, ...productoCollection];
      jest.spyOn(productoService, 'addProductoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ componente });
      comp.ngOnInit();

      expect(productoService.query).toHaveBeenCalled();
      expect(productoService.addProductoToCollectionIfMissing).toHaveBeenCalledWith(productoCollection, ...additionalProductos);
      expect(comp.productosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const componente: IComponente = { id: 456 };
      const producto: IProducto = { id: 18662 };
      componente.producto = producto;

      activatedRoute.data = of({ componente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(componente));
      expect(comp.productosSharedCollection).toContain(producto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Componente>>();
      const componente = { id: 123 };
      jest.spyOn(componenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ componente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: componente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(componenteService.update).toHaveBeenCalledWith(componente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Componente>>();
      const componente = new Componente();
      jest.spyOn(componenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ componente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: componente }));
      saveSubject.complete();

      // THEN
      expect(componenteService.create).toHaveBeenCalledWith(componente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Componente>>();
      const componente = { id: 123 };
      jest.spyOn(componenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ componente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(componenteService.update).toHaveBeenCalledWith(componente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductoById', () => {
      it('Should return tracked Producto primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
