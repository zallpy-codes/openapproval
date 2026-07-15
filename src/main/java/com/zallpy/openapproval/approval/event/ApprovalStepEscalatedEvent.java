package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Domain event published when an approval step
 * has been escalated.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalStepEscalatedEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID escalatedTo;

    public ApprovalStepEscalatedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID escalatedTo) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.escalatedTo = escalatedTo;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public UUID getStepId() {
        return stepId;
    }

    public UUID getEscalatedTo() {
        return escalatedTo;
    }

}