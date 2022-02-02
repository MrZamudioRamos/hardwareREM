import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAlmacen, Almacen } from '../almacen.model';
import { AlmacenService } from '../service/almacen.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

@Component({
  selector: 'jhi-almacen-update',
  templateUrl: './almacen-update.component.html',
})
export class AlmacenUpdateComponent implements OnInit {
  isSaving = false;

  empresasSharedCollection: IEmpresa[] = [];

  editForm = this.fb.group({
    id: [],
    telefono: new FormControl(null, [Validators.minLength(9), Validators.required]),
    pais: new FormControl(null, [Validators.minLength(3), Validators.required]),
    provincia: new FormControl(null, [Validators.minLength(3), Validators.required]),
    sucursal: new FormControl(null, [Validators.minLength(3), Validators.required]),
    codigoPostal: new FormControl(null, [Validators.minLength(5), Validators.required]),
    calle: new FormControl(null, [Validators.minLength(3), Validators.required]),
    albaran: new FormControl(null, [Validators.minLength(3), Validators.required]),
    stockProductos: [],
    empresa: [],
  });

  constructor(
    protected almacenService: AlmacenService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ almacen }) => {
      this.updateForm(almacen);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const almacen = this.createFromForm();
    if (almacen.id !== undefined) {
      this.subscribeToSaveResponse(this.almacenService.update(almacen));
    } else {
      this.subscribeToSaveResponse(this.almacenService.create(almacen));
    }
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlmacen>>): void {
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

  protected updateForm(almacen: IAlmacen): void {
    this.editForm.patchValue({
      id: almacen.id,
      telefono: almacen.telefono,
      pais: almacen.pais,
      provincia: almacen.provincia,
      sucursal: almacen.sucursal,
      codigoPostal: almacen.codigoPostal,
      calle: almacen.calle,
      albaran: almacen.albaran,
      stockProductos: almacen.stockProductos,
      empresa: almacen.empresa,
    });

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, almacen.empresa);
  }

  protected loadRelationshipsOptions(): void {
    this.empresaService
      .query()
      .pipe(map((res: HttpResponse<IEmpresa[]>) => res.body ?? []))
      .pipe(
        map((empresas: IEmpresa[]) => this.empresaService.addEmpresaToCollectionIfMissing(empresas, this.editForm.get('empresa')!.value))
      )
      .subscribe((empresas: IEmpresa[]) => (this.empresasSharedCollection = empresas));
  }

  protected createFromForm(): IAlmacen {
    return {
      ...new Almacen(),
      id: this.editForm.get(['id'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      sucursal: this.editForm.get(['sucursal'])!.value,
      codigoPostal: this.editForm.get(['codigoPostal'])!.value,
      calle: this.editForm.get(['calle'])!.value,
      albaran: this.editForm.get(['albaran'])!.value,
      stockProductos: this.editForm.get(['stockProductos'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
    };
  }
}
