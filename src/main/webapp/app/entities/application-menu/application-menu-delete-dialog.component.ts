import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationMenu } from 'app/shared/model/application-menu.model';
import { ApplicationMenuService } from './application-menu.service';

@Component({
  templateUrl: './application-menu-delete-dialog.component.html'
})
export class ApplicationMenuDeleteDialogComponent {
  applicationMenu?: IApplicationMenu;

  constructor(
    protected applicationMenuService: ApplicationMenuService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicationMenuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicationMenuListModification');
      this.activeModal.close();
    });
  }
}
