package com.zallpy.openapproval.common.exception;

/**
 * Exception thrown when a business conflict occurs.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ConflictException extends BusinessException {

    public ConflictException(String message) {
        super(message);
    }

}