package com.zallpy.openapproval.approval.enums;

/**
 * Defines how approvers are assigned to a stage.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalAssignmentType {

    /**
     * Explicit users.
     */
    NAMED_USERS,

    /**
     * Security roles.
     */
    ROLE,

    /**
     * Departments.
     */
    DEPARTMENT,

    /**
     * Positions.
     */
    POSITION,

    /**
     * Business units.
     */
    BUSINESS_UNIT,

    /**
     * Branches.
     */
    BRANCH,

    /**
     * Reporting manager.
     */
    MANAGER,

    /**
     * Manager hierarchy.
     */
    MANAGER_HIERARCHY,

    /**
     * Dynamic resolver.
     */
    DYNAMIC,

    /**
     * External resolver.
     */
    EXTERNAL
}