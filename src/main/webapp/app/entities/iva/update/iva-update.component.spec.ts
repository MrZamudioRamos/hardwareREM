import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IvaService } from '../service/iva.service';
import { IIva, Iva } from '../iva.model';

import { IvaUpdateComponent } from './iva-update.component';

describe('Iva Management Update Component', () => {
  let comp: IvaUpdateComponent;
  let fixture: ComponentFixture<IvaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ivaService: IvaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IvaUpdateComponent],
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
      .overrideTemplate(IvaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IvaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ivaService = TestBed.inject(IvaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const iva: IIva = { id: 456 };

      activatedRoute.data = of({ iva });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(iva));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Iva>>();
      const iva = { id: 123 };
      jest.spyOn(ivaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: iva }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ivaService.update).toHaveBeenCalledWith(iva);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Iva>>();
      const iva = new Iva();
      jest.spyOn(ivaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: iva }));
      saveSubject.complete();

      // THEN
      expect(ivaService.create).toHaveBeenCalledWith(iva);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Iva>>();
      const iva = { id: 123 };
      jest.spyOn(ivaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ iva });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ivaService.update).toHaveBeenCalledWith(iva);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
