package com.zallpy.openapproval.approval.enums;

/**
 * Defines where an approval should be escalated.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalEscalationType {

    /**
     * Escalate to a specific user.
     */
    USER,

    /**
     * Escalate to a security role.
     */
    ROLE,

    /**
     * Escalate to a user group.
     */
    GROUP,

    /**
     * Escalate to requester's manager.
     */
    MANAGER,

    /**
     * Escalate to next-level manager.
     */
    NEXT_LEVEL_MANAGER,

    /**
     * Escalate to department.
     */
    DEPARTMENT,

    /**
     * Escalate to business unit.
     */
    BUSINESS_UNIT,

    /**
     * Escalate to branch.
     */
    BRANCH,

    /**
     * Escalate using a dynamic resolver.
     */
    DYNAMIC,

    /**
     * Escalate using a custom implementation.
     */
    CUSTOM
}