import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeLivraisonUpdateComponent } from 'app/entities/commande-livraison/commande-livraison-update.component';
import { CommandeLivraisonService } from 'app/entities/commande-livraison/commande-livraison.service';
import { CommandeLivraison } from 'app/shared/model/commande-livraison.model';

describe('Component Tests', () => {
  describe('CommandeLivraison Management Update Component', () => {
    let comp: CommandeLivraisonUpdateComponent;
    let fixture: ComponentFixture<CommandeLivraisonUpdateComponent>;
    let service: CommandeLivraisonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeLivraisonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeLivraisonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeLivraisonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeLivraisonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeLivraison(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeLivraison();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
