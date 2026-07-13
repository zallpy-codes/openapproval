package com.zallpy.openapproval.approval.enums;

/**
 * Events capable of triggering notifications.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationEvent {

    /**
     * Request has been created.
     */
    REQUEST_CREATED,

    /**
     * Request submitted.
     */
    REQUEST_SUBMITTED,

    /**
     * Workflow started.
     */
    WORKFLOW_STARTED,

    /**
     * Stage started.
     */
    STAGE_STARTED,

    /**
     * Stage completed.
     */
    STAGE_COMPLETED,

    /**
     * Task assigned.
     */
    TASK_ASSIGNED,

    /**
     * Task delegated.
     */
    TASK_DELEGATED,

    /**
     * Reminder generated.
     */
    REMINDER,

    /**
     * Escalation occurred.
     */
    ESCALATED,

    /**
     * Request approved.
     */
    REQUEST_APPROVED,

    /**
     * Request rejected.
     */
    REQUEST_REJECTED,

    /**
     * Request returned.
     */
    REQUEST_RETURNED,

    /**
     * Request cancelled.
     */
    REQUEST_CANCELLED,

    /**
     * Workflow completed.
     */
    WORKFLOW_COMPLETED,

    /**
     * Workflow failed.
     */
    WORKFLOW_FAILED
}