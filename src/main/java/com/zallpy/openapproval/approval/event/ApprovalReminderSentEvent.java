package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Domain event published after a reminder
 * notification has been sent.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalReminderSentEvent
        extends AbstractApprovalDomainEvent {

    private final UUID workflowId;
    private final UUID stepId;
    private final UUID approverId;
    private final Integer reminderCount;

    public ApprovalReminderSentEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final Integer reminderCount) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.reminderCount = reminderCount;
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

    public Integer getReminderCount() {
        return reminderCount;
    }

}