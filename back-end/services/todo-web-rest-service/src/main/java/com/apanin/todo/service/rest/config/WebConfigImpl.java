package com.apanin.todo.service.rest.config;

import com.apanin.todo.config.api.WebConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.apanin.todo.web")
public class WebConfigImpl implements WebConfig {

    private String baseUrl;
    private Integer itemsOnPage;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public Integer getItemsOnPage() {
        return itemsOnPage;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setItemsOnPage(Integer itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }
}
