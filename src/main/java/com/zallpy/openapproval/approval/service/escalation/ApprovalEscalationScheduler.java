package com.zallpy.openapproval.approval.service.escalation;

/**
 * Schedules escalation processing for approval steps.
 *
 * <p>
 * Implementations periodically scan approval steps eligible
 * for escalation and delegate processing to the
 * {@link ApprovalEscalationService}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEscalationScheduler {

    /**
     * Executes escalation processing.
     */
    void processEscalations();

}