package info.woodtli.doto.service.mapper;


import info.woodtli.doto.domain.*;
import info.woodtli.doto.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskListMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "list.id", target = "listId")
    TaskDTO toDto(Task task);

    @Mapping(source = "listId", target = "list")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
