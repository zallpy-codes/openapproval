package com.zallpy.openapproval.approval.enums;

/**
 * Represents the runtime lifecycle of an approval task.
 *
 * <p>
 * An approval task is assigned to a single approver during the
 * execution of an approval stage. This enum tracks the current
 * processing state of that task.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalTaskStatus {

    /**
     * Task has been created but not yet assigned.
     */
    PENDING,

    /**
     * Task has been assigned to an approver.
     */
    ASSIGNED,

    /**
     * Task is currently being processed.
     */
    IN_PROGRESS,

    /**
     * Task has been approved.
     */
    APPROVED,

    /**
     * Task has been rejected.
     */
    REJECTED,

    /**
     * Task has been delegated to another approver.
     */
    DELEGATED,

    /**
     * Task has been escalated.
     */
    ESCALATED,

    /**
     * Task has been cancelled.
     */
    CANCELLED,

    /**
     * Task expired before completion.
     */
    EXPIRED
}