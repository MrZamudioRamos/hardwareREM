import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPedido } from '../pedido.model';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html',
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
