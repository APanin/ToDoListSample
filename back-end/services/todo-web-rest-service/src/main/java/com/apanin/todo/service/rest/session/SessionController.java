package com.apanin.todo.service.rest.session;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.api.SessionApiDelegate;
import com.apanin.todo.sample.rest.model.AuthRequest;
import com.apanin.todo.sample.rest.model.User;
import com.apanin.todo.service.rest.user.UserController;
import com.apanin.todo.session.AuthResponse;
import com.apanin.todo.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class SessionController implements SessionApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final String SESSION_GET_URL_PART = "/session/";

    private final SessionService sessionService;
    private final WebConfig webConfig;

    public SessionController(@Autowired SessionService sessionService, @Autowired WebConfig webConfig) {
        this.sessionService = sessionService;
        this.webConfig = webConfig;
    }

    @Override
    public ResponseEntity<User> sessionCreate(AuthRequest authRequest) {
        try {
            final AuthResponse response = sessionService.createSession(authRequest);
            return ResponseEntity.created(URI.create(webConfig.getBaseUrl() + SESSION_GET_URL_PART))
                    .header("Authorization", response.getToken()).body(response.getUser());
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Void> sessionAuthorization( String endpointUrl) {
        try {
            final boolean isAuthorized = sessionService.isUserAuthorizedForEndpoint(endpointUrl);
            return isAuthorized ? ResponseEntity.ok().build() : new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (TechnicalException | RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
