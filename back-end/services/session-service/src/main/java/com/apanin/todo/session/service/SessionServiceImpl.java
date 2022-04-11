package com.apanin.todo.session.service;

import com.apanin.todo.exception.BusinessException;
import com.apanin.todo.exception.TechnicalException;
import com.apanin.todo.sample.rest.model.AuthRequest;
import com.apanin.todo.security.JwtService;
import com.apanin.todo.session.SessionService;
import com.apanin.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    final AuthenticationManager authenticationManager;
    final UserService userRepository;
    final PasswordEncoder encoder;
    final JwtService jwtService;

    public SessionServiceImpl(@Autowired AuthenticationManager authenticationManager, @Autowired UserService userRepository,
                             @Autowired PasswordEncoder encoder, @Autowired JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public String createSession(AuthRequest authRequest) throws BusinessException, TechnicalException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generate(userDetails);
    }

    @Override
    public void deleteSession(String token) throws BusinessException, TechnicalException {

    }
}
