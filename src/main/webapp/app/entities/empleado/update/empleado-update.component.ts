import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEmpleado, Empleado } from '../empleado.model';
import { EmpleadoService } from '../service/empleado.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

@Component({
  selector: 'jhi-empleado-update',
  templateUrl: './empleado-update.component.html',
})
export class EmpleadoUpdateComponent implements OnInit {
  isSaving = false;

  empresasSharedCollection: IEmpresa[] = [];

  editForm = this.fb.group({
    id: [],
    dni: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    apellidos: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    mail: [null, [Validators.required]],
    pais: [null, [Validators.required]],
    provincia: [null, [Validators.required]],
    codigoPostal: [null, [Validators.required]],
    calle: [null, [Validators.required]],
    numeroPedidos: [],
    tipoContrato: [null, [Validators.required]],
    comision: [],
    activo: [null, [Validators.required]],
    contrasena: [null, [Validators.required]],
    empresa: [],
  });

  constructor(
    protected empleadoService: EmpleadoService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.updateForm(empleado);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empleado = this.createFromForm();
    if (empleado.id !== undefined) {
      this.subscribeToSaveResponse(this.empleadoService.update(empleado));
    } else {
      this.subscribeToSaveResponse(this.empleadoService.create(empleado));
    }
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>): void {
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

  protected updateForm(empleado: IEmpleado): void {
    this.editForm.patchValue({
      id: empleado.id,
      dni: empleado.dni,
      nombre: empleado.nombre,
      apellidos: empleado.apellidos,
      telefono: empleado.telefono,
      mail: empleado.mail,
      pais: empleado.pais,
      provincia: empleado.provincia,
      codigoPostal: empleado.codigoPostal,
      calle: empleado.calle,
      numeroPedidos: empleado.numeroPedidos,
      tipoContrato: empleado.tipoContrato,
      comision: empleado.comision,
      activo: empleado.activo,
      contrasena: empleado.contrasena,
      empresa: empleado.empresa,
    });

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, empleado.empresa);
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

  protected createFromForm(): IEmpleado {
    return {
      ...new Empleado(),
      id: this.editForm.get(['id'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      codigoPostal: this.editForm.get(['codigoPostal'])!.value,
      calle: this.editForm.get(['calle'])!.value,
      numeroPedidos: this.editForm.get(['numeroPedidos'])!.value,
      tipoContrato: this.editForm.get(['tipoContrato'])!.value,
      comision: this.editForm.get(['comision'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      contrasena: this.editForm.get(['contrasena'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
    };
  }
}
