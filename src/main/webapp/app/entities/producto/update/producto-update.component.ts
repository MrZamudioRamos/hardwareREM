import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProducto, Producto } from '../producto.model';
import { ProductoService } from '../service/producto.service';
import { IIva } from 'app/entities/iva/iva.model';
import { IvaService } from 'app/entities/iva/service/iva.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';
import { IAlmacen } from 'app/entities/almacen/almacen.model';
import { AlmacenService } from 'app/entities/almacen/service/almacen.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html',
})
export class ProductoUpdateComponent implements OnInit {
  isSaving = false;

  ivasCollection: IIva[] = [];
  empresasSharedCollection: IEmpresa[] = [];
  pedidosSharedCollection: IPedido[] = [];
  almacensSharedCollection: IAlmacen[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: new FormControl(null, [Validators.minLength(3), Validators.required]),
    numeroSerie: new FormControl(null, [Validators.min(1), Validators.required]),
    marca: new FormControl(null, [Validators.minLength(3), Validators.required]),
    modelo: new FormControl(null, [Validators.minLength(3), Validators.required]),
    descripcion: new FormControl(null, [Validators.minLength(3), Validators.required]),
    peso: [null, [Validators.required]],
    fechaVenta: [],
    precioCompra: [null, [Validators.required]],
    precioBruto: [null, [Validators.required]],
    vendido: [null, [Validators.required]],
    iva: [],
    empresa: [],
    pedido: [],
    almacen: [],
  });
  // Enum moneda

  constructor(
    protected productoService: ProductoService,
    protected ivaService: IvaService,
    protected empresaService: EmpresaService,
    protected pedidoService: PedidoService,
    protected almacenService: AlmacenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  trackIvaById(index: number, item: IIva): number {
    return item.id!;
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  trackPedidoById(index: number, item: IPedido): number {
    return item.id!;
  }

  trackAlmacenById(index: number, item: IAlmacen): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>): void {
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

  protected updateForm(producto: IProducto): void {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      numeroSerie: producto.numeroSerie,
      marca: producto.marca,
      modelo: producto.modelo,
      descripcion: producto.descripcion,
      peso: producto.peso,
      fechaVenta: producto.fechaVenta,
      precioCompra: producto.precioCompra,
      precioBruto: producto.precioBruto,
      precioIva: producto.precioIva,
      vendido: producto.vendido,
      iva: producto.iva,
      empresa: producto.empresa,
      pedido: producto.pedido,
      almacen: producto.almacen,
    });

    this.ivasCollection = this.ivaService.addIvaToCollectionIfMissing(this.ivasCollection, producto.iva);
    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, producto.empresa);
    this.pedidosSharedCollection = this.pedidoService.addPedidoToCollectionIfMissing(this.pedidosSharedCollection, producto.pedido);
    this.almacensSharedCollection = this.almacenService.addAlmacenToCollectionIfMissing(this.almacensSharedCollection, producto.almacen);
  }

  protected loadRelationshipsOptions(): void {
    this.ivaService
      .query({ filter: 'producto-is-null' })
      .pipe(map((res: HttpResponse<IIva[]>) => res.body ?? []))
      .pipe(map((ivas: IIva[]) => this.ivaService.addIvaToCollectionIfMissing(ivas, this.editForm.get('iva')!.value)))
      .subscribe((ivas: IIva[]) => (this.ivasCollection = ivas));

    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing(empresas, this.editForm.get('empresa')!.value))
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));

    this.pedidoService
      .query()
      .pipe(map((res: HttpResponse<IPedido[]>) => res.body ?? []))
      .pipe(map((pedidos: IPedido[]) => this.pedidoService.addPedidoToCollectionIfMissing(pedidos, this.editForm.get('pedido')!.value)))
      .subscribe((pedidos: IPedido[]) => (this.pedidosSharedCollection = pedidos));

    this.almacenService
      .query()
      .pipe(map((res: HttpResponse<IAlmacen[]>) => res.body ?? []))
      .pipe(
        map((almacens: IAlmacen[]) => this.almacenService.addAlmacenToCollectionIfMissing(almacens, this.editForm.get('almacen')!.value))
      )
      .subscribe((almacens: IAlmacen[]) => (this.almacensSharedCollection = almacens));
  }

  protected createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      numeroSerie: this.editForm.get(['numeroSerie'])!.value,
      marca: this.editForm.get(['marca'])!.value,
      modelo: this.editForm.get(['modelo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      fechaVenta: this.editForm.get(['fechaVenta'])!.value,
      precioCompra: this.editForm.get(['precioCompra'])!.value,
      precioBruto: this.editForm.get(['precioBruto'])!.value,
      precioIva: this.editForm.get(['precioIva'])!.value,
      vendido: this.editForm.get(['vendido'])!.value,
      iva: this.editForm.get(['iva'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      pedido: this.editForm.get(['pedido'])!.value,
      almacen: this.editForm.get(['almacen'])!.value,
    };
  }
}
