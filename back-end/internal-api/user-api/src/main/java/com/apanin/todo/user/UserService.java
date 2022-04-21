package com.apanin.todo.user;

import com.apanin.todo.entity.UserEntity;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.Permission;
import com.apanin.todo.sample.rest.model.Role;
import com.apanin.todo.sample.rest.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> listUsers(int page) throws BusinessException, TechnicalException;
    User getUser(Long userId) throws BusinessException, TechnicalException;
    UserDetails getUserDetailsByLogin(String login) throws BusinessException;
    User getUserByLogin(String login) throws BusinessException;
    UserEntity getUserEntityByLogin(String login) throws BusinessException;
    Long createUser(User user) throws BusinessException, TechnicalException;
    void updateUser(User user) throws BusinessException, TechnicalException;
    void deleteUser(Long userId) throws BusinessException, TechnicalException;
    List<Role> listRoles(int page) throws BusinessException, TechnicalException;
    List<Permission> listPermissions(int page) throws BusinessException, TechnicalException;
}
