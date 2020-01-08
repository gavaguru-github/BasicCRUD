import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BasicCrudTestModule } from '../../../test.module';
import { ApplicationMenuUpdateComponent } from 'app/entities/application-menu/application-menu-update.component';
import { ApplicationMenuService } from 'app/entities/application-menu/application-menu.service';
import { ApplicationMenu } from 'app/shared/model/application-menu.model';

describe('Component Tests', () => {
  describe('ApplicationMenu Management Update Component', () => {
    let comp: ApplicationMenuUpdateComponent;
    let fixture: ComponentFixture<ApplicationMenuUpdateComponent>;
    let service: ApplicationMenuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BasicCrudTestModule],
        declarations: [ApplicationMenuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationMenuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationMenuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationMenuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationMenu(123);
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
        const entity = new ApplicationMenu();
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
