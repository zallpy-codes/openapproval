package com.zallpy.openapproval.common.exception;

/**
 * Exception thrown when the authenticated user lacks permission.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message) {
        super(message);
    }

}