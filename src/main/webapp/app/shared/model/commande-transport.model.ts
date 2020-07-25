import { Moment } from 'moment';

export interface ICommandeTransport {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  moyenDeTransport?: string;
  prix?: number;
  nombreDePersonnes?: number;
  numeroClient?: string;
}

export class CommandeTransport implements ICommandeTransport {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public moyenDeTransport?: string,
    public prix?: number,
    public nombreDePersonnes?: number,
    public numeroClient?: string
  ) {}
}
