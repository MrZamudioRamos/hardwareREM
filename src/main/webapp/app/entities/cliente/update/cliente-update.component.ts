import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICliente, Cliente } from '../cliente.model';
import { ClienteService } from '../service/cliente.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;

  empresasSharedCollection: IEmpresa[] = [];

  editForm = this.fb.group({
    id: [],
    dni: new FormControl(null, [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
    nombre: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
    apellidos: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
    telefono: new FormControl(null, [Validators.minLength(9), Validators.maxLength(15), Validators.required]),
    mail: new FormControl(null, [Validators.required, Validators.email]),
    pais: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(150)]),
    provincia: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(150)]),
    codigoPostal: new FormControl(null, [Validators.required, Validators.minLength(5), Validators.maxLength(9)]),
    calle: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(150)]),
    fidelizado: [null, [Validators.required]],
    activo: [null, [Validators.required]],
    empresa: [],
  });

  constructor(
    protected clienteService: ClienteService,
    protected empresaService: EmpresaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  trackEmpresaById(index: number, item: IEmpresa): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  protected updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      dni: cliente.dni,
      nombre: cliente.nombre,
      apellidos: cliente.apellidos,
      telefono: cliente.telefono,
      mail: cliente.mail,
      pais: cliente.pais,
      provincia: cliente.provincia,
      codigoPostal: cliente.codigoPostal,
      calle: cliente.calle,
      fidelizado: cliente.fidelizado,
      activo: cliente.activo,
      empresa: cliente.empresa,
    });

    this.empresasSharedCollection = this.empresaService.addEmpresaToCollectionIfMissing(this.empresasSharedCollection, cliente.empresa);
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

  protected createFromForm(): ICliente {
    return {
      ...new Cliente(),
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
      fidelizado: this.editForm.get(['fidelizado'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
    };
  }
}
