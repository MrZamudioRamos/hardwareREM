import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';
import { IEmpleado } from '../../empleado.model';
import { EmpleadoService } from '../../service/empleado.service';

@Component({
  templateUrl: './modal-pedidos-por-empleado-component.html',
})
export class ModalComponent implements OnInit {
  empleado?: IEmpleado;
  pedidosEmpleado?: IPedido[] = [];

  constructor(protected pedidoService: PedidoService, protected activeModal: NgbActiveModal, protected empleadoService: EmpleadoService) {}

  ngOnInit(): void {
    if (this.empleado) {
      this.loadData(this.empleado);
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  loadData(empleado: IEmpleado): void {
    this.pedidoService.buscarPorEmpleado(empleado).subscribe(res => (this.pedidosEmpleado = res.body ?? []));
  }
}
