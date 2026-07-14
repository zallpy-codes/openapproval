package com.zallpy.openapproval.approval.service.validation;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Default implementation of {@link ApprovalValidationService}.
 *
 * <p>
 * Performs business validation for approval engine operations.
 * This service is intentionally stateless and does not access the
 * persistence layer.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
public class ApprovalValidationServiceImpl implements ApprovalValidationService {

    @Override
    public void validateSubmission(final SubmitApprovalRequest request) {

        if (request == null) {
            throw new ApprovalValidationException("Approval submission request cannot be null.");
        }

        if (!StringUtils.hasText(request.getRequestReference())) {
            throw new ApprovalValidationException("Request reference is required.");
        }

        if (!StringUtils.hasText(request.getPolicyCode())) {
            throw new ApprovalValidationException("Policy code is required.");
        }

        if (!StringUtils.hasText(request.getResourceType())) {
            throw new ApprovalValidationException("Resource type is required.");
        }

        if (request.getResourceId() == null) {
            throw new ApprovalValidationException("Resource ID is required.");
        }

        if (!StringUtils.hasText(request.getTitle())) {
            throw new ApprovalValidationException("Approval title is required.");
        }

        if (request.getSubmittedBy() == null) {
            throw new ApprovalValidationException("Submitted-by user is required.");
        }
    }

    @Override
    public void validatePolicy(final ApprovalPolicy policy) {

        if (policy == null) {
            throw new ApprovalValidationException("Approval policy does not exist.");
        }

        if (!policy.isActive()) {
            throw new ApprovalValidationException(
                    "Approval policy [" + policy.getPolicyCode() + "] is inactive."
            );
        }

        if (policy.getStages() == null || policy.getStages().isEmpty()) {
            throw new ApprovalValidationException(
                    "Approval policy must contain at least one approval stage."
            );
        }
    }

    @Override
    public void validateRequest(final ApprovalRequest request) {

        if (request == null) {
            throw new ApprovalValidationException("Approval request does not exist.");
        }
    }

    @Override
    public void validateWorkflow(final ApprovalWorkflow workflow) {

        if (workflow == null) {
            throw new ApprovalValidationException("Approval workflow does not exist.");
        }

        if (workflow.isCompleted()) {
            throw new ApprovalValidationException(
                    "Approval workflow has already been completed."
            );
        }
    }

    @Override
    public void validateCancellation(final ApprovalWorkflow workflow) {

        validateWorkflow(workflow);

        if (workflow.isCancelled()) {
            throw new ApprovalValidationException(
                    "Approval workflow has already been cancelled."
            );
        }
    }

    @Override
    public void validateRecall(final ApprovalWorkflow workflow) {

        validateWorkflow(workflow);

        if (workflow.isCompleted()) {
            throw new ApprovalValidationException(
                    "Completed approval workflows cannot be recalled."
            );
        }
    }

}