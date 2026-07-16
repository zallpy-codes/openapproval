package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalEscalationService}.
 *
 * <p>
 * Coordinates approval step escalation.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Validate escalation policy</li>
 * <li>Resolve escalation recipient</li>
 * <li>Escalate the approval step</li>
 * <li>Publish the escalation event</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalEscalationServiceImpl
        implements ApprovalEscalationService {

    /**
     * Escalation policy resolver.
     */
    private final ApprovalEscalationPolicyResolver policyResolver;

    /**
     * Escalation recipient resolver.
     */
    private final ApprovalEscalationResolver escalationResolver;

    /**
     * Escalation event publisher.
     */
    private final ApprovalEscalationPublisher escalationPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void escalate(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        if (!policyResolver.shouldEscalate(
                approvalStep)) {
            return;
        }

        UUID escalatedTo = escalationResolver
                .resolveEscalatedApprover(
                        approvalStep);

        approvalStep.escalate(
                escalatedTo);

        escalationPublisher.publishEscalation(
                approvalStep);
    }

}