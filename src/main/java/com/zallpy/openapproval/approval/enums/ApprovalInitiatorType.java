package com.zallpy.openapproval.approval.enums;

/**
 * Defines who is allowed to initiate an approval request.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalInitiatorType {

    /**
     * Any authenticated user.
     */
    USER,

    /**
     * A business role.
     */
    ROLE,

    /**
     * A department.
     */
    DEPARTMENT,

    /**
     * A business unit.
     */
    BUSINESS_UNIT,

    /**
     * A branch.
     */
    BRANCH,

    /**
     * Automated system process.
     */
    SYSTEM,

    /**
     * External API integration.
     */
    API,

    /**
     * Scheduled background job.
     */
    SCHEDULER,

    /**
     * Workflow engine.
     */
    WORKFLOW,

    /**
     * Administrator only.
     */
    ADMIN
}