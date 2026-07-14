package com.zallpy.openapproval.approval.service.validation;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

/**
 * Performs validation for approval engine operations.
 *
 * <p>
 * This service centralizes validation logic used by the approval engine,
 * ensuring that requests, policies, and workflows satisfy all business
 * rules before execution.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalValidationService {

    /**
     * Validates a new approval submission request.
     *
     * @param request approval submission request
     */
    void validateSubmission(SubmitApprovalRequest request);

    /**
     * Validates the approval policy before workflow creation.
     *
     * @param policy approval policy
     */
    void validatePolicy(ApprovalPolicy policy);

    /**
     * Validates an approval request.
     *
     * @param request approval request
     */
    void validateRequest(ApprovalRequest request);

    /**
     * Validates a workflow before execution.
     *
     * @param workflow approval workflow
     */
    void validateWorkflow(ApprovalWorkflow workflow);

    /**
     * Validates whether the workflow can be cancelled.
     *
     * @param workflow approval workflow
     */
    void validateCancellation(ApprovalWorkflow workflow);

    /**
     * Validates whether the workflow can be recalled.
     *
     * @param workflow approval workflow
     */
    void validateRecall(ApprovalWorkflow workflow);

}