package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval step is rejected.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalStepRejectedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID approverId;
    private final String reason;

    public ApprovalStepRejectedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final String reason) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.reason = reason;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getStepId() {
        return stepId;
    }

    public UUID getApproverId() {
        return approverId;
    }

    public String getReason() {
        return reason;
    }

}