import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmpresaDetailComponent } from './empresa-detail.component';

describe('Empresa Management Detail Component', () => {
  let comp: EmpresaDetailComponent;
  let fixture: ComponentFixture<EmpresaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpresaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ empresa: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmpresaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load empresa on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.empresa).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
