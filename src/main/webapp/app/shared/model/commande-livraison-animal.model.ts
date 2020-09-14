import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICommandeLivraisonAnimal {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  animal?: string;
  moyenDeTransport?: string;
  numeroClient?: string;
  prix?: number;
  dateheure?: Moment;
  cvalider?: boolean;
  client?: IUser;
  livreur?: IUser;
}

export class CommandeLivraisonAnimal implements ICommandeLivraisonAnimal {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public animal?: string,
    public moyenDeTransport?: string,
    public numeroClient?: string,
    public prix?: number,
    public dateheure?: Moment,
    public cvalider?: boolean,
    public client?: IUser,
    public livreur?: IUser
  ) {
    this.cvalider = this.cvalider || false;
  }
}
