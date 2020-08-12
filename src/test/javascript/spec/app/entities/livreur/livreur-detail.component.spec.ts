import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { LivreurDetailComponent } from 'app/entities/livreur/livreur-detail.component';
import { Livreur } from 'app/shared/model/livreur.model';

describe('Component Tests', () => {
  describe('Livreur Management Detail Component', () => {
    let comp: LivreurDetailComponent;
    let fixture: ComponentFixture<LivreurDetailComponent>;
    const route = ({ data: of({ livreur: new Livreur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [LivreurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LivreurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivreurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load livreur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livreur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
