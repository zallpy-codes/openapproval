package com.zallpy.openapproval.approval.enums;

/**
 * Identifies who performed an audited action.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalAuditActorType {

    /**
     * Human user.
     */
    USER,

    /**
     * Workflow engine.
     */
    WORKFLOW_ENGINE,

    /**
     * Scheduled background process.
     */
    SCHEDULER,

    /**
     * Internal system process.
     */
    SYSTEM,

    /**
     * REST or external API.
     */
    API,

    /**
     * Administrator.
     */
    ADMINISTRATOR
}