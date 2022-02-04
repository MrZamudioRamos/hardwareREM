import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFactura, Factura } from '../factura.model';
import { FacturaService } from '../service/factura.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

@Component({
  selector: 'jhi-factura-update',
  templateUrl: './factura-update.component.html',
})
export class FacturaUpdateComponent implements OnInit {
  isSaving = false;

  clientesSharedCollection: ICliente[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  pedidosSharedCollection: IPedido[] = [];

  editForm = this.fb.group({
    id: [],
    fechaFacturacion: [null, [Validators.required]],
    fechaVencimiento: [null, [Validators.required]],
    cliente: [],
    empresa: [],
    pedido: [],
  });

  constructor(
    protected facturaService: FacturaService,
    protected clienteService: ClienteService,
    protected empresaService: EmpresaService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      this.updateForm(factura);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factura = this.createFromForm();
    if (factura.id !== undefined) {
      this.subscribeToSaveResponse(this.facturaService.update(factura));
    } else {
      this.subscribeToSaveResponse(this.facturaService.create(factura));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }
  trackPedidoById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactura>>): void {
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

  protected updateForm(factura: IFactura): void {
    this.editForm.patchValue({
      id: factura.id,
      fechaFacturacion: factura.fechaFacturacion,
      fechaVencimiento: factura.fechaVencimiento,
      cliente: factura.cliente,
      empresa: factura.empresa,
      pedido: factura.pedido,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, factura.cliente);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, factura.empresa);
    this.pedidosSharedCollection = this.pedidoService.addPedidoToCollectionIfMissing(this.pedidosSharedCollection, factura.pedido);
  }

  protected loadRelationshipsOptions(): void {
    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing(empresas, this.editForm.get('empresa')!.value))
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.pedidoService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(map((pedidos: IPedido[]) => this.pedidoService.addPedidoToCollectionIfMissing(pedidos, this.editForm.get('pedido')!.value)))
      .subscribe((pedidos: IPedido[]) => (this.pedidosSharedCollection = pedidos));
  }

  protected createFromForm(): IFactura {
    return {
      ...new Factura(),
      id: this.editForm.get(['id'])!.value,
      fechaFacturacion: this.editForm.get(['fechaFacturacion'])!.value,
      fechaVencimiento: this.editForm.get(['fechaVencimiento'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      pedido: this.editForm.get(['pedido'])!.value,
    };
  }
}
