import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TarifTransportAnimalService } from 'app/entities/tarif-transport-animal/tarif-transport-animal.service';
import { ITarifTransportAnimal, TarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

describe('Service Tests', () => {
  describe('TarifTransportAnimal Service', () => {
    let injector: TestBed;
    let service: TarifTransportAnimalService;
    let httpMock: HttpTestingController;
    let elemDefault: ITarifTransportAnimal;
    let expectedResult: ITarifTransportAnimal | ITarifTransportAnimal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TarifTransportAnimalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TarifTransportAnimal(0, 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TarifTransportAnimal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TarifTransportAnimal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TarifTransportAnimal', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            animal: 'BBBBBB',
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

      it('should return a list of TarifTransportAnimal', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            animal: 'BBBBBB',
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

      it('should delete a TarifTransportAnimal', () => {
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
