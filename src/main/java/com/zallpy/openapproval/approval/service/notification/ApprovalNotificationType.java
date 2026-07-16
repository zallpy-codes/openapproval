package com.zallpy.openapproval.approval.service.notification;

/**
 * Enumerates the business notification types supported
 * by the approval engine.
 *
 * <p>
 * Notification types are independent of delivery channels.
 * For example, an {@code APPROVAL_STEP_APPROVED} notification
 * may be delivered through Email, SMS, In-App notifications,
 * or Webhooks.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationType {

    /**
     * Approval request submitted.
     */
    REQUEST_SUBMITTED,

    /**
     * Approval workflow completed.
     */
    WORKFLOW_COMPLETED,

    /**
     * Approval workflow rejected.
     */
    WORKFLOW_REJECTED,

    /**
     * Approval workflow cancelled.
     */
    WORKFLOW_CANCELLED,

    /**
     * Approval step approved.
     */
    STEP_APPROVED,

    /**
     * Approval step rejected.
     */
    STEP_REJECTED,

    /**
     * Approval step delegated.
     */
    STEP_DELEGATED,

    /**
     * Approval step escalated.
     */
    STEP_ESCALATED,

    /**
     * Approval step timed out.
     */
    STEP_TIMED_OUT,

    /**
     * Approval reminder sent.
     */
    REMINDER_SENT

}