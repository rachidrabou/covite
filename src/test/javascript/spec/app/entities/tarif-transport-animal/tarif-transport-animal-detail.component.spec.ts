import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { TarifTransportAnimalDetailComponent } from 'app/entities/tarif-transport-animal/tarif-transport-animal-detail.component';
import { TarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

describe('Component Tests', () => {
  describe('TarifTransportAnimal Management Detail Component', () => {
    let comp: TarifTransportAnimalDetailComponent;
    let fixture: ComponentFixture<TarifTransportAnimalDetailComponent>;
    const route = ({ data: of({ tarifTransportAnimal: new TarifTransportAnimal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [TarifTransportAnimalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TarifTransportAnimalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TarifTransportAnimalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tarifTransportAnimal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tarifTransportAnimal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
