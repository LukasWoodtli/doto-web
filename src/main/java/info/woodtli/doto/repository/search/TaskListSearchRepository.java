package info.woodtli.doto.repository.search;

import info.woodtli.doto.domain.TaskList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TaskList} entity.
 */
public interface TaskListSearchRepository extends ElasticsearchRepository<TaskList, Long> {
}
