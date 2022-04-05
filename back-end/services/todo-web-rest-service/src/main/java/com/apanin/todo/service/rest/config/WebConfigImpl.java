package com.apanin.todo.service.rest.config;

import com.apanin.todo.config.api.WebConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.apanin.todo.web")
public class WebConfigImpl implements WebConfig {

    private String baseUrl;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }
}
