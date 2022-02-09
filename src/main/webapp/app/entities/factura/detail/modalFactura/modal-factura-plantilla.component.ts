import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

import { IFactura } from '../../factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';

import { ActivatedRoute } from '@angular/router';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';

@Component({
  templateUrl: './modal-factura-plantilla.component.html',
})
export class ModalPlantillaComponent implements OnInit {
  empresa?: IEmpresa;
  cliente?: ICliente;
  factura: IFactura | null = null;

  pedido: IPedido | null = null;
  productos?: IProducto[] = [];
  precioTotal = 0;
  precioTotalSinIva = 0;

  constructor(
    protected pedidoService: PedidoService,
    protected activeModal: NgbActiveModal,
    protected empresaService: EmpresaService,
    protected clienteService: ClienteService,
    protected facturaService: FacturaService,
    protected activatedRoute: ActivatedRoute,
    protected productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.loadProductosByPedidoId(this.factura?.pedido);
  }
  loadProductosByPedidoId(pedido: IPedido | null | undefined): void {
    this.productoService.findProductosByPedidoId(pedido!).subscribe(res => (this.productos = res.body ?? []));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
