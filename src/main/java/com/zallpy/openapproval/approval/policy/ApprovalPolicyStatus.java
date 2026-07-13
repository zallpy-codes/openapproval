package com.zallpy.openapproval.approval.policy;

/**
 * Represents the lifecycle status of an approval policy.
 *
 * <p>
 * The status determines whether a policy can participate in approval
 * processing and what operations are permitted.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyStatus {

    /**
     * Policy is being designed and is not available for use.
     */
    DRAFT,

    /**
     * Policy is active and can be used to start approval processes.
     */
    ACTIVE,

    /**
     * Policy is temporarily disabled and cannot be used.
     */
    INACTIVE,

    /**
     * Policy has been retired and is kept for historical purposes.
     */
    ARCHIVED

}