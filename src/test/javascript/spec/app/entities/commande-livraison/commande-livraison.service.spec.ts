import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';
import { ICommandeLivraison, CommandeLivraison } from 'app/shared/model/commande-livraison.model';

describe('Service Tests', () => {
  describe('CommandeLivraison Service', () => {
    let injector: TestBed;
    let service: CommandeLivraisonService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeLivraison;
    let expectedResult: ICommandeLivraison | ICommandeLivraison[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeLivraisonService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommandeLivraison(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateheure: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommandeLivraison', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateheure: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateheure: currentDate
          },
          returnedFromService
        );

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
            cin: 'BBBBBB',
            dateheure: currentDate.format(DATE_TIME_FORMAT),
            cvalider: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateheure: currentDate
          },
          returnedFromService
        );

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
            cin: 'BBBBBB',
            dateheure: currentDate.format(DATE_TIME_FORMAT),
            cvalider: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateheure: currentDate
          },
          returnedFromService
        );

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
