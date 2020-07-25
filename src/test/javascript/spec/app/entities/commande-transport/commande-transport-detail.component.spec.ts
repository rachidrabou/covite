import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviteTestModule } from '../../../test.module';
import { CommandeTransportDetailComponent } from 'app/entities/commande-transport/commande-transport-detail.component';
import { CommandeTransport } from 'app/shared/model/commande-transport.model';

describe('Component Tests', () => {
  describe('CommandeTransport Management Detail Component', () => {
    let comp: CommandeTransportDetailComponent;
    let fixture: ComponentFixture<CommandeTransportDetailComponent>;
    const route = ({ data: of({ commandeTransport: new CommandeTransport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviteTestModule],
        declarations: [CommandeTransportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeTransportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeTransportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeTransport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeTransport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
