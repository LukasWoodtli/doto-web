import { ITask } from 'app/shared/model/task.model';

export interface ITaskList {
  id?: number;
  name?: string;
  tasks?: ITask[];
}

export class TaskList implements ITaskList {
  constructor(public id?: number, public name?: string, public tasks?: ITask[]) {}
}
