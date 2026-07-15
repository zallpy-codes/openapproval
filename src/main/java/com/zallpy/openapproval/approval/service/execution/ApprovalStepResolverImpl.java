package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zallpy.openapproval.approval.event.ApprovalDomainEventPublisher;
import com.zallpy.openapproval.approval.event.ApprovalStepActivatedEvent;
import lombok.RequiredArgsConstructor;

/**
 * Default implementation of {@link ApprovalStepResolver}.
 *
 * <p>
 * Resolves the currently executable approval step.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalStepResolverImpl
        implements ApprovalStepResolver {

    private final ApprovalDomainEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStep resolveCurrentStep(
            final ApprovalWorkflow workflow) {

        if (workflow == null) {
            throw new ApprovalValidationException(
                    "Approval workflow is required.");
        }

        ApprovalStep currentStep = workflow.getCurrentApprovalStep();

        if (currentStep == null) {
            throw new ApprovalValidationException(
                    "No active approval step exists.");
        }

        if (!currentStep.isExecutable()) {
            throw new ApprovalValidationException(
                    "Current approval step cannot be executed.");
        }

        eventPublisher.publish(
                new ApprovalStepActivatedEvent(
                        workflow.getId(),
                        currentStep.getId(),
                        currentStep.getApproverId()));

        return currentStep;
    }

}