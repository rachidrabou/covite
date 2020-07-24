import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandes } from 'app/shared/model/commandes.model';

type EntityResponseType = HttpResponse<ICommandes>;
type EntityArrayResponseType = HttpResponse<ICommandes[]>;

@Injectable({ providedIn: 'root' })
export class CommandesService {
  public resourceUrl = SERVER_API_URL + 'api/commandes';

  constructor(protected http: HttpClient) {}

  create(commandes: ICommandes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandes);
    return this.http
      .post<ICommandes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandes: ICommandes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandes);
    return this.http
      .put<ICommandes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandes: ICommandes): ICommandes {
    const copy: ICommandes = Object.assign({}, commandes, {
      date: commandes.date && commandes.date.isValid() ? commandes.date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandes: ICommandes) => {
        commandes.date = commandes.date ? moment(commandes.date) : undefined;
      });
    }
    return res;
  }
}
