import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BasicCrudSharedModule } from 'app/shared/shared.module';
import { BreadCrumbComponent } from './bread-crumb.component';
import { BreadCrumbDetailComponent } from './bread-crumb-detail.component';
import { BreadCrumbUpdateComponent } from './bread-crumb-update.component';
import { BreadCrumbDeleteDialogComponent } from './bread-crumb-delete-dialog.component';
import { breadCrumbRoute } from './bread-crumb.route';

@NgModule({
  imports: [BasicCrudSharedModule, RouterModule.forChild(breadCrumbRoute)],
  declarations: [BreadCrumbComponent, BreadCrumbDetailComponent, BreadCrumbUpdateComponent, BreadCrumbDeleteDialogComponent],
  entryComponents: [BreadCrumbDeleteDialogComponent]
})
export class BasicCrudBreadCrumbModule {}
