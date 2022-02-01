import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComponente } from '../componente.model';

@Component({
  selector: 'jhi-componente-detail',
  templateUrl: './componente-detail.component.html',
})
export class ComponenteDetailComponent implements OnInit {
  componente: IComponente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ componente }) => {
      this.componente = componente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
