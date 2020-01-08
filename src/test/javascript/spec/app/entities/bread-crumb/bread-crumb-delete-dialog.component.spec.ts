import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BasicCrudTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BreadCrumbDeleteDialogComponent } from 'app/entities/bread-crumb/bread-crumb-delete-dialog.component';
import { BreadCrumbService } from 'app/entities/bread-crumb/bread-crumb.service';

describe('Component Tests', () => {
  describe('BreadCrumb Management Delete Component', () => {
    let comp: BreadCrumbDeleteDialogComponent;
    let fixture: ComponentFixture<BreadCrumbDeleteDialogComponent>;
    let service: BreadCrumbService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BasicCrudTestModule],
        declarations: [BreadCrumbDeleteDialogComponent]
      })
        .overrideTemplate(BreadCrumbDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BreadCrumbDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BreadCrumbService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
