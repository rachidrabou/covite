export interface ITarifLivraison {
  id?: number;
  service?: string;
  objet?: string;
  distance?: number;
  prix?: number;
}

export class TarifLivraison implements ITarifLivraison {
  constructor(public id?: number, public service?: string, public objet?: string, public distance?: number, public prix?: number) {}
}
