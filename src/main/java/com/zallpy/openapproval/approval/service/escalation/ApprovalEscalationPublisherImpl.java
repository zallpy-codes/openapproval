package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.event.ApprovalStepEscalatedEvent;
import com.zallpy.openapproval.approval.service.event.ApprovalDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalEscalationPublisher}.
 *
 * <p>
 * Publishes escalation events after a successful escalation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalEscalationPublisherImpl
        implements ApprovalEscalationPublisher {

    /**
     * Domain event publisher.
     */
    private final ApprovalDomainEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishEscalation(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        eventPublisher.publish(

                new ApprovalStepEscalatedEvent(
                        approvalStep.getWorkflowId(),
                        approvalStep.getId(),
                        approvalStep.getEscalatedTo()));
    }

}