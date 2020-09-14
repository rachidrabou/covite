import { IUser } from 'app/core/user/user.model';
import { IVehicule } from 'app/shared/model/vehicule.model';

export interface ILivreur {
  id?: number;
  telephone?: string;
  solde?: number;
  cin?: string;
  user?: IUser;
  vehicule?: IVehicule;
}

export class Livreur implements ILivreur {
  constructor(
    public id?: number,
    public telephone?: string,
    public solde?: number,
    public cin?: string,
    public user?: IUser,
    public vehicule?: IVehicule
  ) {}
}
