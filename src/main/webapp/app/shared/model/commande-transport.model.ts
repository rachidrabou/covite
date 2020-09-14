import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICommandeTransport {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  moyenDeTransport?: string;
  prix?: number;
  nombreDePersonnes?: number;
  numeroClient?: string;
  dateheure?: Moment;
  client?: IUser;
  livreur?: IUser;
}

export class CommandeTransport implements ICommandeTransport {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public moyenDeTransport?: string,
    public prix?: number,
    public nombreDePersonnes?: number,
    public numeroClient?: string,
    public dateheure?: Moment,
    public client?: IUser,
    public livreur?: IUser
  ) {}
}
