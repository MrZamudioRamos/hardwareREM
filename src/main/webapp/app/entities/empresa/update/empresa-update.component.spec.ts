import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmpresaService } from '../service/empresa.service';
import { IEmpresa, Empresa } from '../empresa.model';

import { EmpresaUpdateComponent } from './empresa-update.component';

describe('Empresa Management Update Component', () => {
  let comp: EmpresaUpdateComponent;
  let fixture: ComponentFixture<EmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmpresaUpdateComponent],
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
      .overrideTemplate(EmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const empresa: IEmpresa = { id: 456 };

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(empresa));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Empresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaService.update).toHaveBeenCalledWith(empresa);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Empresa>>();
      const empresa = new Empresa();
      jest.spyOn(empresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaService.create).toHaveBeenCalledWith(empresa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Empresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaService.update).toHaveBeenCalledWith(empresa);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
