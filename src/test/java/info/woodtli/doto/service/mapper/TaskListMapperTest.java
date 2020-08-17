package info.woodtli.doto.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskListMapperTest {

    private TaskListMapper taskListMapper;

    @BeforeEach
    public void setUp() {
        taskListMapper = new TaskListMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskListMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskListMapper.fromId(null)).isNull();
    }
}
