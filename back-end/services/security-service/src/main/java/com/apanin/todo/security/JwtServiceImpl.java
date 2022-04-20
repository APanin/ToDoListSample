package com.apanin.todo.security;

import com.apanin.todo.config.api.SecurityParams;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    private final SecurityParams params;

    private static final String jwtSecret = "RTFTt6lvR8tUDMfMvsBJPiTvaADhEITUAgXCxaEGz1BzDLFPsg3ZmsRJCTQzMAhyuEdsKM8MfdSDsp92";
    private static final String jwtIssuer = "apanin.com";

    public JwtServiceImpl(@Autowired SecurityParams params) {
        this.params = params;
    }

    @Override
    public String generate(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuer(jwtIssuer)
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()
                        + params.getJwtTtl())).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    @Override
    public boolean validate(UserDetails userDetails, String token) {
        final String username = getUserLogin(token);
        return (username.equals(userDetails.getUsername()) && validate(token)) && getExpirationDate(token).after(new Date());
    }

    @Override
    public String getUserLogin(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    private Date getExpirationDate(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    private boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
