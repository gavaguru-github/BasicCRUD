import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BasicCrudTestModule } from '../../../test.module';
import { ApplicationMenuDetailComponent } from 'app/entities/application-menu/application-menu-detail.component';
import { ApplicationMenu } from 'app/shared/model/application-menu.model';

describe('Component Tests', () => {
  describe('ApplicationMenu Management Detail Component', () => {
    let comp: ApplicationMenuDetailComponent;
    let fixture: ComponentFixture<ApplicationMenuDetailComponent>;
    const route = ({ data: of({ applicationMenu: new ApplicationMenu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BasicCrudTestModule],
        declarations: [ApplicationMenuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationMenuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationMenuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicationMenu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationMenu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
