import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'application-menu',
        loadChildren: () => import('./application-menu/application-menu.module').then(m => m.BasicCrudApplicationMenuModule)
      },
      {
        path: 'bread-crumb',
        loadChildren: () => import('./bread-crumb/bread-crumb.module').then(m => m.BasicCrudBreadCrumbModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BasicCrudEntityModule {}
