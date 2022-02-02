import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IComponente, Componente } from '../componente.model';
import { ComponenteService } from '../service/componente.service';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';

@Component({
  selector: 'jhi-componente-update',
  templateUrl: './componente-update.component.html',
})
export class ComponenteUpdateComponent implements OnInit {
  isSaving = false;

  productosSharedCollection: IProducto[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: new FormControl(null, [Validators.minLength(3), Validators.required]),
    numeroSerie: new FormControl(null, [Validators.min(1), Validators.required]),
    marca: new FormControl(null, [Validators.minLength(3), Validators.required]),
    modelo: new FormControl(null, [Validators.minLength(3), Validators.required]),
    descripcion: new FormControl(null, [Validators.minLength(3), Validators.required]),
    peso: [null, [Validators.required]],
    producto: [],
  });

  constructor(
    protected componenteService: ComponenteService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ componente }) => {
      this.updateForm(componente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const componente = this.createFromForm();
    if (componente.id !== undefined) {
      this.subscribeToSaveResponse(this.componenteService.update(componente));
    } else {
      this.subscribeToSaveResponse(this.componenteService.create(componente));
    }
  }

  trackProductoById(index: number, item: IProducto): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComponente>>): void {
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

  protected updateForm(componente: IComponente): void {
    this.editForm.patchValue({
      id: componente.id,
      nombre: componente.nombre,
      numeroSerie: componente.numeroSerie,
      marca: componente.marca,
      modelo: componente.modelo,
      descripcion: componente.descripcion,
      peso: componente.peso,
      producto: componente.producto,
    });

    this.productosSharedCollection = this.productoService.addProductoToCollectionIfMissing(
      this.productosSharedCollection,
      componente.producto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productoService
      .query()
      .pipe(map((res: HttpResponse<IProducto[]>) => res.body ?? []))
      .pipe(
        map((productos: IProducto[]) =>
          this.productoService.addProductoToCollectionIfMissing(productos, this.editForm.get('producto')!.value)
        )
      )
      .subscribe((productos: IProducto[]) => (this.productosSharedCollection = productos));
  }

  protected createFromForm(): IComponente {
    return {
      ...new Componente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      numeroSerie: this.editForm.get(['numeroSerie'])!.value,
      marca: this.editForm.get(['marca'])!.value,
      modelo: this.editForm.get(['modelo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      peso: this.editForm.get(['peso'])!.value,
      producto: this.editForm.get(['producto'])!.value,
    };
  }
}
