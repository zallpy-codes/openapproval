package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval step is approved.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalStepApprovedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID approverId;
    private final String comment;

    public ApprovalStepApprovedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final String comment) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

}