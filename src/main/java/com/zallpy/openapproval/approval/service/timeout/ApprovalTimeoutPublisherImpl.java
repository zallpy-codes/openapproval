package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.event.ApprovalStepTimedOutEvent;
import com.zallpy.openapproval.approval.service.event.ApprovalDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalTimeoutPublisher}.
 *
 * <p>
 * Publishes timeout events after a successful timeout.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalTimeoutPublisherImpl
        implements ApprovalTimeoutPublisher {

    /**
     * Domain event publisher.
     */
    private final ApprovalDomainEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishTimeout(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        eventPublisher.publish(

                new ApprovalStepTimedOutEvent(
                        approvalStep.getWorkflowId(),
                        approvalStep.getId(),
                        approvalStep.getApproverId(),
                        approvalStep.getTimedOutAt()));
    }

}