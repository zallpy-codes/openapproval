package com.zallpy.openapproval.common.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}