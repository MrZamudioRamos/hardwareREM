import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProducto } from '../producto.model';
import { ProductoService } from '../service/producto.service';

@Component({
  templateUrl: './producto-delete-dialog.component.html',
})
export class ProductoDeleteDialogComponent {
  producto?: IProducto;

  constructor(protected productoService: ProductoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
