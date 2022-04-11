package com.apanin.todo.user;

import com.apanin.todo.entity.UserEntity;
import com.apanin.todo.sample.rest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper
public interface UserToUserEntityMapper {
    UserToUserEntityMapper INSTANCE = Mappers.getMapper(UserToUserEntityMapper.class);

    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity entity);

    default Date map(OffsetDateTime value) {
        return Date.from(value.toInstant());
    }

    default OffsetDateTime map(Date value) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()), ZoneId.systemDefault());
    }
}
