package com.zallpy.openapproval.approval.enums;

/**
 * Represents the delivery status of an approval reminder.
 *
 * <p>
 * This status tracks the lifecycle of reminders sent by the
 * approval workflow engine.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalReminderStatus {

    /**
     * Reminder has been scheduled.
     */
    SCHEDULED,

    /**
     * Reminder is queued for delivery.
     */
    QUEUED,

    /**
     * Reminder is currently being sent.
     */
    SENDING,

    /**
     * Reminder was successfully delivered.
     */
    SENT,

    /**
     * Reminder delivery failed.
     */
    FAILED,

    /**
     * Reminder was cancelled.
     */
    CANCELLED,

    /**
     * Reminder delivery timed out.
     */
    EXPIRED
}