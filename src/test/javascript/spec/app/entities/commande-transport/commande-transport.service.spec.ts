import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommandeTransportService } from 'app/entities/commande-transport/commande-transport.service';
import { ICommandeTransport, CommandeTransport } from 'app/shared/model/commande-transport.model';

describe('Service Tests', () => {
  describe('CommandeTransport Service', () => {
    let injector: TestBed;
    let service: CommandeTransportService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommandeTransport;
    let expectedResult: ICommandeTransport | ICommandeTransport[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommandeTransportService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommandeTransport(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateHeure: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommandeTransport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateHeure: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateHeure: currentDate
          },
          returnedFromService
        );

        service.create(new CommandeTransport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommandeTransport', () => {
        const returnedFromService = Object.assign(
          {
            adresseDepart: 'BBBBBB',
            adresseArrivee: 'BBBBBB',
            dateHeure: currentDate.format(DATE_FORMAT),
            moyenDeTransport: 'BBBBBB',
            prix: 1,
            nombreDePersonnes: 1,
            numeroClient: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateHeure: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommandeTransport', () => {
        const returnedFromService = Object.assign(
          {
            adresseDepart: 'BBBBBB',
            adresseArrivee: 'BBBBBB',
            dateHeure: currentDate.format(DATE_FORMAT),
            moyenDeTransport: 'BBBBBB',
            prix: 1,
            nombreDePersonnes: 1,
            numeroClient: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateHeure: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommandeTransport', () => {
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
