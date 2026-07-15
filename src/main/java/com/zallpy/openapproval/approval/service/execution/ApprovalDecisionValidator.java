package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

import java.util.UUID;

/**
 * Validates approval decisions before execution.
 *
 * <p>
 * This service centralizes all pre-execution validation rules,
 * allowing the execution service to focus on orchestration.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDecisionValidator {

    /**
     * Validates an approval decision.
     *
     * @param workflow   approval workflow
     * @param step       current approval step
     * @param approverId approving user
     */
    void validateApproval(
            ApprovalWorkflow workflow,
            ApprovalStep step,
            UUID approverId);

    /**
     * Validates a rejection decision.
     *
     * @param workflow   approval workflow
     * @param step       current approval step
     * @param approverId rejecting user
     */
    void validateRejection(
            ApprovalWorkflow workflow,
            ApprovalStep step,
            UUID approverId);

}