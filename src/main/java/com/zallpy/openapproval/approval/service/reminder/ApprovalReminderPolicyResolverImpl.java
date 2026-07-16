package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalReminderPolicyResolver}.
 *
 * <p>
 * Determines whether a reminder should be sent for an approval step.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalReminderPolicyResolverImpl
        implements ApprovalReminderPolicyResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldSendReminder(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return false;
        }

        return approvalStep.isPending()
                && !approvalStep.isCompleted()
                && !approvalStep.isTimedOut()
                && approvalStep.hasDueDate()
                && !approvalStep.isOverdue();
    }

}