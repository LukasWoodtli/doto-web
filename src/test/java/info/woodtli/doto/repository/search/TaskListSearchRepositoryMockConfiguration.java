package info.woodtli.doto.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TaskListSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TaskListSearchRepositoryMockConfiguration {

    @MockBean
    private TaskListSearchRepository mockTaskListSearchRepository;

}
