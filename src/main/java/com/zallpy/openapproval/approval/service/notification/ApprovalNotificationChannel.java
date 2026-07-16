package com.zallpy.openapproval.approval.service.notification;

/**
 * Supported notification delivery channels.
 *
 * <p>
 * These channels represent the available mechanisms for
 * delivering approval notifications.
 * </p>
 *
 * <p>
 * Multiple channels may be supported simultaneously in
 * future milestones.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalNotificationChannel {

    /**
     * Email notification.
     */
    EMAIL,

    /**
     * SMS notification.
     */
    SMS,

    /**
     * In-application notification.
     */
    IN_APP,

    /**
     * Webhook notification.
     */
    WEBHOOK,

    /**
     * Microsoft Teams notification.
     */
    MICROSOFT_TEAMS,

    /**
     * Slack notification.
     */
    SLACK

}