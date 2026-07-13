package com.zallpy.openapproval.approval.enums;

/**
 * Represents the lifecycle state of an approval request.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalStatus {

    /**
     * Request is being prepared.
     */
    DRAFT,

    /**
     * Waiting to be submitted.
     */
    READY,

    /**
     * Submitted and awaiting processing.
     */
    PENDING,

    /**
     * Currently under review.
     */
    IN_PROGRESS,

    /**
     * Waiting for additional information.
     */
    ON_HOLD,

    /**
     * Approved successfully.
     */
    APPROVED,

    /**
     * Rejected by an approver.
     */
    REJECTED,

    /**
     * Returned to the requester for correction.
     */
    RETURNED,

    /**
     * Cancelled by the requester or the system.
     */
    CANCELLED,

    /**
     * Escalated to another approver.
     */
    ESCALATED,

    /**
     * Approval has expired.
     */
    EXPIRED,

    /**
     * Approval process failed due to an unexpected error.
     */
    FAILED
}