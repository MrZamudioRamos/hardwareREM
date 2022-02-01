import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpleado } from '../empleado.model';

@Component({
  selector: 'jhi-empleado-detail',
  templateUrl: './empleado-detail.component.html',
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: IEmpleado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.empleado = empleado;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
