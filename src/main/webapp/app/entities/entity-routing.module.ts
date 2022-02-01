import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'empresa',
        data: { pageTitle: 'hardwareRemApp.empresa.home.title' },
        loadChildren: () => import('./empresa/empresa.module').then(m => m.EmpresaModule),
      },
      {
        path: 'almacen',
        data: { pageTitle: 'hardwareRemApp.almacen.home.title' },
        loadChildren: () => import('./almacen/almacen.module').then(m => m.AlmacenModule),
      },
      {
        path: 'producto',
        data: { pageTitle: 'hardwareRemApp.producto.home.title' },
        loadChildren: () => import('./producto/producto.module').then(m => m.ProductoModule),
      },
      {
        path: 'componente',
        data: { pageTitle: 'hardwareRemApp.componente.home.title' },
        loadChildren: () => import('./componente/componente.module').then(m => m.ComponenteModule),
      },
      {
        path: 'pedido',
        data: { pageTitle: 'hardwareRemApp.pedido.home.title' },
        loadChildren: () => import('./pedido/pedido.module').then(m => m.PedidoModule),
      },
      {
        path: 'cliente',
        data: { pageTitle: 'hardwareRemApp.cliente.home.title' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'empleado',
        data: { pageTitle: 'hardwareRemApp.empleado.home.title' },
        loadChildren: () => import('./empleado/empleado.module').then(m => m.EmpleadoModule),
      },
      {
        path: 'factura',
        data: { pageTitle: 'hardwareRemApp.factura.home.title' },
        loadChildren: () => import('./factura/factura.module').then(m => m.FacturaModule),
      },
      {
        path: 'iva',
        data: { pageTitle: 'hardwareRemApp.iva.home.title' },
        loadChildren: () => import('./iva/iva.module').then(m => m.IvaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
