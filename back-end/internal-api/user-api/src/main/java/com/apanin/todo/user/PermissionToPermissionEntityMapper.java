package com.apanin.todo.user;

import com.apanin.todo.entity.PermissionEntity;
import com.apanin.todo.sample.rest.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface PermissionToPermissionEntityMapper {
    PermissionToPermissionEntityMapper INSTANCE = Mappers.getMapper(PermissionToPermissionEntityMapper.class);

    PermissionEntity permissionToPermissionEntity(Permission Permission);
    Permission permissionEntityToPermission(PermissionEntity entity);

    default Date map(OffsetDateTime value) {
        return value == null ? null : Date.from(value.toInstant());
    }

    default OffsetDateTime map(Date value) {
        return value == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()), ZoneId.systemDefault());
    }
}
