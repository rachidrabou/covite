import { IUser } from 'app/core/user/user.model';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { ICommandeTransport } from 'app/shared/model/commande-transport.model';

export interface INotification {
  id?: number;
  titre?: string;
  client?: IUser;
  commandeLivraison?: ICommandeLivraison;
  commandeLivraisonAnimal?: ICommandeLivraisonAnimal;
  commandeTransport?: ICommandeTransport;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public titre?: string,
    public client?: IUser,
    public commandeLivraison?: ICommandeLivraison,
    public commandeLivraisonAnimal?: ICommandeLivraisonAnimal,
    public commandeTransport?: ICommandeTransport
  ) {}
}
