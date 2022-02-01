import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlmacen } from '../almacen.model';

@Component({
  selector: 'jhi-almacen-detail',
  templateUrl: './almacen-detail.component.html',
})
export class AlmacenDetailComponent implements OnInit {
  almacen: IAlmacen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ almacen }) => {
      this.almacen = almacen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
