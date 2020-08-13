export interface ITarifTransportAnimal {
  id?: number;
  service?: string;
  animal?: string;
  distance?: number;
  prix?: number;
}

export class TarifTransportAnimal implements ITarifTransportAnimal {
  constructor(public id?: number, public service?: string, public animal?: string, public distance?: number, public prix?: number) {}
}
