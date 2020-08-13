import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { TarifTransportAnimalUpdateComponent } from 'app/entities/tarif-transport-animal/tarif-transport-animal-update.component';
import { TarifTransportAnimalService } from 'app/entities/tarif-transport-animal/tarif-transport-animal.service';
import { TarifTransportAnimal } from 'app/shared/model/tarif-transport-animal.model';

describe('Component Tests', () => {
  describe('TarifTransportAnimal Management Update Component', () => {
    let comp: TarifTransportAnimalUpdateComponent;
    let fixture: ComponentFixture<TarifTransportAnimalUpdateComponent>;
    let service: TarifTransportAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [TarifTransportAnimalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifTransportAnimalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifTransportAnimalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifTransportAnimalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifTransportAnimal(123);
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
        const entity = new TarifTransportAnimal();
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
