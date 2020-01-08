import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBreadCrumb } from 'app/shared/model/bread-crumb.model';

type EntityResponseType = HttpResponse<IBreadCrumb>;
type EntityArrayResponseType = HttpResponse<IBreadCrumb[]>;

@Injectable({ providedIn: 'root' })
export class BreadCrumbService {
  public resourceUrl = SERVER_API_URL + 'api/bread-crumbs';

  constructor(protected http: HttpClient) {}

  create(breadCrumb: IBreadCrumb): Observable<EntityResponseType> {
    return this.http.post<IBreadCrumb>(this.resourceUrl, breadCrumb, { observe: 'response' });
  }

  update(breadCrumb: IBreadCrumb): Observable<EntityResponseType> {
    return this.http.put<IBreadCrumb>(this.resourceUrl, breadCrumb, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBreadCrumb>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBreadCrumb[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
