package com.zallpy.openapproval.common.exception;

/**
 * Exception thrown when authentication is required or has failed.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message);
    }

}