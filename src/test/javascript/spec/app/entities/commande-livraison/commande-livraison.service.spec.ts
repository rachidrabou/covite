import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';
import { ICommandeLivraison, CommandeLivraison } from 'app/shared/model/commande-livraison.model';

describe('Service Tests', () => {
  describe('CommandeLivraison Service', () => {
    let injector: TestBed;
    let service: CommandeLivraisonService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeLivraison;
    let expectedResult: ICommandeLivraison | ICommandeLivraison[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeLivraisonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CommandeLivraison(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommandeLivraison', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CommandeLivraison()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommandeLivraison', () => {
        const returnedFromService = Object.assign(
          {
            adresseDepart: 'BBBBBB',
            adresseArrivee: 'BBBBBB',
            prix: 1,
            numeroClient: 'BBBBBB',
            objet: 'BBBBBB',
            cin: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommandeLivraison', () => {
        const returnedFromService = Object.assign(
          {
            adresseDepart: 'BBBBBB',
            adresseArrivee: 'BBBBBB',
            prix: 1,
            numeroClient: 'BBBBBB',
            objet: 'BBBBBB',
            cin: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommandeLivraison', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
