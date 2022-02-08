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

@Component({
  templateUrl: './modal-facturaPlantilla.component.html',
})
export class ModalPlantillaComponent implements OnInit {
  empresa?: IEmpresa;
  cliente?: ICliente;
  pedido?: IPedido;
  factura?: IFactura;

  constructor(
    protected pedidoService: PedidoService,
    protected activeModal: NgbActiveModal,
    protected empresaService: EmpresaService,
    protected clienteService: ClienteService,
    protected facturaService: FacturaService
  ) {}

  ngOnInit(): void {
    if (this.empresa) {
      this.loadData(this.empresa);
    }
    if (this.pedido) {
      this.loadData(this.pedido);
    }
    if (this.cliente) {
      this.loadData(this.cliente);
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  loadData(cliente: ICliente): void {
    this.facturaService.find;
  }
}
