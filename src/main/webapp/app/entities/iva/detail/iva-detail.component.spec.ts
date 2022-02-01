import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvaDetailComponent } from './iva-detail.component';

describe('Iva Management Detail Component', () => {
  let comp: IvaDetailComponent;
  let fixture: ComponentFixture<IvaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IvaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ iva: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IvaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IvaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load iva on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.iva).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
