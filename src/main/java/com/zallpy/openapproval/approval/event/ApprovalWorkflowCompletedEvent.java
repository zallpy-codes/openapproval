package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when a workflow completes successfully.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalWorkflowCompletedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID requestId;
    private final UUID completedBy;

    public ApprovalWorkflowCompletedEvent(
            final UUID workflowId,
            final UUID requestId,
            final UUID completedBy) {

        this.workflowId = workflowId;
        this.requestId = requestId;
        this.completedBy = completedBy;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public UUID getCompletedBy() {
        return completedBy;
    }

}