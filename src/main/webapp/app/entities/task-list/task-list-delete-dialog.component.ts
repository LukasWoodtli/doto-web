import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskList } from 'app/shared/model/task-list.model';
import { TaskListService } from './task-list.service';

@Component({
  templateUrl: './task-list-delete-dialog.component.html',
})
export class TaskListDeleteDialogComponent {
  taskList?: ITaskList;

  constructor(protected taskListService: TaskListService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskListListModification');
      this.activeModal.close();
    });
  }
}
