import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

type EntityResponseType = HttpResponse<ITarifTransportAnimal>;
type EntityArrayResponseType = HttpResponse<ITarifTransportAnimal[]>;

@Injectable({ providedIn: 'root' })
export class TarifTransportAnimalService {
  public resourceUrl = SERVER_API_URL + 'api/tarif-transport-animals';

  constructor(protected http: HttpClient) {}

  create(tarifTransportAnimal: ITarifTransportAnimal): Observable<EntityResponseType> {
    return this.http.post<ITarifTransportAnimal>(this.resourceUrl, tarifTransportAnimal, { observe: 'response' });
  }

  update(tarifTransportAnimal: ITarifTransportAnimal): Observable<EntityResponseType> {
    return this.http.put<ITarifTransportAnimal>(this.resourceUrl, tarifTransportAnimal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITarifTransportAnimal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITarifTransportAnimal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
