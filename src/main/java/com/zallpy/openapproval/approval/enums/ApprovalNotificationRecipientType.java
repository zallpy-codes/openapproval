package com.zallpy.openapproval.approval.enums;

/**
 * Identifies who should receive a notification.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationRecipientType {

    /**
     * User who initiated the request.
     */
    REQUESTER,

    /**
     * Current approver.
     */
    CURRENT_APPROVER,

    /**
     * Next approver.
     */
    NEXT_APPROVER,

    /**
     * Previous approver.
     */
    PREVIOUS_APPROVER,

    /**
     * Requester's manager.
     */
    MANAGER,

    /**
     * Specific user.
     */
    USER,

    /**
     * Security role.
     */
    ROLE,

    /**
     * User group.
     */
    GROUP,

    /**
     * Department.
     */
    DEPARTMENT,

    /**
     * Business unit.
     */
    BUSINESS_UNIT,

    /**
     * Branch.
     */
    BRANCH,

    /**
     * System administrator.
     */
    ADMINISTRATOR,

    /**
     * Custom recipient resolved at runtime.
     */
    CUSTOM
}