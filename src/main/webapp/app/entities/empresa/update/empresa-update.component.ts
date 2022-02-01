import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEmpresa, Empresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html',
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombreSocial: [null, [Validators.required]],
    cif: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    mail: [null, [Validators.required]],
    pais: [null, [Validators.required]],
    provincia: [null, [Validators.required]],
    sucursal: [null, [Validators.required]],
    codigoPostal: [null, [Validators.required]],
    calle: [null, [Validators.required]],
    categoria: [null, [Validators.required]],
    fechaCreacion: [null, [Validators.required]],
  });

  constructor(protected empresaService: EmpresaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>): void {
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

  protected updateForm(empresa: IEmpresa): void {
    this.editForm.patchValue({
      id: empresa.id,
      nombreSocial: empresa.nombreSocial,
      cif: empresa.cif,
      telefono: empresa.telefono,
      mail: empresa.mail,
      pais: empresa.pais,
      provincia: empresa.provincia,
      sucursal: empresa.sucursal,
      codigoPostal: empresa.codigoPostal,
      calle: empresa.calle,
      categoria: empresa.categoria,
      fechaCreacion: empresa.fechaCreacion,
    });
  }

  protected createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id'])!.value,
      nombreSocial: this.editForm.get(['nombreSocial'])!.value,
      cif: this.editForm.get(['cif'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      provincia: this.editForm.get(['provincia'])!.value,
      sucursal: this.editForm.get(['sucursal'])!.value,
      codigoPostal: this.editForm.get(['codigoPostal'])!.value,
      calle: this.editForm.get(['calle'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      fechaCreacion: this.editForm.get(['fechaCreacion'])!.value,
    };
  }
}