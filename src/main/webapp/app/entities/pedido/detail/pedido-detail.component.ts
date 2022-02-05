import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';

import { IPedido } from '../pedido.model';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html',
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido | null = null;
  productos?: IProducto[] = [];

  constructor(protected activatedRoute: ActivatedRoute, protected productoService: ProductoService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
      this.loadProductosByPedidoId(pedido);
    });
  }

  previousState(): void {
    window.history.back();
  }

  loadProductosByPedidoId(pedido: IPedido): void {
    this.productoService.findProductosByPedidoId(pedido).subscribe(res => (this.productos = res.body ?? []));
  }
}
