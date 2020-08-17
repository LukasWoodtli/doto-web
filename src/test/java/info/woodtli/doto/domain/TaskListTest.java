package info.woodtli.doto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import info.woodtli.doto.web.rest.TestUtil;

public class TaskListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskList.class);
        TaskList taskList1 = new TaskList();
        taskList1.setId(1L);
        TaskList taskList2 = new TaskList();
        taskList2.setId(taskList1.getId());
        assertThat(taskList1).isEqualTo(taskList2);
        taskList2.setId(2L);
        assertThat(taskList1).isNotEqualTo(taskList2);
        taskList1.setId(null);
        assertThat(taskList1).isNotEqualTo(taskList2);
    }
}
