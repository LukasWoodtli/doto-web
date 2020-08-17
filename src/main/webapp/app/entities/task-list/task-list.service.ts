import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskList } from 'app/shared/model/task-list.model';

type EntityResponseType = HttpResponse<ITaskList>;
type EntityArrayResponseType = HttpResponse<ITaskList[]>;

@Injectable({ providedIn: 'root' })
export class TaskListService {
  public resourceUrl = SERVER_API_URL + 'api/task-lists';

  constructor(protected http: HttpClient) {}

  create(taskList: ITaskList): Observable<EntityResponseType> {
    return this.http.post<ITaskList>(this.resourceUrl, taskList, { observe: 'response' });
  }

  update(taskList: ITaskList): Observable<EntityResponseType> {
    return this.http.put<ITaskList>(this.resourceUrl, taskList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaskList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaskList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
