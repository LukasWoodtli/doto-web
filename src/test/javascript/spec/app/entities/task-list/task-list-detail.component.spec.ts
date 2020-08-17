import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DotoTestModule } from '../../../test.module';
import { TaskListDetailComponent } from 'app/entities/task-list/task-list-detail.component';
import { TaskList } from 'app/shared/model/task-list.model';

describe('Component Tests', () => {
  describe('TaskList Management Detail Component', () => {
    let comp: TaskListDetailComponent;
    let fixture: ComponentFixture<TaskListDetailComponent>;
    const route = ({ data: of({ taskList: new TaskList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DotoTestModule],
        declarations: [TaskListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
