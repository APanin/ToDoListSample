package com.apanin.todo.service.rest.task;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.api.TaskApiDelegate;
import com.apanin.todo.sample.rest.api.TasksApiDelegate;
import com.apanin.todo.sample.rest.model.Task;
import com.apanin.todo.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController implements TaskApiDelegate, TasksApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final String TASK_GET_URL_PART = "/task/";

    private final TaskService taskService;
    private final WebConfig webConfig;

    public TaskController(@Autowired TaskService taskService, @Autowired WebConfig webConfig) {
        this.taskService = taskService;
        this.webConfig = webConfig;
    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TaskApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Task>> tasksGet(Long userId, Integer page) {
        try {
            return ResponseEntity.ok(taskService.listTasks(userId, page));
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> taskDelete(Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Task> taskGet(Long taskId) {
        try {
            return ResponseEntity.ok(taskService.getTask(taskId));
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Task> taskPost(Task task) {
        try {
            final long taskId = taskService.createTask(task);
            return ResponseEntity.created(URI.create(webConfig.getBaseUrl() + TASK_GET_URL_PART + taskId)).build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> taskPut(Task task) {
        try {
            taskService.updateTask(task);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
