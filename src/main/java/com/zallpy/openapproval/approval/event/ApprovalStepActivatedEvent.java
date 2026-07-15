package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Published when an approval step becomes the active step.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalStepActivatedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID approverId;

    public ApprovalStepActivatedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
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

}