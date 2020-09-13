import { IUser } from 'app/core/user/user.model';

export interface ICommandeLivraison {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  prix?: number;
  numeroClient?: string;
  objet?: string;
  cin?: string;
  client?: IUser;
  livreur?: IUser;
}

export class CommandeLivraison implements ICommandeLivraison {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public prix?: number,
    public numeroClient?: string,
    public objet?: string,
    public cin?: string,
    public client?: IUser,
    public livreur?: IUser
  ) {}
}
