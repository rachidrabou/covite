import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandesUpdateComponent } from 'app/entities/commandes/commandes-update.component';
import { CommandesService } from 'app/entities/commandes/commandes.service';
import { Commandes } from 'app/shared/model/commandes.model';

describe('Component Tests', () => {
  describe('Commandes Management Update Component', () => {
    let comp: CommandesUpdateComponent;
    let fixture: ComponentFixture<CommandesUpdateComponent>;
    let service: CommandesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Commandes(123);
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
        const entity = new Commandes();
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
