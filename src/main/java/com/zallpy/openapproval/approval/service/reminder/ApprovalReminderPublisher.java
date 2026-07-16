package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Publishes reminder events after successful
 * reminder processing.
 *
 * <p>
 * Implementations typically publish
 * {@code ApprovalReminderSentEvent}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalReminderPublisher {

    /**
     * Publishes a reminder event.
     *
     * @param approvalStep approval step
     */
    void publishReminderSent(
            ApprovalStep approvalStep);

}