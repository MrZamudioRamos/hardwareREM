import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComponenteDetailComponent } from './componente-detail.component';

describe('Componente Management Detail Component', () => {
  let comp: ComponenteDetailComponent;
  let fixture: ComponentFixture<ComponenteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ComponenteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ componente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ComponenteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ComponenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load componente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.componente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
