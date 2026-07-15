package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflowRepository;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zallpy.openapproval.approval.event.ApprovalDomainEventPublisher;
import com.zallpy.openapproval.approval.event.ApprovalStepApprovedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepRejectedEvent;
import com.zallpy.openapproval.approval.event.ApprovalWorkflowRejectedEvent;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalExecutionService}.
 *
 * <p>
 * Coordinates execution of approval decisions.
 * </p>
 *
 * <p>
 * Business rules remain inside the domain model while this service
 * orchestrates workflow execution.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalExecutionServiceImpl
                implements ApprovalExecutionService {

        private final ApprovalDomainEventPublisher eventPublisher;

        /**
         * Workflow repository.
         */
        private final ApprovalWorkflowRepository approvalWorkflowRepository;

        /**
         * Resolves executable approval steps.
         */
        private final ApprovalStepResolver approvalStepResolver;

        /**
         * Advances workflow execution.
         */
        private final ApprovalWorkflowProgressor workflowProgressor;

        private final ApprovalDecisionValidator decisionValidator;

        /**
         * {@inheritDoc}
         */
        @Override
        public void approve(
                        final UUID workflowId,
                        final UUID approverId,
                        final String comment) {

                ApprovalWorkflow workflow = loadWorkflow(workflowId);

                ApprovalStep currentStep = approvalStepResolver.resolveCurrentStep(workflow);

                decisionValidator.validateApproval(
                                workflow,
                                currentStep,
                                approverId);

                /*
                 * Execute approval.
                 */
                currentStep.approve(
                                approverId,
                                comment);

                /*
                 * Publish domain event.
                 */
                eventPublisher.publish(
                                new ApprovalStepApprovedEvent(
                                                workflow.getId(),
                                                currentStep.getId(),
                                                approverId,
                                                comment));

                /*
                 * Progress workflow using configured strategy.
                 */
                workflowProgressor.progress(
                                workflow,
                                currentStep,
                                approverId);

                approvalWorkflowRepository.save(workflow);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reject(
                        final UUID workflowId,
                        final UUID approverId,
                        final String comment) {

                ApprovalWorkflow workflow = loadWorkflow(workflowId);

                ApprovalStep currentStep = approvalStepResolver.resolveCurrentStep(workflow);

                decisionValidator.validateRejection(
                                workflow,
                                currentStep,
                                approverId);

                /*
                 * Execute rejection.
                 */
                currentStep.reject(
                                approverId,
                                comment);

                /*
                 * Reject workflow.
                 */
                workflow.reject(
                                approverId,
                                comment);

                /*
                 * Publish domain events.
                 */
                eventPublisher.publish(
                                new ApprovalStepRejectedEvent(
                                                workflow.getId(),
                                                currentStep.getId(),
                                                approverId,
                                                comment));

                eventPublisher.publish(
                                new ApprovalWorkflowRejectedEvent(
                                                workflow.getId(),
                                                workflow.getApprovalRequest().getId(),
                                                approverId,
                                                comment));

                approvalWorkflowRepository.save(workflow);
        }

        /**
         * Loads a workflow.
         *
         * @param workflowId workflow identifier
         * @return approval workflow
         */
        private ApprovalWorkflow loadWorkflow(
                        final UUID workflowId) {

                return approvalWorkflowRepository
                                .findById(workflowId)
                                .orElseThrow(() -> new ApprovalValidationException(
                                                "Approval workflow was not found."));
        }

}