import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlmacen } from '../almacen.model';
import { AlmacenService } from '../service/almacen.service';

@Component({
  templateUrl: './almacen-delete-dialog.component.html',
})
export class AlmacenDeleteDialogComponent {
  almacen?: IAlmacen;

  constructor(protected almacenService: AlmacenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.almacenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
