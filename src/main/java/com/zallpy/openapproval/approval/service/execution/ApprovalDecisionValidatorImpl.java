package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalDecisionValidator}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalDecisionValidatorImpl
        implements ApprovalDecisionValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateApproval(
            final ApprovalWorkflow workflow,
            final ApprovalStep step,
            final UUID approverId) {

        validateWorkflow(workflow);
        validateStep(step);
        validateApprover(step, approverId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateRejection(
            final ApprovalWorkflow workflow,
            final ApprovalStep step,
            final UUID approverId) {

        validateWorkflow(workflow);
        validateStep(step);
        validateApprover(step, approverId);
    }

    /**
     * Validates the workflow state.
     *
     * @param workflow approval workflow
     */
    private void validateWorkflow(
            final ApprovalWorkflow workflow) {

        if (workflow == null) {
            throw new ApprovalValidationException(
                    "Approval workflow is required.");
        }

        if (workflow.isCompleted()) {
            throw new ApprovalValidationException(
                    "Approval workflow has already completed.");
        }

        if (workflow.isCancelled()) {
            throw new ApprovalValidationException(
                    "Approval workflow has been cancelled.");
        }

        if (workflow.isRejected()) {
            throw new ApprovalValidationException(
                    "Approval workflow has already been rejected.");
        }

        if (!workflow.isExecutable()) {
            throw new ApprovalValidationException(
                    "Approval workflow cannot be executed.");
        }
    }

    /**
     * Validates the approval step.
     *
     * @param step approval step
     */
    private void validateStep(
            final ApprovalStep step) {

        if (step == null) {
            throw new ApprovalValidationException(
                    "Approval step is required.");
        }

        if (!step.isExecutable()) {
            throw new ApprovalValidationException(
                    "Approval step cannot be executed.");
        }
    }

    /**
     * Validates the assigned approver.
     *
     * @param step       approval step
     * @param approverId approving user
     */
    private void validateApprover(
            final ApprovalStep step,
            final UUID approverId) {

        if (approverId == null) {
            throw new ApprovalValidationException(
                    "Approver is required.");
        }

        if (!approverId.equals(step.getApproverId())) {
            throw new ApprovalValidationException(
                    "The approval step is not assigned to the supplied approver.");
        }
    }

}