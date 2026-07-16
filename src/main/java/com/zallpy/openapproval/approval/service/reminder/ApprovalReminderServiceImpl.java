package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalReminderService}.
 *
 * <p>
 * Coordinates reminder processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalReminderServiceImpl
        implements ApprovalReminderService {

    private final ApprovalReminderPolicyResolver
            policyResolver;

    private final ApprovalReminderPublisher
            reminderPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendReminder(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        if (!policyResolver.shouldSendReminder(
                approvalStep)) {
            return;
        }

        approvalStep.incrementReminderCount();

        reminderPublisher.publishReminderSent(
                approvalStep);
    }

}