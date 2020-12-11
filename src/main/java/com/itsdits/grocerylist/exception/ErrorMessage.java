package com.itsdits.grocerylist.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {

    private HttpStatus status;
    private String message;
    private String debugMessage;
    private List<SubError> subErrors;

    private ErrorMessage() {
        LocalDateTime timestamp = LocalDateTime.now();
    }

    ErrorMessage(HttpStatus status) {
        this();
        this.status = status;
    }

    ErrorMessage(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ErrorMessage(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
