package com.zallpy.openapproval.approval.service.request;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Default implementation of {@link ApprovalRequestFactory}.
 *
 * <p>
 * Responsible for constructing ApprovalRequest aggregates.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
public class ApprovalRequestFactoryImpl
        implements ApprovalRequestFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalRequest create(
            final SubmitApprovalRequest request,
            final ApprovalPolicy approvalPolicy) {

        ApprovalRequest approvalRequest = new ApprovalRequest();

        approvalRequest.setRequestReference(request.getRequestReference());
        approvalRequest.setResourceType(request.getResourceType());
        approvalRequest.setResourceId(request.getResourceId());

        approvalRequest.setTitle(request.getTitle());
        approvalRequest.setDescription(request.getDescription());

        approvalRequest.setSubmittedBy(request.getSubmittedBy());
        approvalRequest.setSubmittedAt(LocalDateTime.now());

        approvalRequest.setApprovalPolicy(approvalPolicy);

        approvalRequest.setBusinessKey(request.getBusinessKey());
        approvalRequest.setCorrelationId(request.getCorrelationId());
        approvalRequest.setPriority(request.getPriority());
        approvalRequest.setDueDate(request.getDueDate());
        approvalRequest.setAttributes(request.getAttributes());

        approvalRequest.setStatus(ApprovalStatus.PENDING);

        /*
         * Initialize runtime state.
         */
        approvalRequest.setCompleted(false);
        approvalRequest.setCancelled(false);
        approvalRequest.setRecalled(false);

        approvalRequest.setCompletedAt(null);
        approvalRequest.setCompletedBy(null);

        approvalRequest.setCancelledAt(null);
        approvalRequest.setCancelledBy(null);
        approvalRequest.setCancellationReason(null);

        approvalRequest.setRecalledAt(null);
        approvalRequest.setRecalledBy(null);
        approvalRequest.setRecallReason(null);

        return approvalRequest;
    }

}