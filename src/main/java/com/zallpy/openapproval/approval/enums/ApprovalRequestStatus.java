package com.zallpy.openapproval.approval.enums;

/**
 * Represents the runtime lifecycle of an approval request.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalRequestStatus {

    /**
     * Request has been created but not submitted.
     */
    DRAFT,

    /**
     * Request has been submitted.
     */
    SUBMITTED,

    /**
     * Approval execution is in progress.
     */
    IN_PROGRESS,

    /**
     * Waiting for additional information.
     */
    ON_HOLD,

    /**
     * Request has been approved.
     */
    APPROVED,

    /**
     * Request has been rejected.
     */
    REJECTED,

    /**
     * Request has been cancelled.
     */
    CANCELLED,

    /**
     * Request has expired.
     */
    EXPIRED,

    /**
     * Request has been withdrawn.
     */
    WITHDRAWN,

    /**
     * Request completed successfully.
     */
    COMPLETED
}