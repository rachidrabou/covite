import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { LivreurUpdateComponent } from 'app/entities/livreur/livreur-update.component';
import { LivreurService } from 'app/entities/livreur/livreur.service';
import { Livreur } from 'app/shared/model/livreur.model';

describe('Component Tests', () => {
  describe('Livreur Management Update Component', () => {
    let comp: LivreurUpdateComponent;
    let fixture: ComponentFixture<LivreurUpdateComponent>;
    let service: LivreurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [LivreurUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LivreurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivreurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivreurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Livreur(123);
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
        const entity = new Livreur();
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
