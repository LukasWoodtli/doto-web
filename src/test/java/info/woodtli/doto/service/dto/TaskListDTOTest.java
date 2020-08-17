package info.woodtli.doto.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import info.woodtli.doto.web.rest.TestUtil;

public class TaskListDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskListDTO.class);
        TaskListDTO taskListDTO1 = new TaskListDTO();
        taskListDTO1.setId(1L);
        TaskListDTO taskListDTO2 = new TaskListDTO();
        assertThat(taskListDTO1).isNotEqualTo(taskListDTO2);
        taskListDTO2.setId(taskListDTO1.getId());
        assertThat(taskListDTO1).isEqualTo(taskListDTO2);
        taskListDTO2.setId(2L);
        assertThat(taskListDTO1).isNotEqualTo(taskListDTO2);
        taskListDTO1.setId(null);
        assertThat(taskListDTO1).isNotEqualTo(taskListDTO2);
    }
}
