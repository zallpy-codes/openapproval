package com.zallpy.openapproval.common.exception;

/**
 * Exception thrown when business validation fails.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message);
    }

}