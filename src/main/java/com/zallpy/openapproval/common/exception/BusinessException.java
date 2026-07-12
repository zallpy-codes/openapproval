package com.zallpy.openapproval.common.exception;

/**
 * Base exception for all business-related exceptions.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}