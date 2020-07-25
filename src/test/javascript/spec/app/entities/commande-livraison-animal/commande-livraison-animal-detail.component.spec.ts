import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeLivraisonAnimalDetailComponent } from 'app/entities/commande-livraison-animal/commande-livraison-animal-detail.component';
import { CommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';

describe('Component Tests', () => {
  describe('CommandeLivraisonAnimal Management Detail Component', () => {
    let comp: CommandeLivraisonAnimalDetailComponent;
    let fixture: ComponentFixture<CommandeLivraisonAnimalDetailComponent>;
    const route = ({ data: of({ commandeLivraisonAnimal: new CommandeLivraisonAnimal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeLivraisonAnimalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeLivraisonAnimalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeLivraisonAnimalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeLivraisonAnimal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeLivraisonAnimal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
