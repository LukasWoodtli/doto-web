package info.woodtli.doto.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link info.woodtli.doto.domain.Task} entity.
 */
public class TaskDTO implements Serializable {
    
    private Long id;

    private String title;

    private Boolean done;

    private String description;


    private Long listId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long taskListId) {
        this.listId = taskListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", done='" + isDone() + "'" +
            ", description='" + getDescription() + "'" +
            ", listId=" + getListId() +
            "}";
    }
}
