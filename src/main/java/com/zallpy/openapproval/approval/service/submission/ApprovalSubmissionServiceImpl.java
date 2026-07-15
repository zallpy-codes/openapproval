package com.zallpy.openapproval.approval.service.submission;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.dto.response.ApprovalSubmissionResponse;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalRequestRepository;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.service.policy.ApprovalPolicyResolver;
import com.zallpy.openapproval.approval.service.request.ApprovalRequestFactory;
import com.zallpy.openapproval.approval.service.validation.ApprovalValidationService;
import com.zallpy.openapproval.approval.service.workflow.ApprovalWorkflowBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zallpy.openapproval.approval.event.ApprovalDomainEventPublisher;
import com.zallpy.openapproval.approval.event.ApprovalSubmittedEvent;

/**
 * Default implementation of {@link ApprovalSubmissionService}.
 *
 * <p>
 * Coordinates the submission of approval requests.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalSubmissionServiceImpl
                implements ApprovalSubmissionService {

        private final ApprovalValidationService validationService;

        private final ApprovalPolicyResolver policyResolverService;

        private final ApprovalDomainEventPublisher eventPublisher;

        /**
         * Will be introduced in Batch 2.
         */
        private final ApprovalRequestFactory approvalRequestFactory;

        private final ApprovalWorkflowBuilder workflowBuilder;

        private final ApprovalRequestRepository approvalRequestRepository;

        @Override
        public ApprovalSubmissionResponse submit(
                        final SubmitApprovalRequest request) {

                /*
                 * Validate incoming request.
                 */
                validationService.validateSubmission(request);

                /*
                 * Resolve approval policy.
                 */
                ApprovalPolicy approvalPolicy = policyResolverService.resolve(request.getPolicyCode());

                /*
                 * Create approval request.
                 */
                ApprovalRequest approvalRequest = approvalRequestFactory.create(
                                request,
                                approvalPolicy);

                /*
                 * Build workflow.
                 */
                ApprovalWorkflow workflow = workflowBuilder.buildWorkflow(
                                request,
                                approvalPolicy,
                                approvalRequest);

                /*
                 * Link aggregate.
                 */
                approvalRequest.setWorkflow(workflow);

                /*
                 * Persist aggregate root.
                 */
                approvalRequest = approvalRequestRepository.save(approvalRequest);

                eventPublisher.publish(
                                new ApprovalSubmittedEvent(
                                                approvalRequest.getId(),
                                                workflow.getId(),
                                                approvalRequest.getSubmittedBy()));

                /*
                 * Response creation continues in Part 2.
                 */
                return buildSubmissionResponse(approvalRequest);
        }

        /**
         * Builds the submission response.
         *
         * @param approvalRequest persisted approval request
         * @return submission response
         */
        private ApprovalSubmissionResponse buildSubmissionResponse(
                        final ApprovalRequest approvalRequest) {

                ApprovalSubmissionResponse response = new ApprovalSubmissionResponse();

                response.setRequestId(
                                approvalRequest.getId());

                if (approvalRequest.getWorkflow() != null) {
                        response.setWorkflowId(
                                        approvalRequest.getWorkflow().getId());
                }

                response.setRequestReference(
                                approvalRequest.getRequestReference());

                response.setStatus(
                                approvalRequest.getStatus());

                response.setSubmittedAt(
                                approvalRequest.getSubmittedAt());

                response.setMessage(
                                "Approval request submitted successfully.");

                return response;
        }

}