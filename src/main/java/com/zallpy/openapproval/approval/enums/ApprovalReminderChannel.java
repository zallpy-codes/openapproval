package com.zallpy.openapproval.approval.enums;

/**
 * Represents the communication channel used to send
 * an approval reminder.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalReminderChannel {

    /**
     * Email notification.
     */
    EMAIL,

    /**
     * SMS notification.
     */
    SMS,

    /**
     * Push notification.
     */
    PUSH,

    /**
     * In-application notification.
     */
    IN_APP,

    /**
     * Microsoft Teams notification.
     */
    MICROSOFT_TEAMS,

    /**
     * Slack notification.
     */
    SLACK,

    /**
     * Webhook notification.
     */
    WEBHOOK
}