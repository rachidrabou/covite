import { Moment } from 'moment';

export interface ICommandeLivraisonAnimal {
  id?: number;
  adresseDepart?: string;
  adresseArrivee?: string;
  dateHeure?: Moment;
  animal?: string;
  moyenDeTransport?: string;
  numeroClient?: string;
  prix?: number;
}

export class CommandeLivraisonAnimal implements ICommandeLivraisonAnimal {
  constructor(
    public id?: number,
    public adresseDepart?: string,
    public adresseArrivee?: string,
    public dateHeure?: Moment,
    public animal?: string,
    public moyenDeTransport?: string,
    public numeroClient?: string,
    public prix?: number
  ) {}
}
