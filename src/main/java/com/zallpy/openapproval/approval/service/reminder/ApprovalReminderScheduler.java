package com.zallpy.openapproval.approval.service.reminder;

/**
 * Schedules reminder processing for pending approval steps.
 *
 * <p>
 * Implementations periodically scan pending approval steps
 * and delegate reminder processing to the
 * {@link ApprovalReminderService}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalReminderScheduler {

    /**
     * Executes reminder processing.
     */
    void processReminders();

}