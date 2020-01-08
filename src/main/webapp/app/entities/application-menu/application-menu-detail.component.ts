import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationMenu } from 'app/shared/model/application-menu.model';

@Component({
  selector: 'jhi-application-menu-detail',
  templateUrl: './application-menu-detail.component.html'
})
export class ApplicationMenuDetailComponent implements OnInit {
  applicationMenu: IApplicationMenu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationMenu }) => {
      this.applicationMenu = applicationMenu;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
