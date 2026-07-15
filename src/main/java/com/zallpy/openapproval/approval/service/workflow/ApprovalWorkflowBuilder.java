package com.zallpy.openapproval.approval.service.workflow;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

/**
 * Builds executable approval workflows from approval policies.
 *
 * <p>
 * This service transforms an approval policy into a runtime workflow,
 * including the creation of all approval steps.
 * </p>
 *
 * <p>
 * The approval engine delegates workflow construction to this service,
 * keeping orchestration separate from object creation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalWorkflowBuilder {

    /**
     * Builds an executable workflow.
     *
     * @param submissionRequest submission request
     * @param approvalPolicy    approval policy
     * @param approvalRequest   approval request
     * @return approval workflow
     */
    ApprovalWorkflow buildWorkflow(
            SubmitApprovalRequest submissionRequest,
            ApprovalPolicy approvalPolicy,
            ApprovalRequest approvalRequest);

}