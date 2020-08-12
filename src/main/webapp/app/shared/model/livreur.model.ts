import { IUser } from 'app/core/user/user.model';

export interface ILivreur {
  id?: number;
  telephone?: string;
  solde?: number;
  user?: IUser;
}

export class Livreur implements ILivreur {
  constructor(public id?: number, public telephone?: string, public solde?: number, public user?: IUser) {}
}
