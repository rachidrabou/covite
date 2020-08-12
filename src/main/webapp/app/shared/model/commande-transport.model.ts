import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICommandeTransport {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  moyenDeTransport?: string;
  prix?: number;
  nombreDePersonnes?: number;
  numeroClient?: string;
  validated?: boolean;
  client?: IUser;
  livreur?: IUser;
}

export class CommandeTransport implements ICommandeTransport {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public moyenDeTransport?: string,
    public prix?: number,
    public nombreDePersonnes?: number,
    public numeroClient?: string,
    public validated?: boolean,
    public client?: IUser,
    public livreur?: IUser
  ) {
    this.validated = this.validated || false;
  }
}
