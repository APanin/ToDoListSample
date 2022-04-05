package com.apanin.todo.service.rest.session;

import com.apanin.todo.sample.rest.api.SessionApiDelegate;
import com.apanin.todo.sample.rest.model.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionController implements SessionApiDelegate {


    @Override
    public ResponseEntity<Void> sessionCreate(AuthRequest authRequest) {
        return SessionApiDelegate.super.sessionCreate(authRequest);
    }

    @Override
    public ResponseEntity<Void> sessionDelete(String authorization) {
        return SessionApiDelegate.super.sessionDelete(authorization);
    }
}
