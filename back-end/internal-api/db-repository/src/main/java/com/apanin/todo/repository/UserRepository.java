package com.apanin.todo.repository;

import com.apanin.todo.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u where u.login = :login")
    Optional<UserEntity> findUserEntityByLogin(@Param("login") String login);
}
