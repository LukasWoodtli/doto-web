export interface ITask {
  id?: number;
  title?: string;
  done?: boolean;
  description?: string;
  listId?: number;
}

export class Task implements ITask {
  constructor(public id?: number, public title?: string, public done?: boolean, public description?: string, public listId?: number) {
    this.done = this.done || false;
  }
}
