import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBreadCrumb, BreadCrumb } from 'app/shared/model/bread-crumb.model';
import { BreadCrumbService } from './bread-crumb.service';
import { BreadCrumbComponent } from './bread-crumb.component';
import { BreadCrumbDetailComponent } from './bread-crumb-detail.component';
import { BreadCrumbUpdateComponent } from './bread-crumb-update.component';

@Injectable({ providedIn: 'root' })
export class BreadCrumbResolve implements Resolve<IBreadCrumb> {
  constructor(private service: BreadCrumbService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBreadCrumb> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((breadCrumb: HttpResponse<BreadCrumb>) => {
          if (breadCrumb.body) {
            return of(breadCrumb.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BreadCrumb());
  }
}

export const breadCrumbRoute: Routes = [
  {
    path: '',
    component: BreadCrumbComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'basicCrudApp.breadCrumb.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BreadCrumbDetailComponent,
    resolve: {
      breadCrumb: BreadCrumbResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.breadCrumb.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BreadCrumbUpdateComponent,
    resolve: {
      breadCrumb: BreadCrumbResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.breadCrumb.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BreadCrumbUpdateComponent,
    resolve: {
      breadCrumb: BreadCrumbResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'basicCrudApp.breadCrumb.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
