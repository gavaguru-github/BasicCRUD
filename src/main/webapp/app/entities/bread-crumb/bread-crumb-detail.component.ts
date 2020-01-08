import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBreadCrumb } from 'app/shared/model/bread-crumb.model';

@Component({
  selector: 'jhi-bread-crumb-detail',
  templateUrl: './bread-crumb-detail.component.html'
})
export class BreadCrumbDetailComponent implements OnInit {
  breadCrumb: IBreadCrumb | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ breadCrumb }) => {
      this.breadCrumb = breadCrumb;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
