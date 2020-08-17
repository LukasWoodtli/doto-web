import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DotoTestModule } from '../../../test.module';
import { TaskListUpdateComponent } from 'app/entities/task-list/task-list-update.component';
import { TaskListService } from 'app/entities/task-list/task-list.service';
import { TaskList } from 'app/shared/model/task-list.model';

describe('Component Tests', () => {
  describe('TaskList Management Update Component', () => {
    let comp: TaskListUpdateComponent;
    let fixture: ComponentFixture<TaskListUpdateComponent>;
    let service: TaskListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DotoTestModule],
        declarations: [TaskListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskList(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskList();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
