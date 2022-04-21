package com.apanin.todo.security.config;

import com.apanin.todo.config.api.SecurityParams;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.apanin.todo.security")
public class SecurityParamsImpl implements SecurityParams {

    private String jwtSecret;
    private long jwtTtl;

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    public String getJwtSecret() {
        return jwtSecret;
    }

    @Override
    public long getJwtTtl() {
        return jwtTtl;
    }

    public void setJwtTtl(long jwtTtl) {
        this.jwtTtl = jwtTtl;
    }
}
