import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProducto } from '../producto.model';

@Component({
  selector: 'jhi-producto-detail',
  templateUrl: './producto-detail.component.html',
})
export class ProductoDetailComponent implements OnInit {
  producto: IProducto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.producto = producto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
