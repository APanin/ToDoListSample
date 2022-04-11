package com.apanin.todo.repository;

import com.apanin.todo.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t where t.userEntity.id = :userId")
    Page<TaskEntity> listPagedByUserId(@Param("userId") long userId, Pageable pageable);
}
