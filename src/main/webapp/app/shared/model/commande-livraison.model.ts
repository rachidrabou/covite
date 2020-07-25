import { Moment } from 'moment';

export interface ICommandeLivraison {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  prix?: number;
  numeroClient?: string;
  objet?: string;
}

export class CommandeLivraison implements ICommandeLivraison {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public prix?: number,
    public numeroClient?: string,
    public objet?: string
  ) {}
}
