package com.apanin.todo.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface JwtService {
    String generate(UserDetails userDetails);
    boolean validate(UserDetails userDetails, String token);
    String getUserLogin(String token);
}
