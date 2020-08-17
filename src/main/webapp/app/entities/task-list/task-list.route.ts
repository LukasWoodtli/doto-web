import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskList, TaskList } from 'app/shared/model/task-list.model';
import { TaskListService } from './task-list.service';
import { TaskListComponent } from './task-list.component';
import { TaskListDetailComponent } from './task-list-detail.component';
import { TaskListUpdateComponent } from './task-list-update.component';

@Injectable({ providedIn: 'root' })
export class TaskListResolve implements Resolve<ITaskList> {
  constructor(private service: TaskListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskList: HttpResponse<TaskList>) => {
          if (taskList.body) {
            return of(taskList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskList());
  }
}

export const taskListRoute: Routes = [
  {
    path: '',
    component: TaskListComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'dotoApp.taskList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskListDetailComponent,
    resolve: {
      taskList: TaskListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dotoApp.taskList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskListUpdateComponent,
    resolve: {
      taskList: TaskListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dotoApp.taskList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskListUpdateComponent,
    resolve: {
      taskList: TaskListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dotoApp.taskList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
