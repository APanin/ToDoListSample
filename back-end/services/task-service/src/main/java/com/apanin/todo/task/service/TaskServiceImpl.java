package com.apanin.todo.task.service;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.entity.TaskEntity;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.repository.TaskRepository;
import com.apanin.todo.sample.rest.model.Task;
import com.apanin.todo.task.TaskService;
import com.apanin.todo.task.TaskToTaskEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final WebConfig config;

    public TaskServiceImpl(@Autowired TaskRepository taskRepository, @Autowired WebConfig config) {
        this.taskRepository = taskRepository;
        this.config = config;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public long createTask(Task task) throws BusinessException, TechnicalException {
        final TaskEntity entity = TaskToTaskEntityMapper.INSTANCE.taskToTaskEntity(task);
        final TaskEntity persistedEntity = taskRepository.save(entity);
        return persistedEntity.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateTask(Task task) throws BusinessException, TechnicalException {
        final TaskEntity entity = TaskToTaskEntityMapper.INSTANCE.taskToTaskEntity(task);
        taskRepository.save(entity);
    }

    @Override
    public void deleteTask(long taskId) throws BusinessException, TechnicalException {
        final Optional<TaskEntity> entityOpt = taskRepository.findById(taskId);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by id " + taskId);
        taskRepository.delete(entityOpt.get());
    }

    @Override
    public Task getTask(long id) throws BusinessException, TechnicalException {
        final Optional<TaskEntity> entityOpt = taskRepository.findById(id);
        if (!entityOpt.isPresent())
            throw new BusinessException("Entity not found by id " + id);
        return TaskToTaskEntityMapper.INSTANCE.taskEntityToTask(entityOpt.get());
    }

    @Override
    public List<Task> listTasks(long userId, int page) throws BusinessException, TechnicalException {
        final Pageable pageable = PageRequest.of(page, config.getItemsOnPage());
        final Page<TaskEntity> taskEntityPage = taskRepository.listPagedByUserId(userId, pageable);
        return taskEntityPage.stream().map(taskEntity -> {
            final Task task = TaskToTaskEntityMapper.INSTANCE.taskEntityToTask(taskEntity);
            task.setCurrentPage(page);
            task.setTotalPages(taskEntityPage.getTotalPages());
            return task;
        }).collect(Collectors.toList());
    }
}
