package info.woodtli.doto.web.rest;

import info.woodtli.doto.DotoApp;
import info.woodtli.doto.domain.TaskList;
import info.woodtli.doto.repository.TaskListRepository;
import info.woodtli.doto.repository.search.TaskListSearchRepository;
import info.woodtli.doto.service.TaskListService;
import info.woodtli.doto.service.dto.TaskListDTO;
import info.woodtli.doto.service.mapper.TaskListMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskListResource} REST controller.
 */
@SpringBootTest(classes = DotoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskListMapper taskListMapper;

    @Autowired
    private TaskListService taskListService;

    /**
     * This repository is mocked in the info.woodtli.doto.repository.search test package.
     *
     * @see info.woodtli.doto.repository.search.TaskListSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaskListSearchRepository mockTaskListSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskListMockMvc;

    private TaskList taskList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskList createEntity(EntityManager em) {
        TaskList taskList = new TaskList()
            .name(DEFAULT_NAME);
        return taskList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskList createUpdatedEntity(EntityManager em) {
        TaskList taskList = new TaskList()
            .name(UPDATED_NAME);
        return taskList;
    }

    @BeforeEach
    public void initTest() {
        taskList = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskList() throws Exception {
        int databaseSizeBeforeCreate = taskListRepository.findAll().size();
        // Create the TaskList
        TaskListDTO taskListDTO = taskListMapper.toDto(taskList);
        restTaskListMockMvc.perform(post("/api/task-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskListDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskList in the database
        List<TaskList> taskListList = taskListRepository.findAll();
        assertThat(taskListList).hasSize(databaseSizeBeforeCreate + 1);
        TaskList testTaskList = taskListList.get(taskListList.size() - 1);
        assertThat(testTaskList.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the TaskList in Elasticsearch
        verify(mockTaskListSearchRepository, times(1)).save(testTaskList);
    }

    @Test
    @Transactional
    public void createTaskListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskListRepository.findAll().size();

        // Create the TaskList with an existing ID
        taskList.setId(1L);
        TaskListDTO taskListDTO = taskListMapper.toDto(taskList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskListMockMvc.perform(post("/api/task-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskList in the database
        List<TaskList> taskListList = taskListRepository.findAll();
        assertThat(taskListList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaskList in Elasticsearch
        verify(mockTaskListSearchRepository, times(0)).save(taskList);
    }


    @Test
    @Transactional
    public void getAllTaskLists() throws Exception {
        // Initialize the database
        taskListRepository.saveAndFlush(taskList);

        // Get all the taskListList
        restTaskListMockMvc.perform(get("/api/task-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTaskList() throws Exception {
        // Initialize the database
        taskListRepository.saveAndFlush(taskList);

        // Get the taskList
        restTaskListMockMvc.perform(get("/api/task-lists/{id}", taskList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTaskList() throws Exception {
        // Get the taskList
        restTaskListMockMvc.perform(get("/api/task-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskList() throws Exception {
        // Initialize the database
        taskListRepository.saveAndFlush(taskList);

        int databaseSizeBeforeUpdate = taskListRepository.findAll().size();

        // Update the taskList
        TaskList updatedTaskList = taskListRepository.findById(taskList.getId()).get();
        // Disconnect from session so that the updates on updatedTaskList are not directly saved in db
        em.detach(updatedTaskList);
        updatedTaskList
            .name(UPDATED_NAME);
        TaskListDTO taskListDTO = taskListMapper.toDto(updatedTaskList);

        restTaskListMockMvc.perform(put("/api/task-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskListDTO)))
            .andExpect(status().isOk());

        // Validate the TaskList in the database
        List<TaskList> taskListList = taskListRepository.findAll();
        assertThat(taskListList).hasSize(databaseSizeBeforeUpdate);
        TaskList testTaskList = taskListList.get(taskListList.size() - 1);
        assertThat(testTaskList.getName()).isEqualTo(UPDATED_NAME);

        // Validate the TaskList in Elasticsearch
        verify(mockTaskListSearchRepository, times(1)).save(testTaskList);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskList() throws Exception {
        int databaseSizeBeforeUpdate = taskListRepository.findAll().size();

        // Create the TaskList
        TaskListDTO taskListDTO = taskListMapper.toDto(taskList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskListMockMvc.perform(put("/api/task-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskList in the database
        List<TaskList> taskListList = taskListRepository.findAll();
        assertThat(taskListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaskList in Elasticsearch
        verify(mockTaskListSearchRepository, times(0)).save(taskList);
    }

    @Test
    @Transactional
    public void deleteTaskList() throws Exception {
        // Initialize the database
        taskListRepository.saveAndFlush(taskList);

        int databaseSizeBeforeDelete = taskListRepository.findAll().size();

        // Delete the taskList
        restTaskListMockMvc.perform(delete("/api/task-lists/{id}", taskList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskList> taskListList = taskListRepository.findAll();
        assertThat(taskListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaskList in Elasticsearch
        verify(mockTaskListSearchRepository, times(1)).deleteById(taskList.getId());
    }

    @Test
    @Transactional
    public void searchTaskList() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        taskListRepository.saveAndFlush(taskList);
        when(mockTaskListSearchRepository.search(queryStringQuery("id:" + taskList.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taskList), PageRequest.of(0, 1), 1));

        // Search the taskList
        restTaskListMockMvc.perform(get("/api/_search/task-lists?query=id:" + taskList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
