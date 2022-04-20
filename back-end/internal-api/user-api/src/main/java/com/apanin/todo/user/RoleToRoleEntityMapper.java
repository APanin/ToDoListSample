package com.apanin.todo.user;

import com.apanin.todo.entity.RoleEntity;
import com.apanin.todo.sample.rest.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface RoleToRoleEntityMapper {
    RoleToRoleEntityMapper INSTANCE = Mappers.getMapper(RoleToRoleEntityMapper.class);

    RoleEntity roleToRoleEntity(Role Role);
    Role roleEntityToRole(RoleEntity entity);

    default Date map(OffsetDateTime value) {
        return value == null ? null : Date.from(value.toInstant());
    }

    default OffsetDateTime map(Date value) {
        return value == null ? null : OffsetDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()), ZoneId.systemDefault());
    }
}
