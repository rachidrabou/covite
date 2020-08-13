import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifLivraison } from 'app/shared/model/tarif-livraison.model';

type EntityResponseType = HttpResponse<ITarifLivraison>;
type EntityArrayResponseType = HttpResponse<ITarifLivraison[]>;

@Injectable({ providedIn: 'root' })
export class TarifLivraisonService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-livraisons';

  constructor(protected http: HttpClient) {}

  create(tarifLivraison: ITarifLivraison): Observable<EntityResponseType> {
    return this.http.post<ITarifLivraison>(this.resourceUrl, tarifLivraison, { observe: 'response' });
  }

  update(tarifLivraison: ITarifLivraison): Observable<EntityResponseType> {
    return this.http.put<ITarifLivraison>(this.resourceUrl, tarifLivraison, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifLivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifLivraison[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
