package com.zallpy.openapproval.approval.enums;

/**
 * Defines how reminder notifications are repeated.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalReminderFrequency {

    /**
     * Send only one reminder.
     */
    ONCE,

    /**
     * Fixed interval.
     */
    FIXED_INTERVAL,

    /**
     * Hourly.
     */
    HOURLY,

    /**
     * Daily.
     */
    DAILY,

    /**
     * Weekly.
     */
    WEEKLY,

    /**
     * Monthly.
     */
    MONTHLY
}