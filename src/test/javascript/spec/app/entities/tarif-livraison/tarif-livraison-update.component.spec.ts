import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { TarifLivraisonUpdateComponent } from 'app/entities/tarif-livraison/tarif-livraison-update.component';
import { TarifLivraisonService } from 'app/entities/tarif-livraison/tarif-livraison.service';
import { TarifLivraison } from 'app/shared/model/tarif-livraison.model';

describe('Component Tests', () => {
  describe('TarifLivraison Management Update Component', () => {
    let comp: TarifLivraisonUpdateComponent;
    let fixture: ComponentFixture<TarifLivraisonUpdateComponent>;
    let service: TarifLivraisonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [TarifLivraisonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifLivraisonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifLivraisonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifLivraisonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TarifLivraison(123);
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
        const entity = new TarifLivraison();
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
