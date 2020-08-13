import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TarifTransportService } from 'app/entities/tarif-transport/tarif-transport.service';
import { ITarifTransport, TarifTransport } from 'app/shared/model/tarif-transport.model';

describe('Service Tests', () => {
  describe('TarifTransport Service', () => {
    let injector: TestBed;
    let service: TarifTransportService;
    let httpMock: HttpTestingController;
    let elemDefault: ITarifTransport;
    let expectedResult: ITarifTransport | ITarifTransport[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TarifTransportService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TarifTransport(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TarifTransport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TarifTransport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TarifTransport', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            vehicule: 'BBBBBB',
            nombreDePersonne: 1,
            distance: 1,
            prix: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TarifTransport', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            vehicule: 'BBBBBB',
            nombreDePersonne: 1,
            distance: 1,
            prix: 1
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

      it('should delete a TarifTransport', () => {
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
