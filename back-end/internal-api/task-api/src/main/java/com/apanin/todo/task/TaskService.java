package com.apanin.todo.task;

import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.Task;

import java.util.List;

public interface TaskService {
    long createTask(Task task) throws BusinessException, TechnicalException;
    void updateTask(Task task) throws BusinessException, TechnicalException;
    void deleteTask(long taskId) throws BusinessException, TechnicalException;
    Task getTask(long id) throws BusinessException, TechnicalException;
    List<Task> listTasks(long userId, int pageNumber) throws BusinessException, TechnicalException;
}
