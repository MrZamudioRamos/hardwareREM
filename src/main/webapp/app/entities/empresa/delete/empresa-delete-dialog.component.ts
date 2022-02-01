import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmpresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';

@Component({
  templateUrl: './empresa-delete-dialog.component.html',
})
export class EmpresaDeleteDialogComponent {
  empresa?: IEmpresa;

  constructor(protected empresaService: EmpresaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.empresaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
