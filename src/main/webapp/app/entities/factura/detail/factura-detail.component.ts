import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactura } from '../factura.model';

@Component({
  selector: 'jhi-factura-detail',
  templateUrl: './factura-detail.component.html',
})
export class FacturaDetailComponent implements OnInit {
  factura: IFactura | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      this.factura = factura;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
