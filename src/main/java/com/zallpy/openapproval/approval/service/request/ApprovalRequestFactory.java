package com.zallpy.openapproval.approval.service.request;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;

/**
 * Factory responsible for creating ApprovalRequest aggregates.
 *
 * <p>
 * Converts an incoming submission request into a fully initialized
 * ApprovalRequest ready for workflow generation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalRequestFactory {

    /**
     * Creates a new approval request.
     *
     * @param request submission request
     * @param policy resolved approval policy
     * @return approval request
     */
    ApprovalRequest create(
            SubmitApprovalRequest request,
            ApprovalPolicy policy);

}