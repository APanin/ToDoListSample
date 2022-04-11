package com.apanin.todo.user;

import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<User> listUsers(int page) throws BusinessException, TechnicalException;
    User getUser(Long userId) throws BusinessException, TechnicalException;
    UserDetails getUserDetailsByLogin(String login) throws BusinessException;
    Long createUser(User user) throws BusinessException, TechnicalException;
    void updateUser(User user) throws BusinessException, TechnicalException;
    void deleteUser(Long userId) throws BusinessException, TechnicalException;
}
