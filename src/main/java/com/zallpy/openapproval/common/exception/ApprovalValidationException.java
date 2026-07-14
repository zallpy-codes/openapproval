package com.zallpy.openapproval.common.exception;

import java.io.Serial;

/**
 * Exception thrown when approval validation fails.
 *
 * <p>
 * This exception indicates that an approval request, workflow,
 * policy, or other approval component violates one or more
 * business validation rules.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new validation exception.
     *
     * @param message exception message
     */
    public ApprovalValidationException(final String message) {
        super(message);
    }

    /**
     * Creates a new validation exception.
     *
     * @param message exception message
     * @param cause root cause
     */
    public ApprovalValidationException(final String message,
                                       final Throwable cause) {
        super(message, cause);
    }

}