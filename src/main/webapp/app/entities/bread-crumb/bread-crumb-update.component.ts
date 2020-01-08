import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBreadCrumb, BreadCrumb } from 'app/shared/model/bread-crumb.model';
import { BreadCrumbService } from './bread-crumb.service';

@Component({
  selector: 'jhi-bread-crumb-update',
  templateUrl: './bread-crumb-update.component.html'
})
export class BreadCrumbUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    path: [],
    name: []
  });

  constructor(protected breadCrumbService: BreadCrumbService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ breadCrumb }) => {
      this.updateForm(breadCrumb);
    });
  }

  updateForm(breadCrumb: IBreadCrumb): void {
    this.editForm.patchValue({
      id: breadCrumb.id,
      path: breadCrumb.path,
      name: breadCrumb.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const breadCrumb = this.createFromForm();
    if (breadCrumb.id !== undefined) {
      this.subscribeToSaveResponse(this.breadCrumbService.update(breadCrumb));
    } else {
      this.subscribeToSaveResponse(this.breadCrumbService.create(breadCrumb));
    }
  }

  private createFromForm(): IBreadCrumb {
    return {
      ...new BreadCrumb(),
      id: this.editForm.get(['id'])!.value,
      path: this.editForm.get(['path'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBreadCrumb>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
