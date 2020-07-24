import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehicule } from 'app/shared/model/vehicule.model';

type EntityResponseType = HttpResponse<IVehicule>;
type EntityArrayResponseType = HttpResponse<IVehicule[]>;

@Injectable({ providedIn: 'root' })
export class VehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/vehicules';

  constructor(protected http: HttpClient) {}

  create(vehicule: IVehicule): Observable<EntityResponseType> {
    return this.http.post<IVehicule>(this.resourceUrl, vehicule, { observe: 'response' });
  }

  update(vehicule: IVehicule): Observable<EntityResponseType> {
    return this.http.put<IVehicule>(this.resourceUrl, vehicule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
