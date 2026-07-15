package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval request is submitted.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalSubmittedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID requestId;
    private final UUID workflowId;
    private final UUID submittedBy;

    public ApprovalSubmittedEvent(
            final UUID requestId,
            final UUID workflowId,
            final UUID submittedBy) {

        this.requestId = requestId;
        this.workflowId = workflowId;
        this.submittedBy = submittedBy;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getSubmittedBy() {
        return submittedBy;
    }

}