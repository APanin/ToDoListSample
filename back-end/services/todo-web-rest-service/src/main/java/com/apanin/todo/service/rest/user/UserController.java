package com.apanin.todo.service.rest.user;

import com.apanin.todo.sample.rest.api.UserApiDelegate;
import com.apanin.todo.sample.rest.api.UsersApiDelegate;
import com.apanin.todo.sample.rest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

public class UserController implements UserApiDelegate, UsersApiDelegate {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return UserApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<List<User>> usersGet(Integer limit, Integer afterId) {
        return UsersApiDelegate.super.usersGet(limit, afterId);
    }

    @Override
    public ResponseEntity<Void> userDelete(Long taskId) {
        return UserApiDelegate.super.userDelete(taskId);
    }

    @Override
    public ResponseEntity<User> userGet(Long id) {
        return UserApiDelegate.super.userGet(id);
    }

    @Override
    public ResponseEntity<User> userPost(User user) {
        return UserApiDelegate.super.userPost(user);
    }

    @Override
    public ResponseEntity<Void> userPut(User user) {
        return UserApiDelegate.super.userPut(user);
    }
}
