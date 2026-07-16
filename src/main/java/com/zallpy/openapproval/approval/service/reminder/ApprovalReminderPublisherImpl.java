package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.event.ApprovalReminderSentEvent;
import com.zallpy.openapproval.approval.service.event.ApprovalDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalReminderPublisher}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalReminderPublisherImpl
        implements ApprovalReminderPublisher {

    private final ApprovalDomainEventPublisher
            eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishReminderSent(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        eventPublisher.publish(

                new ApprovalReminderSentEvent(
                        approvalStep.getWorkflowId(),
                        approvalStep.getId(),
                        approvalStep.getApproverId(),
                        approvalStep.getReminderCount()));
    }

}