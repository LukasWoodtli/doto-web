package info.woodtli.doto.service.mapper;


import info.woodtli.doto.domain.*;
import info.woodtli.doto.service.dto.TaskListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskList} and its DTO {@link TaskListDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskListMapper extends EntityMapper<TaskListDTO, TaskList> {


    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTasks", ignore = true)
    TaskList toEntity(TaskListDTO taskListDTO);

    default TaskList fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskList taskList = new TaskList();
        taskList.setId(id);
        return taskList;
    }
}
