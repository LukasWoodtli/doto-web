package info.woodtli.doto.repository;

import info.woodtli.doto.domain.TaskList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaskList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
