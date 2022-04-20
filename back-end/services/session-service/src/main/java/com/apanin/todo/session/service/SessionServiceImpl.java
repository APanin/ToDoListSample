package com.apanin.todo.session.service;

import com.apanin.todo.entity.UserEntity;
import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.AuthRequest;
import com.apanin.todo.sample.rest.model.Role;
import com.apanin.todo.sample.rest.model.User;
import com.apanin.todo.security.JwtService;
import com.apanin.todo.session.AuthResponse;
import com.apanin.todo.session.SessionService;
import com.apanin.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public SessionServiceImpl(@Autowired AuthenticationManager authenticationManager, @Autowired UserService userService,
                             @Autowired PasswordEncoder encoder, @Autowired JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse createSession(AuthRequest authRequest) throws BusinessException, TechnicalException {
        final User user = userService.getUserByLogin(authRequest.getLogin());
        if (!encoder.matches(authRequest.getPassword(), user.getPassword()))
            throw new BusinessException("Incorrect password!");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token =  jwtService.generate(userDetails);
        user.setPassword(null);
        final AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(user);
        return response;
    }

    @Override
    public boolean isUserAuthorizedForEndpoint(String endpointUrl) throws BusinessException, TechnicalException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String currentUserName;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        } else {
            throw new BusinessException("Not authorized");
        }
        final User user = userService.getUserByLogin(currentUserName);
        return user.getRoles().stream()
                .anyMatch(role -> role.getPermissions().stream()
                        .anyMatch(permission -> permission.getRoute().equals(endpointUrl)));
    }
}
