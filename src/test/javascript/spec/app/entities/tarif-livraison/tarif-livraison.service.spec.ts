import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TarifLivraisonService } from 'app/entities/tarif-livraison/tarif-livraison.service';
import { ITarifLivraison, TarifLivraison } from 'app/shared/model/tarif-livraison.model';

describe('Service Tests', () => {
  describe('TarifLivraison Service', () => {
    let injector: TestBed;
    let service: TarifLivraisonService;
    let httpMock: HttpTestingController;
    let elemDefault: ITarifLivraison;
    let expectedResult: ITarifLivraison | ITarifLivraison[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TarifLivraisonService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TarifLivraison(0, 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TarifLivraison', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TarifLivraison()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TarifLivraison', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            objet: 'BBBBBB',
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

      it('should return a list of TarifLivraison', () => {
        const returnedFromService = Object.assign(
          {
            service: 'BBBBBB',
            objet: 'BBBBBB',
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

      it('should delete a TarifLivraison', () => {
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
