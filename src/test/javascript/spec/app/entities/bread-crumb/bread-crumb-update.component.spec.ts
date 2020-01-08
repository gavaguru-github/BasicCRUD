import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BasicCrudTestModule } from '../../../test.module';
import { BreadCrumbUpdateComponent } from 'app/entities/bread-crumb/bread-crumb-update.component';
import { BreadCrumbService } from 'app/entities/bread-crumb/bread-crumb.service';
import { BreadCrumb } from 'app/shared/model/bread-crumb.model';

describe('Component Tests', () => {
  describe('BreadCrumb Management Update Component', () => {
    let comp: BreadCrumbUpdateComponent;
    let fixture: ComponentFixture<BreadCrumbUpdateComponent>;
    let service: BreadCrumbService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BasicCrudTestModule],
        declarations: [BreadCrumbUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BreadCrumbUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BreadCrumbUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BreadCrumbService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BreadCrumb(123);
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
        const entity = new BreadCrumb();
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
