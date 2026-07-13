package com.zallpy.openapproval.approval.policy;

/**
 * Represents the execution priority of an approval policy.
 *
 * <p>
 * When multiple policies satisfy the same matching criteria,
 * the policy with the highest priority is evaluated first.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyPriority {

    /**
     * Lowest priority.
     */
    LOW,

    /**
     * Normal/default priority.
     */
    NORMAL,

    /**
     * High priority.
     */
    HIGH,

    /**
     * Highest priority.
     */
    CRITICAL

}