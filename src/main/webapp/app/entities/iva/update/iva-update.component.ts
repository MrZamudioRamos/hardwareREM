import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IIva, Iva } from '../iva.model';
import { IvaService } from '../service/iva.service';

@Component({
  selector: 'jhi-iva-update',
  templateUrl: './iva-update.component.html',
})
export class IvaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    tipo: [],
    valor: [],
  });

  constructor(protected ivaService: IvaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ iva }) => {
      this.updateForm(iva);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const iva = this.createFromForm();
    if (iva.id !== undefined) {
      this.subscribeToSaveResponse(this.ivaService.update(iva));
    } else {
      this.subscribeToSaveResponse(this.ivaService.create(iva));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIva>>): void {
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

  protected updateForm(iva: IIva): void {
    this.editForm.patchValue({
      id: iva.id,
      nombre: iva.nombre,
      tipo: iva.tipo,
      valor: iva.valor,
    });
  }

  protected createFromForm(): IIva {
    return {
      ...new Iva(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      valor: this.editForm.get(['valor'])!.value,
    };
  }
}
