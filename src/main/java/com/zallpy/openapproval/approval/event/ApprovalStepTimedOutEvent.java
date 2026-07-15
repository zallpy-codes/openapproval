package com.zallpy.openapproval.approval.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event published when an approval step
 * times out.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalStepTimedOutEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID approverId;
    private final LocalDateTime timedOutAt;

    public ApprovalStepTimedOutEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final LocalDateTime timedOutAt) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.timedOutAt = timedOutAt;
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

    public LocalDateTime getTimedOutAt() {
        return timedOutAt;
    }

}