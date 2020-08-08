import { IUser } from 'app/core/user/user.model';
import { ICommandeLivraison } from 'app/shared/model/commande-livraison.model';
import { ICommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';
import { ICommandeTransport } from 'app/shared/model/commande-transport.model';

export interface INotification {
  id?: number;
  titre?: string;
  prix?: number;
  prixValider?: boolean;
  client?: IUser;
  commandeLivraison?: ICommandeLivraison;
  commandeLivraisonAnimal?: ICommandeLivraisonAnimal;
  commandeTransport?: ICommandeTransport;
  livreur?: IUser;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public titre?: string,
    public prix?: number,
    public prixValider?: boolean,
    public client?: IUser,
    public commandeLivraison?: ICommandeLivraison,
    public commandeLivraisonAnimal?: ICommandeLivraisonAnimal,
    public commandeTransport?: ICommandeTransport,
    public livreur?: IUser
  ) {
    this.prixValider = this.prixValider || false;
  }
}
