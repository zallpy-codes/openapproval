package com.zallpy.openapproval.approval.enums;

/**
 * Represents the runtime status of an approval delegation.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalDelegationStatus {

    /**
     * Delegation has been created.
     */
    PENDING,

    /**
     * Delegation has been accepted.
     */
    ACCEPTED,

    /**
     * Delegation was declined.
     */
    DECLINED,

    /**
     * Delegation completed successfully.
     */
    COMPLETED,

    /**
     * Delegation has been revoked.
     */
    REVOKED,

    /**
     * Delegation expired before acceptance.
     */
    EXPIRED,

    /**
     * Delegation was cancelled.
     */
    CANCELLED
}