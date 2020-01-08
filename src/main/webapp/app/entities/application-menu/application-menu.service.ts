import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApplicationMenu } from 'app/shared/model/application-menu.model';

type EntityResponseType = HttpResponse<IApplicationMenu>;
type EntityArrayResponseType = HttpResponse<IApplicationMenu[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationMenuService {
  public resourceUrl = SERVER_API_URL + 'api/application-menus';

  constructor(protected http: HttpClient) {}

  create(applicationMenu: IApplicationMenu): Observable<EntityResponseType> {
    return this.http.post<IApplicationMenu>(this.resourceUrl, applicationMenu, { observe: 'response' });
  }

  update(applicationMenu: IApplicationMenu): Observable<EntityResponseType> {
    return this.http.put<IApplicationMenu>(this.resourceUrl, applicationMenu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApplicationMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApplicationMenu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
