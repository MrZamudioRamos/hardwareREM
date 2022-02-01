import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IComponente } from '../componente.model';
import { ComponenteService } from '../service/componente.service';

@Component({
  templateUrl: './componente-delete-dialog.component.html',
})
export class ComponenteDeleteDialogComponent {
  componente?: IComponente;

  constructor(protected componenteService: ComponenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.componenteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
