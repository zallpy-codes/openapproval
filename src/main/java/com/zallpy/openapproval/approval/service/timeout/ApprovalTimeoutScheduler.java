package com.zallpy.openapproval.approval.service.timeout;

/**
 * Schedules timeout processing for approval steps.
 *
 * <p>
 * Implementations periodically scan approval steps eligible
 * for timeout processing and delegate execution to the
 * {@link ApprovalTimeoutService}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimeoutScheduler {

    /**
     * Executes timeout processing.
     */
    void processTimeouts();

}