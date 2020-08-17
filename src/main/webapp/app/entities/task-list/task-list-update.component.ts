import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaskList, TaskList } from 'app/shared/model/task-list.model';
import { TaskListService } from './task-list.service';

@Component({
  selector: 'jhi-task-list-update',
  templateUrl: './task-list-update.component.html',
})
export class TaskListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected taskListService: TaskListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskList }) => {
      this.updateForm(taskList);
    });
  }

  updateForm(taskList: ITaskList): void {
    this.editForm.patchValue({
      id: taskList.id,
      name: taskList.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskList = this.createFromForm();
    if (taskList.id !== undefined) {
      this.subscribeToSaveResponse(this.taskListService.update(taskList));
    } else {
      this.subscribeToSaveResponse(this.taskListService.create(taskList));
    }
  }

  private createFromForm(): ITaskList {
    return {
      ...new TaskList(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskList>>): void {
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
