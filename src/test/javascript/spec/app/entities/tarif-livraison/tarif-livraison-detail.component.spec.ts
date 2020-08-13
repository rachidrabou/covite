import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { TarifLivraisonDetailComponent } from 'app/entities/tarif-livraison/tarif-livraison-detail.component';
import { TarifLivraison } from 'app/shared/model/tarif-livraison.model';

describe('Component Tests', () => {
  describe('TarifLivraison Management Detail Component', () => {
    let comp: TarifLivraisonDetailComponent;
    let fixture: ComponentFixture<TarifLivraisonDetailComponent>;
    const route = ({ data: of({ tarifLivraison: new TarifLivraison(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [TarifLivraisonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarifLivraisonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifLivraisonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifLivraison on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifLivraison).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
