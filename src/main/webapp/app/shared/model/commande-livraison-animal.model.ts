import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICommandeLivraisonAnimal {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  animal?: string;
  moyenDeTransport?: string;
  numeroClient?: string;
  prix?: number;
  validated?: boolean;
  client?: IUser;
  livreur?: IUser;
}

export class CommandeLivraisonAnimal implements ICommandeLivraisonAnimal {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public animal?: string,
    public moyenDeTransport?: string,
    public numeroClient?: string,
    public prix?: number,
    public validated?: boolean,
    public client?: IUser,
    public livreur?: IUser
  ) {
    this.validated = this.validated || false;
  }
}
