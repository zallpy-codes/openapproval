package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval workflow is rejected.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalWorkflowRejectedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID requestId;
    private final UUID rejectedBy;
    private final String reason;

    public ApprovalWorkflowRejectedEvent(
            final UUID workflowId,
            final UUID requestId,
            final UUID rejectedBy,
            final String reason) {

        this.workflowId = workflowId;
        this.requestId = requestId;
        this.rejectedBy = rejectedBy;
        this.reason = reason;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public UUID getRejectedBy() {
        return rejectedBy;
    }

    public String getReason() {
        return reason;
    }

}