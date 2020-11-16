package com.itsdits.grocerylist.exception;

/**
 * ResourceNotFoundException.java - This class extends RuntimeException to provide a custom message
 * when a method attempts to operate on a resource that doesn't exist, resulting in an exception being thrown.
 * An example would be a {@code GET} method failing to find an object with {@code id = 3}.
 *
 * @author Tim Dillon
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * This throws a ResourceNotFoundException with the error message passed as a param.
     *
     * @param msg String of error message that caused the exception to be thrown
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
