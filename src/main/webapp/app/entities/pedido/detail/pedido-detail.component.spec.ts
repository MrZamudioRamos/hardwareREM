import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PedidoDetailComponent } from './pedido-detail.component';

describe('Pedido Management Detail Component', () => {
  let comp: PedidoDetailComponent;
  let fixture: ComponentFixture<PedidoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PedidoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pedido: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PedidoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PedidoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pedido on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pedido).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
