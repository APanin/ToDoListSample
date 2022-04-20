package com.apanin.todo.task;

import com.apanin.todo.entity.TaskEntity;
import com.apanin.todo.sample.rest.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface TaskToTaskEntityMapper {
    TaskToTaskEntityMapper INSTANCE = Mappers.getMapper(TaskToTaskEntityMapper.class);

    @Mapping(source = "isDone", target = "done")
    TaskEntity taskToTaskEntity(Task task);
    @Mapping(source = "done", target = "isDone")
    Task taskEntityToTask(TaskEntity entity);

    default Date map(OffsetDateTime value) {
        return value == null ? null: Date.from(value.toInstant());
    }

    default OffsetDateTime map(Date value) {
        return value == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()), ZoneId.systemDefault());
    }
}
