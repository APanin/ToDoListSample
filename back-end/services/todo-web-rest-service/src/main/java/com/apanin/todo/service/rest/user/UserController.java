package com.apanin.todo.service.rest.user;

import com.apanin.todo.user.UserService;
import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.api.UserApiDelegate;
import com.apanin.todo.sample.rest.api.UsersApiDelegate;
import com.apanin.todo.sample.rest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController implements UserApiDelegate, UsersApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final String USER_GET_URL_PART = "/user/";

    private final UserService userService;
    private final WebConfig webConfig;

    public UserController(@Autowired UserService userService, @Autowired WebConfig webConfig) {
        this.userService = userService;
        this.webConfig = webConfig;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UserApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<List<User>> usersGet(Integer page) {
        try {
            return ResponseEntity.ok(userService.listUsers(page));
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> userDelete(Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<User> userGet(Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<User> userPost(User user) {
        try {
            final long userId = userService.createUser(user);
            return ResponseEntity.created(URI.create(webConfig.getBaseUrl() + USER_GET_URL_PART + userId)).build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> userPut(User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
