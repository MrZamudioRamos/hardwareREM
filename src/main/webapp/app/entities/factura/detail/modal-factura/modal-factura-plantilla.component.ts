import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

import { IFactura } from '../../factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';

// import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  templateUrl: './modal-factura-plantilla.component.html',
})
export class ModalPlantillaComponent {
  empresa?: IEmpresa;
  cliente?: ICliente;
  pedido?: IPedido;
  factura: IFactura | null = null;

  constructor(
    protected pedidoService: PedidoService,
    protected activeModal: NgbActiveModal,
    protected empresaService: EmpresaService,
    protected clienteService: ClienteService,
    protected facturaService: FacturaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }
}
