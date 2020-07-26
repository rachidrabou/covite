import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICommandeLivraison {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  prix?: number;
  numeroClient?: string;
  objet?: string;
  client?: IUser;
}

export class CommandeLivraison implements ICommandeLivraison {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public prix?: number,
    public numeroClient?: string,
    public objet?: string,
    public client?: IUser
  ) {}
}
