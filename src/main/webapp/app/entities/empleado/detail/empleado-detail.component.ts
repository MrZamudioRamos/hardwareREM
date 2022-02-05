import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

import { IEmpleado } from '../empleado.model';
import { ModalComponent } from '../list/modal/modal-pedidos-por-empleado-component';

@Component({
  selector: 'jhi-empleado-detail',
  templateUrl: './empleado-detail.component.html',
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: IEmpleado | null = null;
  pedidosEmpleado: IPedido[] = [];

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected pedidoService: PedidoService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.empleado = empleado;
    });
  }

  buscarPorEmpleado(empleado: IEmpleado): void {
    this.pedidoService.buscarPorEmpleado(empleado).subscribe({
      next: (res: HttpResponse<IPedido[]>) => {
        this.pedidosEmpleado = res.body ?? [];
      },
    });
  }

  previousState(): void {
    window.history.back();
  }

  open(empleado: IEmpleado): void {
    const modalRef = this.modalService.open(ModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.empleado = empleado;
  }
}
