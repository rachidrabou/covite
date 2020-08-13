export interface ITarifTransport {
  id?: number;
  service?: string;
  vehicule?: string;
  nombreDePersonne?: number;
  distance?: number;
  prix?: number;
}

export class TarifTransport implements ITarifTransport {
  constructor(
    public id?: number,
    public service?: string,
    public vehicule?: string,
    public nombreDePersonne?: number,
    public distance?: number,
    public prix?: number
  ) {}
}
