import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductoDetailComponent } from './producto-detail.component';

describe('Producto Management Detail Component', () => {
  let comp: ProductoDetailComponent;
  let fixture: ComponentFixture<ProductoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ producto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load producto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.producto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
