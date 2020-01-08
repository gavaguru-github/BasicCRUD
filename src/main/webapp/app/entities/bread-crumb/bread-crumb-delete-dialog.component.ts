import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBreadCrumb } from 'app/shared/model/bread-crumb.model';
import { BreadCrumbService } from './bread-crumb.service';

@Component({
  templateUrl: './bread-crumb-delete-dialog.component.html'
})
export class BreadCrumbDeleteDialogComponent {
  breadCrumb?: IBreadCrumb;

  constructor(
    protected breadCrumbService: BreadCrumbService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.breadCrumbService.delete(id).subscribe(() => {
      this.eventManager.broadcast('breadCrumbListModification');
      this.activeModal.close();
    });
  }
}
