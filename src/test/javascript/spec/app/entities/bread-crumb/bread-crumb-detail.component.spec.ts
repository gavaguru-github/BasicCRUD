import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BasicCrudTestModule } from '../../../test.module';
import { BreadCrumbDetailComponent } from 'app/entities/bread-crumb/bread-crumb-detail.component';
import { BreadCrumb } from 'app/shared/model/bread-crumb.model';

describe('Component Tests', () => {
  describe('BreadCrumb Management Detail Component', () => {
    let comp: BreadCrumbDetailComponent;
    let fixture: ComponentFixture<BreadCrumbDetailComponent>;
    const route = ({ data: of({ breadCrumb: new BreadCrumb(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BasicCrudTestModule],
        declarations: [BreadCrumbDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BreadCrumbDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BreadCrumbDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load breadCrumb on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.breadCrumb).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
