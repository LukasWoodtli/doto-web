import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { ITaskList } from 'app/shared/model/task-list.model';
import { TaskListService } from 'app/entities/task-list/task-list.service';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  tasklists: ITaskList[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    done: [],
    description: [],
    listId: [],
  });

  constructor(
    protected taskService: TaskService,
    protected taskListService: TaskListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      this.updateForm(task);

      this.taskListService.query().subscribe((res: HttpResponse<ITaskList[]>) => (this.tasklists = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      title: task.title,
      done: task.done,
      description: task.description,
      listId: task.listId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      done: this.editForm.get(['done'])!.value,
      description: this.editForm.get(['description'])!.value,
      listId: this.editForm.get(['listId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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

  trackById(index: number, item: ITaskList): any {
    return item.id;
  }
}
