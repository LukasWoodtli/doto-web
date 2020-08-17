package info.woodtli.doto.web.rest;

import info.woodtli.doto.service.TaskListService;
import info.woodtli.doto.web.rest.errors.BadRequestAlertException;
import info.woodtli.doto.service.dto.TaskListDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link info.woodtli.doto.domain.TaskList}.
 */
@RestController
@RequestMapping("/api")
public class TaskListResource {

    private final Logger log = LoggerFactory.getLogger(TaskListResource.class);

    private static final String ENTITY_NAME = "taskList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskListService taskListService;

    public TaskListResource(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    /**
     * {@code POST  /task-lists} : Create a new taskList.
     *
     * @param taskListDTO the taskListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskListDTO, or with status {@code 400 (Bad Request)} if the taskList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-lists")
    public ResponseEntity<TaskListDTO> createTaskList(@RequestBody TaskListDTO taskListDTO) throws URISyntaxException {
        log.debug("REST request to save TaskList : {}", taskListDTO);
        if (taskListDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskListDTO result = taskListService.save(taskListDTO);
        return ResponseEntity.created(new URI("/api/task-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-lists} : Updates an existing taskList.
     *
     * @param taskListDTO the taskListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskListDTO,
     * or with status {@code 400 (Bad Request)} if the taskListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-lists")
    public ResponseEntity<TaskListDTO> updateTaskList(@RequestBody TaskListDTO taskListDTO) throws URISyntaxException {
        log.debug("REST request to update TaskList : {}", taskListDTO);
        if (taskListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskListDTO result = taskListService.save(taskListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-lists} : get all the taskLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskLists in body.
     */
    @GetMapping("/task-lists")
    public ResponseEntity<List<TaskListDTO>> getAllTaskLists(Pageable pageable) {
        log.debug("REST request to get a page of TaskLists");
        Page<TaskListDTO> page = taskListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-lists/:id} : get the "id" taskList.
     *
     * @param id the id of the taskListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-lists/{id}")
    public ResponseEntity<TaskListDTO> getTaskList(@PathVariable Long id) {
        log.debug("REST request to get TaskList : {}", id);
        Optional<TaskListDTO> taskListDTO = taskListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskListDTO);
    }

    /**
     * {@code DELETE  /task-lists/:id} : delete the "id" taskList.
     *
     * @param id the id of the taskListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-lists/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        log.debug("REST request to delete TaskList : {}", id);
        taskListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
