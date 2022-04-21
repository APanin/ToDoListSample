package com.apanin.todo.exception;

public class TechnicalException extends Exception {
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
