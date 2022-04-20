package com.apanin.todo.repository;

import com.apanin.todo.entity.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends PagingAndSortingRepository<RoleEntity, Long> {
}
