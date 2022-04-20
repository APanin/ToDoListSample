package com.apanin.todo.session;

import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.AuthRequest;

public interface SessionService {
    AuthResponse createSession(AuthRequest authRequest) throws BusinessException, TechnicalException;
    boolean isUserAuthorizedForEndpoint(String endpointUrl) throws BusinessException, TechnicalException;
}
