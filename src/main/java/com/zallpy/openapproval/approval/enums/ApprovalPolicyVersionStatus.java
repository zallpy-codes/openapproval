package com.zallpy.openapproval.approval.enums;

/**
 * Represents the lifecycle status of an approval policy version.
 *
 * <p>
 * Policy versions move through a controlled lifecycle before
 * becoming available for runtime execution.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyVersionStatus {

    /**
     * Work in progress.
     */
    DRAFT,

    /**
     * Submitted for review.
     */
    IN_REVIEW,

    /**
     * Approved but not yet active.
     */
    APPROVED,

    /**
     * Currently active.
     */
    ACTIVE,

    /**
     * No longer active.
     */
    RETIRED,

    /**
     * Permanently archived.
     */
    ARCHIVED
}