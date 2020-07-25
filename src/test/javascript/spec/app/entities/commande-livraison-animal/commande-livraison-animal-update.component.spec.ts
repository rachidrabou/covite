import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeLivraisonAnimalUpdateComponent } from 'app/entities/commande-livraison-animal/commande-livraison-animal-update.component';
import { CommandeLivraisonAnimalService } from 'app/entities/commande-livraison-animal/commande-livraison-animal.service';
import { CommandeLivraisonAnimal } from 'app/shared/model/commande-livraison-animal.model';

describe('Component Tests', () => {
  describe('CommandeLivraisonAnimal Management Update Component', () => {
    let comp: CommandeLivraisonAnimalUpdateComponent;
    let fixture: ComponentFixture<CommandeLivraisonAnimalUpdateComponent>;
    let service: CommandeLivraisonAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeLivraisonAnimalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeLivraisonAnimalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeLivraisonAnimalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeLivraisonAnimalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeLivraisonAnimal(123);
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
        const entity = new CommandeLivraisonAnimal();
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
