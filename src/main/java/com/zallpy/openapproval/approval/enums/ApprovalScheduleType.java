package com.zallpy.openapproval.approval.enums;

/**
 * Defines the scheduling strategy used for policy lifecycle events.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalScheduleType {

    /**
     * Execute only once at the scheduled date and time.
     */
    ONE_TIME,

    /**
     * Execute repeatedly using a fixed interval.
     */
    RECURRING,

    /**
     * Execute using a CRON expression.
     */
    CRON
}