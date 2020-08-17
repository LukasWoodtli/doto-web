import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DotoSharedModule } from 'app/shared/shared.module';
import { TaskListComponent } from './task-list.component';
import { TaskListDetailComponent } from './task-list-detail.component';
import { TaskListUpdateComponent } from './task-list-update.component';
import { TaskListDeleteDialogComponent } from './task-list-delete-dialog.component';
import { taskListRoute } from './task-list.route';

@NgModule({
  imports: [DotoSharedModule, RouterModule.forChild(taskListRoute)],
  declarations: [TaskListComponent, TaskListDetailComponent, TaskListUpdateComponent, TaskListDeleteDialogComponent],
  entryComponents: [TaskListDeleteDialogComponent],
})
export class DotoTaskListModule {}
