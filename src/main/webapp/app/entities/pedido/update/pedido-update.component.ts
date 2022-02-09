import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPedido, Pedido } from '../pedido.model';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { EmpleadoService } from 'app/entities/empleado/service/empleado.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IAlmacen } from 'app/entities/almacen/almacen.model';
import { AlmacenService } from 'app/entities/almacen/service/almacen.service';
import { IProducto } from 'app/entities/producto/producto.model';
import { ModalProductComponent } from './modal/pedido-modal-productos-list.component';
import dayjs from 'dayjs/esm';
import { ProductoService } from 'app/entities/producto/service/producto.service';
import { PedidoService } from '../service/pedido.service';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html',
})
export class PedidoUpdateComponent implements OnInit {
  isSaving = false;

  facturasCollection: IFactura[] = [];
  empleadosSharedCollection: IEmpleado[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  almacensSharedCollection: IAlmacen[] = [];
  productosInPedidoCollection: IProducto[] = [];

  editForm = this.fb.group({
    id: [],
    direccionEntrega: [null, [Validators.required]],
    fechaPedido: [null, [Validators.required]], // SETEAR AUTOMATICAMENTE
    fechaEntrega: [null, [Validators.required]], // SETEAR AUTOMATICAMENTE + 5 DIAS POR EJ
    descuento: [],
    tipoPago: [null, [Validators.required]], // ENUM
    precioTotal: [null, [Validators.required]], // AUTOMATICO CON UNA FUNCION DE SUMAS
    observaciones: [],
    entregado: [null, [Validators.required]], // FALSE
    enviado: [null, [Validators.required]], // FALSE
    factura: [], // SE DEBERIA DE CREAR UNA FUNCION QUE CREE UNA NUEVA FACTURA A RAIZ DE ESTA
    empleado: [], // SE SETEA CON EL ID DEL USUAIRO QUE HA INICIADO SESION
    empresa: [], // SE SETEA CON EL ID DE LA EMPRESA AUTOMATICAMENTE
    almacen: [], // SE SETEA CON EL ID DEL ALMACEN AUTOMATICAMENTE
    productos: [],
  });

  constructor(
    protected pedidoService: PedidoService,
    protected productoService: ProductoService,
    protected facturaService: FacturaService,
    protected empleadoService: EmpleadoService,
    protected empresaService: EmpresaService,
    protected almacenService: AlmacenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);
      if (pedido.productos) {
        this.productosInPedidoCollection = pedido.productos;
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  open(): void {
    const modalRef = this.modalService.open(ModalProductComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emitService.subscribe((emmitedValue: IProducto[]) => {
      this.productosInPedidoCollection = emmitedValue;
    });
  }

  save(): void {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      pedido.fechaPedido = dayjs();
      pedido.fechaEntrega = dayjs().add(7, 'day');
      // hacer metodo para calcular el precio total.
      pedido.precioTotal = 100;
      pedido.entregado = false;
      pedido.enviado = false;
      pedido.productos = this.productosInPedidoCollection;
      pedido.empleado = this.empleadosSharedCollection[1];
      pedido.empresa = this.empresasSharedCollection[1];
      pedido.almacen = this.almacensSharedCollection[1];

      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  trackFacturaById(index: number, item: IFactura): number {
    return item.id!;
  }

  trackEmpleadoById(index: number, item: IEmpleado): number {
    return item.id!;
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  trackAlmacenById(index: number, item: IAlmacen): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pedido: IPedido): void {
    this.editForm.patchValue({
      id: pedido.id,
      direccionEntrega: pedido.direccionEntrega,
      fechaPedido: pedido.fechaPedido,
      fechaEntrega: pedido.fechaEntrega,
      descuento: pedido.descuento,
      tipoPago: pedido.tipoPago,
      precioTotal: pedido.precioTotal,
      observaciones: pedido.observaciones,
      entregado: pedido.entregado,
      enviado: pedido.enviado,
      factura: pedido.factura,
      empleado: pedido.empleado,
      empresa: pedido.empresa,
      almacen: pedido.almacen,
    });

    this.facturasCollection = this.facturaService.addFacturaToCollectionIfMissing(this.facturasCollection, pedido.factura);
    this.empleadosSharedCollection = this.empleadoService.addEmpleadoToCollectionIfMissing(this.empleadosSharedCollection, pedido.empleado);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, pedido.empresa);
    this.almacensSharedCollection = this.almacenService.addAlmacenToCollectionIfMissing(this.almacensSharedCollection, pedido.almacen);
  }

  protected loadRelationshipsOptions(): void {
    this.facturaService
      .query({ filter: 'pedido-is-null' })
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) => this.facturaService.addFacturaToCollectionIfMissing(facturas, this.editForm.get('factura')!.value))
      )
      .subscribe((facturas: IFactura[]) => (this.facturasCollection = facturas));

    this.empleadoService
      .query()
      .pipe(map((res: HttpResponse<IEmpleado[]>) => res.body ?? []))
      .pipe(
        map((empleados: IEmpleado[]) =>
          this.empleadoService.addEmpleadoToCollectionIfMissing(empleados, this.editForm.get('empleado')!.value)
        )
      )
      .subscribe((empleados: IEmpleado[]) => (this.empleadosSharedCollection = empleados));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing(empresas, this.editForm.get('empresa')!.value))
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.almacenService
      .query()
      .pipe(map((res: HttpResponse<IAlmacen[]>) => res.body ?? []))
      .pipe(
        map((almacens: IAlmacen[]) => this.almacenService.addAlmacenToCollectionIfMissing(almacens, this.editForm.get('almacen')!.value))
      )
      .subscribe((almacens: IAlmacen[]) => (this.almacensSharedCollection = almacens));
  }

  protected createFromForm(): IPedido {
    return {
      ...new Pedido(),
      id: this.editForm.get(['id'])!.value,
      direccionEntrega: this.editForm.get(['direccionEntrega'])!.value,
      fechaPedido: this.editForm.get(['fechaPedido'])!.value,
      fechaEntrega: this.editForm.get(['fechaEntrega'])!.value,
      descuento: this.editForm.get(['descuento'])!.value,
      tipoPago: this.editForm.get(['tipoPago'])!.value,
      precioTotal: this.editForm.get(['precioTotal'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      entregado: this.editForm.get(['entregado'])!.value,
      enviado: this.editForm.get(['enviado'])!.value,
      factura: this.editForm.get(['factura'])!.value,
      empleado: this.editForm.get(['empleado'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      almacen: this.editForm.get(['almacen'])!.value,
    };
  }
}
