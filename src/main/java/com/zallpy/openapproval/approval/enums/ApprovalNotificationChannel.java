package com.zallpy.openapproval.approval.enums;

/**
 * Supported notification delivery channels.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationChannel {

    /**
     * Electronic mail.
     */
    EMAIL,

    /**
     * Short Message Service.
     */
    SMS,

    /**
     * Mobile push notification.
     */
    PUSH_NOTIFICATION,

    /**
     * In-application notification.
     */
    IN_APP,

    /**
     * Microsoft Teams.
     */
    MICROSOFT_TEAMS,

    /**
     * Slack.
     */
    SLACK,

    /**
     * WhatsApp.
     */
    WHATSAPP,

    /**
     * Webhook callback.
     */
    WEBHOOK
}