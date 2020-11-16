package com.itsdits.grocerylist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * ControllerExceptionHandler.java - This class uses the {@code @ControllerAdvice} annotation to provide
 * global exception handling for this app. Utilizes {@link ErrorMessage} for ErrorMessage objects.
 *
 * @author Tim Dillon
 * @version 1.0
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * This handles the ResourceNotFoundException that is thrown when a method cannot find a resource such as
     * an object having {@code id = 3}.
     *
     * @param exception ResourceNotFoundException that holds the error message
     * @param request WebRequest that holds the description of the request
     * @return Error message and HttpStatus.NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * This handles various exceptions not caught by other handlers. Generic global handler implementation.
     *
     * @param exception Exception that holds the error message
     * @param request WebRequest that holds the description of the request
     * @return Error message and HttpStatus.INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
