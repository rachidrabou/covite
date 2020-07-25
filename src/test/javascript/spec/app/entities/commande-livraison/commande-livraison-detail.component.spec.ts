import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeLivraisonDetailComponent } from 'app/entities/commande-livraison/commande-livraison-detail.component';
import { CommandeLivraison } from 'app/shared/model/commande-livraison.model';

describe('Component Tests', () => {
  describe('CommandeLivraison Management Detail Component', () => {
    let comp: CommandeLivraisonDetailComponent;
    let fixture: ComponentFixture<CommandeLivraisonDetailComponent>;
    const route = ({ data: of({ commandeLivraison: new CommandeLivraison(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeLivraisonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeLivraisonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeLivraisonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeLivraison on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeLivraison).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
