import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmpleadoDetailComponent } from './empleado-detail.component';

describe('Empleado Management Detail Component', () => {
  let comp: EmpleadoDetailComponent;
  let fixture: ComponentFixture<EmpleadoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ empleado: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmpleadoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmpleadoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empleado on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.empleado).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
