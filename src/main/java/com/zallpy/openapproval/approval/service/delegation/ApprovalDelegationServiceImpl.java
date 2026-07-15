package com.zallpy.openapproval.approval.service.delegation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflowRepository;
import com.zallpy.openapproval.approval.event.ApprovalStepDelegatedEvent;
import com.zallpy.openapproval.approval.service.event.ApprovalDomainEventPublisher;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalDelegationService}.
 *
 * <p>
 * Coordinates approval step delegation.
 * </p>
 *
 * <p>
 * Business rules remain inside the domain model.
 * This service is responsible for orchestration only.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalDelegationServiceImpl
                implements ApprovalDelegationService {

        /**
         * Workflow repository.
         */
        private final ApprovalWorkflowRepository approvalWorkflowRepository;

        /**
         * Resolves delegation targets.
         */
        private final ApprovalDelegateResolver delegateResolver;

        /**
         * Domain event publisher.
         */
        private final ApprovalDomainEventPublisher eventPublisher;

        /**
         * {@inheritDoc}
         */
        @Override
        public void delegate(
                        final UUID workflowId,
                        final UUID stepId,
                        final UUID delegatedBy,
                        final UUID delegatedTo,
                        final String reason) {

                ApprovalWorkflow workflow = approvalWorkflowRepository
                                .findById(workflowId)
                                .orElseThrow(() -> new ApprovalValidationException(
                                                "Approval workflow was not found."));

                ApprovalStep step = workflow.getApprovalSteps()
                                .stream()
                                .filter(s -> s.getId().equals(stepId))
                                .findFirst()
                                .orElseThrow(() -> new ApprovalValidationException(
                                                "Approval step was not found."));

                /*
                 * Validate delegation target.
                 */
                delegateResolver.resolve(
                                delegatedTo);

                /*
                 * Delegate the approval step.
                 *
                 * Business rules are implemented by the domain entity.
                 */
                step.delegate(
                                delegatedBy,
                                delegatedTo);

                /*
                 * Publish domain event.
                 */
                eventPublisher.publish(
                                new ApprovalStepDelegatedEvent(
                                                workflow.getId(),
                                                step.getId(),
                                                delegatedBy,
                                                delegatedTo,
                                                reason));

                /*
                 * Persist aggregate.
                 */
                approvalWorkflowRepository.save(workflow);
        }

}