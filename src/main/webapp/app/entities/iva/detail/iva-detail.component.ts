import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIva } from '../iva.model';

@Component({
  selector: 'jhi-iva-detail',
  templateUrl: './iva-detail.component.html',
})
export class IvaDetailComponent implements OnInit {
  iva: IIva | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ iva }) => {
      this.iva = iva;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
