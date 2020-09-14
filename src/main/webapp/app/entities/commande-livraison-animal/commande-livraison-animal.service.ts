import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';

type EntityResponseType = HttpResponse<ICommandeLivraisonAnimal>;
type EntityArrayResponseType = HttpResponse<ICommandeLivraisonAnimal[]>;

@Injectable({ providedIn: 'root' })
export class CommandeLivraisonAnimalService {
  public resourceUrl = SERVER_API_URL + 'api/commande-livraison-animals';

  constructor(protected http: HttpClient) {}

  create(commandeLivraisonAnimal: ICommandeLivraisonAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLivraisonAnimal);
    return this.http
      .post<ICommandeLivraisonAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeLivraisonAnimal: ICommandeLivraisonAnimal): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeLivraisonAnimal);
    return this.http
      .put<ICommandeLivraisonAnimal>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeLivraisonAnimal>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeLivraisonAnimal[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeLivraisonAnimal: ICommandeLivraisonAnimal): ICommandeLivraisonAnimal {
    const copy: ICommandeLivraisonAnimal = Object.assign({}, commandeLivraisonAnimal, {
      dateheure:
        commandeLivraisonAnimal.dateheure && commandeLivraisonAnimal.dateheure.isValid()
          ? commandeLivraisonAnimal.dateheure.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateheure = res.body.dateheure ? moment(res.body.dateheure) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeLivraisonAnimal: ICommandeLivraisonAnimal) => {
        commandeLivraisonAnimal.dateheure = commandeLivraisonAnimal.dateheure ? moment(commandeLivraisonAnimal.dateheure) : undefined;
      });
    }
    return res;
  }
}
