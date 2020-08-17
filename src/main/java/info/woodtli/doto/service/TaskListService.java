package info.woodtli.doto.service;

import info.woodtli.doto.domain.TaskList;
import info.woodtli.doto.repository.TaskListRepository;
import info.woodtli.doto.repository.search.TaskListSearchRepository;
import info.woodtli.doto.service.dto.TaskListDTO;
import info.woodtli.doto.service.mapper.TaskListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TaskList}.
 */
@Service
@Transactional
public class TaskListService {

    private final Logger log = LoggerFactory.getLogger(TaskListService.class);

    private final TaskListRepository taskListRepository;

    private final TaskListMapper taskListMapper;

    private final TaskListSearchRepository taskListSearchRepository;

    public TaskListService(TaskListRepository taskListRepository, TaskListMapper taskListMapper, TaskListSearchRepository taskListSearchRepository) {
        this.taskListRepository = taskListRepository;
        this.taskListMapper = taskListMapper;
        this.taskListSearchRepository = taskListSearchRepository;
    }

    /**
     * Save a taskList.
     *
     * @param taskListDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskListDTO save(TaskListDTO taskListDTO) {
        log.debug("Request to save TaskList : {}", taskListDTO);
        TaskList taskList = taskListMapper.toEntity(taskListDTO);
        taskList = taskListRepository.save(taskList);
        TaskListDTO result = taskListMapper.toDto(taskList);
        taskListSearchRepository.save(taskList);
        return result;
    }

    /**
     * Get all the taskLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskLists");
        return taskListRepository.findAll(pageable)
            .map(taskListMapper::toDto);
    }


    /**
     * Get one taskList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskListDTO> findOne(Long id) {
        log.debug("Request to get TaskList : {}", id);
        return taskListRepository.findById(id)
            .map(taskListMapper::toDto);
    }

    /**
     * Delete the taskList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskList : {}", id);
        taskListRepository.deleteById(id);
        taskListSearchRepository.deleteById(id);
    }

    /**
     * Search for the taskList corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskListDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaskLists for query {}", query);
        return taskListSearchRepository.search(queryStringQuery(query), pageable)
            .map(taskListMapper::toDto);
    }
}
