import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IProducto } from 'app/entities/producto/producto.model';
import { ProductoService } from 'app/entities/producto/service/producto.service';

@Component({
  templateUrl: './pedido-modal-productos-list.component.html',
})
export class ModalProductComponent implements OnInit {
  productos?: IProducto[] = [];
  productsToAddPedido?: IProducto[] = [];
  @Output() emitService = new EventEmitter();

  constructor(protected productoService: ProductoService, protected activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    this.loadData();
  }

  cancel(): void {
    this.activeModal.dismiss();
    this.emitService.next(this.productsToAddPedido);
  }

  addProduct(producto: IProducto): void {
    const index = this.productos!.indexOf(producto);
    this.productos!.splice(index, 1);
    this.productsToAddPedido?.push(producto);
  }

  loadData(): void {
    this.productoService.getListProducts().subscribe(res => (this.productos = res.body ?? []));
  }
}
