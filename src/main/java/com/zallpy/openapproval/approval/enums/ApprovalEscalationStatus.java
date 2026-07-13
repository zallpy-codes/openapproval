package com.zallpy.openapproval.approval.enums;

/**
 * Represents the processing status of an approval escalation.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalEscalationStatus {

    /**
     * Escalation has been scheduled.
     */
    SCHEDULED,

    /**
     * Escalation is queued.
     */
    QUEUED,

    /**
     * Escalation is being processed.
     */
    PROCESSING,

    /**
     * Escalation completed successfully.
     */
    COMPLETED,

    /**
     * Escalation failed.
     */
    FAILED,

    /**
     * Escalation was cancelled.
     */
    CANCELLED,

    /**
     * Escalation expired.
     */
    EXPIRED
}