package com.apanin.todo.config.api;

public interface SecurityParams {
    String getJwtSecret();
    long getJwtTtl();
}
