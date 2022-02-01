import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AlmacenDetailComponent } from './almacen-detail.component';

describe('Almacen Management Detail Component', () => {
  let comp: AlmacenDetailComponent;
  let fixture: ComponentFixture<AlmacenDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AlmacenDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ almacen: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AlmacenDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AlmacenDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load almacen on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.almacen).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
