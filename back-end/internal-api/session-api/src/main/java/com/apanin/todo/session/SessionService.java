package com.apanin.todo.session;

import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.AuthRequest;

public interface SessionService {
    String createSession(AuthRequest authRequest) throws BusinessException, TechnicalException;
    void deleteSession(String token) throws BusinessException, TechnicalException;
}
