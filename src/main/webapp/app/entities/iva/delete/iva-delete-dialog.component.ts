import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIva } from '../iva.model';
import { IvaService } from '../service/iva.service';

@Component({
  templateUrl: './iva-delete-dialog.component.html',
})
export class IvaDeleteDialogComponent {
  iva?: IIva;

  constructor(protected ivaService: IvaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ivaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
