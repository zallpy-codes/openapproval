package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Determines whether reminder processing
 * is allowed for an approval step.
 *
 * <p>
 * Reminder policies may consider:
 * <ul>
 *     <li>Due date</li>
 *     <li>Reminder interval</li>
 *     <li>Maximum reminder count</li>
 *     <li>Approval status</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalReminderPolicyResolver {

    /**
     * Determines whether a reminder should be sent.
     *
     * @param approvalStep approval step
     * @return {@code true} if reminder should be sent
     */
    boolean shouldSendReminder(
            ApprovalStep approvalStep);

}