package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval workflow is cancelled.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalWorkflowCancelledEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID requestId;
    private final UUID cancelledBy;
    private final String reason;

    public ApprovalWorkflowCancelledEvent(
            final UUID workflowId,
            final UUID requestId,
            final UUID cancelledBy,
            final String reason) {

        this.workflowId = workflowId;
        this.requestId = requestId;
        this.cancelledBy = cancelledBy;
        this.reason = reason;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public UUID getCancelledBy() {
        return cancelledBy;
    }

    public String getReason() {
        return reason;
    }

}