import { IUser } from 'app/core/user/user.model';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';

export interface INotification {
  id?: number;
  titre?: string;
  client?: IUser;
  commandeLivraison?: ICommandeLivraison;
}

export class Notification implements INotification {
  constructor(public id?: number, public titre?: string, public client?: IUser, public commandeLivraison?: ICommandeLivraison) {}
}
