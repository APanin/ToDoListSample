package com.apanin.todo.repository;

import com.apanin.todo.entity.PermissionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository  extends PagingAndSortingRepository<PermissionEntity, Long> {
}
