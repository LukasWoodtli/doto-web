import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.DotoTaskModule),
      },
      {
        path: 'task-list',
        loadChildren: () => import('./task-list/task-list.module').then(m => m.DotoTaskListModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DotoEntityModule {}
