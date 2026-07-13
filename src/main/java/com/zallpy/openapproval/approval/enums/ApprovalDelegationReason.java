package com.zallpy.openapproval.approval.enums;

/**
 * Represents the business reason for delegating
 * an approval task.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalDelegationReason {

    /**
     * Annual or personal leave.
     */
    LEAVE,

    /**
     * Business travel.
     */
    BUSINESS_TRIP,

    /**
     * Medical leave or illness.
     */
    SICKNESS,

    /**
     * Approver is unavailable.
     */
    UNAVAILABLE,

    /**
     * Workload balancing.
     */
    WORKLOAD,

    /**
     * Temporary reassignment.
     */
    REASSIGNMENT,

    /**
     * Administrative decision.
     */
    ADMINISTRATIVE,

    /**
     * Custom business reason.
     */
    OTHER
}