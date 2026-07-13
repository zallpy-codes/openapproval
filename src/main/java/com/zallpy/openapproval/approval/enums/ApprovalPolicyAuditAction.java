package com.zallpy.openapproval.approval.enums;

/**
 * Defines audit actions performed on approval policy configuration.
 *
 * <p>
 * These audit actions capture administrative changes made to approval
 * policy definitions and related configuration objects.
 *
 * <p>
 * This enumeration is intended for configuration auditing only.
 * Runtime approval actions are audited separately by the approval
 * execution engine.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPolicyAuditAction {

    /**
     * Policy created.
     */
    CREATE_POLICY,

    /**
     * Policy updated.
     */
    UPDATE_POLICY,

    /**
     * Policy deleted.
     */
    DELETE_POLICY,

    /**
     * Stage created.
     */
    CREATE_STAGE,

    /**
     * Stage updated.
     */
    UPDATE_STAGE,

    /**
     * Stage deleted.
     */
    DELETE_STAGE,

    /**
     * Approver added.
     */
    ADD_APPROVER,

    /**
     * Approver removed.
     */
    REMOVE_APPROVER,

    /**
     * Approval condition created.
     */
    CREATE_CONDITION,

    /**
     * Approval condition updated.
     */
    UPDATE_CONDITION,

    /**
     * Approval condition deleted.
     */
    DELETE_CONDITION,

    /**
     * Notification created.
     */
    CREATE_NOTIFICATION,

    /**
     * Notification updated.
     */
    UPDATE_NOTIFICATION,

    /**
     * Notification deleted.
     */
    DELETE_NOTIFICATION,

    /**
     * Reminder created.
     */
    CREATE_REMINDER,

    /**
     * Reminder updated.
     */
    UPDATE_REMINDER,

    /**
     * Reminder deleted.
     */
    DELETE_REMINDER,

    /**
     * Escalation created.
     */
    CREATE_ESCALATION,

    /**
     * Escalation updated.
     */
    UPDATE_ESCALATION,

    /**
     * Escalation deleted.
     */
    DELETE_ESCALATION,

    /**
     * Policy version created.
     */
    CREATE_VERSION,

    /**
     * Policy version published.
     */
    PUBLISH_VERSION,

    /**
     * Policy version activated.
     */
    ACTIVATE_VERSION,

    /**
     * Policy version retired.
     */
    RETIRE_VERSION,

    /**
     * Policy version archived.
     */
    ARCHIVE_VERSION,

    /**
     * Schedule created.
     */
    CREATE_SCHEDULE,

    /**
     * Schedule updated.
     */
    UPDATE_SCHEDULE,

    /**
     * Schedule deleted.
     */
    DELETE_SCHEDULE,

    /**
     * Variable created.
     */
    CREATE_VARIABLE,

    /**
     * Variable updated.
     */
    UPDATE_VARIABLE,

    /**
     * Variable deleted.
     */
    DELETE_VARIABLE,

    /**
     * Policy imported.
     */
    IMPORT_POLICY,

    /**
     * Policy exported.
     */
    EXPORT_POLICY
}