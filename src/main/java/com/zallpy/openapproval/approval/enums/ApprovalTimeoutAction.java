package com.zallpy.openapproval.approval.enums;

/**
 * Action performed when an approval timeout occurs.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalTimeoutAction {

    /**
     * Escalate the approval.
     */
    ESCALATE,

    /**
     * Automatically approve.
     */
    AUTO_APPROVE,

    /**
     * Automatically reject.
     */
    AUTO_REJECT,

    /**
     * Cancel the approval.
     */
    CANCEL,

    /**
     * Send another reminder.
     */
    SEND_REMINDER,

    /**
     * Delegate the task.
     */
    DELEGATE,

    /**
     * Skip the current approver.
     */
    SKIP,

    /**
     * No action.
     */
    NONE
}