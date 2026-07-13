package com.zallpy.openapproval.approval.enums;

/**
 * Approval notification events.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationType {

    REQUEST_CREATED,

    REQUEST_SUBMITTED,

    REQUEST_APPROVED,

    REQUEST_REJECTED,

    REQUEST_RETURNED,

    REQUEST_CANCELLED,

    STAGE_STARTED,

    STAGE_COMPLETED,

    REMINDER,

    ESCALATION,

    DELEGATION,

    COMPLETED
}