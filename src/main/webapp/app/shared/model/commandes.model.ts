import { Moment } from 'moment';
import { Categorie } from 'app/shared/model/enumerations/categorie.model';
import { Service } from 'app/shared/model/enumerations/service.model';

export interface ICommandes {
  id?: number;
  date?: Moment;
  prix?: number;
  type?: Categorie;
  typeservice?: Service;
}

export class Commandes implements ICommandes {
  constructor(public id?: number, public date?: Moment, public prix?: number, public type?: Categorie, public typeservice?: Service) {}
}
