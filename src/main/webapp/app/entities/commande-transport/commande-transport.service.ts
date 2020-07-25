import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeTransport } from 'app/shared/model/commande-transport.model';

type EntityResponseType = HttpResponse<ICommandeTransport>;
type EntityArrayResponseType = HttpResponse<ICommandeTransport[]>;

@Injectable({ providedIn: 'root' })
export class CommandeTransportService {
  public resourceUrl = SERVER_API_URL + 'api/commande-transports';

  constructor(protected http: HttpClient) {}

  create(commandeTransport: ICommandeTransport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeTransport);
    return this.http
      .post<ICommandeTransport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeTransport: ICommandeTransport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeTransport);
    return this.http
      .put<ICommandeTransport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeTransport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeTransport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeTransport: ICommandeTransport): ICommandeTransport {
    const copy: ICommandeTransport = Object.assign({}, commandeTransport, {
      dateHeure:
        commandeTransport.dateHeure && commandeTransport.dateHeure.isValid() ? commandeTransport.dateHeure.format(DATE_FORMAT) : undefined
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
      res.body.forEach((commandeTransport: ICommandeTransport) => {
        commandeTransport.dateHeure = commandeTransport.dateHeure ? moment(commandeTransport.dateHeure) : undefined;
      });
    }
    return res;
  }
}
