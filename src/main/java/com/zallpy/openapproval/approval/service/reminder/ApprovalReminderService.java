package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Coordinates reminder processing for pending approval steps.
 *
 * <p>
 * Implementations determine whether a reminder should be sent,
 * update reminder statistics and delegate notification publishing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalReminderService {

    /**
     * Processes a reminder for the supplied approval step.
     *
     * @param approvalStep approval step
     */
    void sendReminder(
            ApprovalStep approvalStep);

}