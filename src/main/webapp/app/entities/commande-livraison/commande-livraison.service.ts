import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';

type EntityResponseType = HttpResponse<ICommandeLivraison>;
type EntityArrayResponseType = HttpResponse<ICommandeLivraison[]>;

@Injectable({ providedIn: 'root' })
export class CommandeLivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/commande-livraisons';

  constructor(protected http: HttpClient) {}

  create(commandeLivraison: ICommandeLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLivraison);
    return this.http
      .post<ICommandeLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeLivraison: ICommandeLivraison): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLivraison);
    return this.http
      .put<ICommandeLivraison>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeLivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeLivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeLivraison: ICommandeLivraison): ICommandeLivraison {
    const copy: ICommandeLivraison = Object.assign({}, commandeLivraison, {
      dateHeure:
        commandeLivraison.dateHeure && commandeLivraison.dateHeure.isValid() ? commandeLivraison.dateHeure.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateHeure = res.body.dateHeure ? moment(res.body.dateHeure) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeLivraison: ICommandeLivraison) => {
        commandeLivraison.dateHeure = commandeLivraison.dateHeure ? moment(commandeLivraison.dateHeure) : undefined;
      });
    }
    return res;
  }
}
