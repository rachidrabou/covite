import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { CoviteTestModule } from '../../../test.module';
import { CommandeLivraisonComponent } from 'app/entities/commande-livraison/commande-livraison.component';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';
import { CommandeLivraison } from 'app/shared/model/commande-livraison.model';

describe('Component Tests', () => {
  describe('CommandeLivraison Management Component', () => {
    let comp: CommandeLivraisonComponent;
    let fixture: ComponentFixture<CommandeLivraisonComponent>;
    let service: CommandeLivraisonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeLivraisonComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(CommandeLivraisonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeLivraisonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeLivraisonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeLivraison(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeLivraisons && comp.commandeLivraisons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeLivraison(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeLivraisons && comp.commandeLivraisons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
