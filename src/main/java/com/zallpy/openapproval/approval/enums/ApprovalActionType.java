package com.zallpy.openapproval.approval.enums;

/**
 * Represents the type of action performed during
 * an approval execution.
 *
 * <p>
 * Every significant operation performed by a user or the
 * workflow engine is recorded as an approval action.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalActionType {

    /**
     * Approval request was created.
     */
    CREATED,

    /**
     * Task was assigned.
     */
    ASSIGNED,

    /**
     * Approval task started.
     */
    STARTED,

    /**
     * Request approved.
     */
    APPROVED,

    /**
     * Request rejected.
     */
    REJECTED,

    /**
     * Request returned for correction.
     */
    RETURNED,

    /**
     * Task delegated.
     */
    DELEGATED,

    /**
     * Task escalated.
     */
    ESCALATED,

    /**
     * Task reassigned.
     */
    REASSIGNED,

    /**
     * Reminder sent.
     */
    REMINDER_SENT,

    /**
     * Comment added.
     */
    COMMENT_ADDED,

    /**
     * Attachment added.
     */
    ATTACHMENT_ADDED,

    /**
     * Stage skipped.
     */
    SKIPPED,

    /**
     * Approval request cancelled.
     */
    CANCELLED,

    /**
     * Approval request expired.
     */
    EXPIRED,

    /**
     * Approval request completed.
     */
    COMPLETED
}