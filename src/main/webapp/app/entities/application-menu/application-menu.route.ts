import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApplicationMenu, ApplicationMenu } from 'app/shared/model/application-menu.model';
import { ApplicationMenuService } from './application-menu.service';
import { ApplicationMenuComponent } from './application-menu.component';
import { ApplicationMenuDetailComponent } from './application-menu-detail.component';
import { ApplicationMenuUpdateComponent } from './application-menu-update.component';

@Injectable({ providedIn: 'root' })
export class ApplicationMenuResolve implements Resolve<IApplicationMenu> {
  constructor(private service: ApplicationMenuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApplicationMenu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((applicationMenu: HttpResponse<ApplicationMenu>) => {
          if (applicationMenu.body) {
            return of(applicationMenu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApplicationMenu());
  }
}

export const applicationMenuRoute: Routes = [
  {
    path: '',
    component: ApplicationMenuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.applicationMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationMenuDetailComponent,
    resolve: {
      applicationMenu: ApplicationMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.applicationMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationMenuUpdateComponent,
    resolve: {
      applicationMenu: ApplicationMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.applicationMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationMenuUpdateComponent,
    resolve: {
      applicationMenu: ApplicationMenuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.applicationMenu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
