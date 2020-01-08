import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BasicCrudSharedModule } from 'app/shared/shared.module';
import { ApplicationMenuComponent } from './application-menu.component';
import { ApplicationMenuDetailComponent } from './application-menu-detail.component';
import { ApplicationMenuUpdateComponent } from './application-menu-update.component';
import { ApplicationMenuDeleteDialogComponent } from './application-menu-delete-dialog.component';
import { applicationMenuRoute } from './application-menu.route';

@NgModule({
  imports: [BasicCrudSharedModule, RouterModule.forChild(applicationMenuRoute)],
  declarations: [
    ApplicationMenuComponent,
    ApplicationMenuDetailComponent,
    ApplicationMenuUpdateComponent,
    ApplicationMenuDeleteDialogComponent
  ],
  entryComponents: [ApplicationMenuDeleteDialogComponent]
})
export class BasicCrudApplicationMenuModule {}
