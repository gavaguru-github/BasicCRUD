import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IApplicationMenu, ApplicationMenu } from 'app/shared/model/application-menu.model';
import { ApplicationMenuService } from './application-menu.service';

@Component({
  selector: 'jhi-application-menu-update',
  templateUrl: './application-menu-update.component.html'
})
export class ApplicationMenuUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    parentId: [],
    name: [],
    englishText: [],
    frenchPath: [],
    role: [],
    order: []
  });

  constructor(
    protected applicationMenuService: ApplicationMenuService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationMenu }) => {
      this.updateForm(applicationMenu);
    });
  }

  updateForm(applicationMenu: IApplicationMenu): void {
    this.editForm.patchValue({
      id: applicationMenu.id,
      parentId: applicationMenu.parentId,
      name: applicationMenu.name,
      englishText: applicationMenu.englishText,
      frenchPath: applicationMenu.frenchPath,
      role: applicationMenu.role,
      order: applicationMenu.order
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationMenu = this.createFromForm();
    if (applicationMenu.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationMenuService.update(applicationMenu));
    } else {
      this.subscribeToSaveResponse(this.applicationMenuService.create(applicationMenu));
    }
  }

  private createFromForm(): IApplicationMenu {
    return {
      ...new ApplicationMenu(),
      id: this.editForm.get(['id'])!.value,
      parentId: this.editForm.get(['parentId'])!.value,
      name: this.editForm.get(['name'])!.value,
      englishText: this.editForm.get(['englishText'])!.value,
      frenchPath: this.editForm.get(['frenchPath'])!.value,
      role: this.editForm.get(['role'])!.value,
      order: this.editForm.get(['order'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationMenu>>): void {
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
