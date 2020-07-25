import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeTransportUpdateComponent } from 'app/entities/commande-transport/commande-transport-update.component';
import { CommandeTransportService } from 'app/entities/commande-transport/commande-transport.service';
import { CommandeTransport } from 'app/shared/model/commande-transport.model';

describe('Component Tests', () => {
  describe('CommandeTransport Management Update Component', () => {
    let comp: CommandeTransportUpdateComponent;
    let fixture: ComponentFixture<CommandeTransportUpdateComponent>;
    let service: CommandeTransportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeTransportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeTransportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeTransportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeTransportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeTransport(123);
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
        const entity = new CommandeTransport();
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
