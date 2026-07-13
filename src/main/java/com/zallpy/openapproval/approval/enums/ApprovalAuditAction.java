package com.zallpy.openapproval.approval.enums;

/**
 * Represents an action recorded in the approval audit trail.
 *
 * <p>
 * Every significant event during approval execution should
 * produce one audit record.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalAuditAction {

    /**
     * Approval request created.
     */
    REQUEST_CREATED,

    /**
     * Approval request submitted.
     */
    REQUEST_SUBMITTED,

    /**
     * Execution started.
     */
    EXECUTION_STARTED,

    /**
     * Stage started.
     */
    STAGE_STARTED,

    /**
     * Stage completed.
     */
    STAGE_COMPLETED,

    /**
     * Task created.
     */
    TASK_CREATED,

    /**
     * Task assigned.
     */
    TASK_ASSIGNED,

    /**
     * Task completed.
     */
    TASK_COMPLETED,

    /**
     * Approval granted.
     */
    APPROVED,

    /**
     * Request rejected.
     */
    REJECTED,

    /**
     * Request returned.
     */
    RETURNED,

    /**
     * Request cancelled.
     */
    CANCELLED,

    /**
     * Reminder sent.
     */
    REMINDER_SENT,

    /**
     * Escalation executed.
     */
    ESCALATED,

    /**
     * Delegation executed.
     */
    DELEGATED,

    /**
     * Comment added.
     */
    COMMENT_ADDED,

    /**
     * Attachment added.
     */
    ATTACHMENT_ADDED,

    /**
     * Approval execution completed.
     */
    EXECUTION_COMPLETED,

    /**
     * Approval execution expired.
     */
    EXECUTION_EXPIRED,

    /**
     * System-generated event.
     */
    SYSTEM_EVENT
}