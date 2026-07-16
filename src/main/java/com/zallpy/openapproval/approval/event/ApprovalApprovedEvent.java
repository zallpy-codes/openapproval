package com.zallpy.openapproval.approval.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event published when an approval step
 * or approval request has been approved.
 *
 * <p>
 * This event is emitted after an approver
 * successfully approves a pending approval step.
 * </p>
 *
 * <p>
 * Consumers include:
 * <ul>
 * <li>Audit Engine</li>
 * <li>Notification Engine</li>
 * <li>Workflow Progression Engine</li>
 * <li>Analytics & Reporting</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalApprovedEvent
        extends AbstractApprovalDomainEvent {

    /**
     * Approval workflow identifier.
     */
    private final UUID workflowId;

    /**
     * Approval step identifier.
     */
    private final UUID stepId;

    /**
     * User that approved the step.
     */
    private final UUID approverId;

    /**
     * Time the approval occurred.
     */
    private final LocalDateTime approvedAt;

    /**
     * Creates a new approval approved event.
     *
     * @param workflowId approval workflow identifier
     * @param stepId     approval step identifier
     * @param approverId approving user
     * @param approvedAt approval timestamp
     */
    public ApprovalApprovedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final LocalDateTime approvedAt) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.approvedAt = approvedAt;
    }

    /**
     * Returns the approval workflow identifier.
     *
     * @return workflow identifier
     */
    public UUID getWorkflowId() {
        return workflowId;
    }

    /**
     * Returns the approval step identifier.
     *
     * @return approval step identifier
     */
    public UUID getStepId() {
        return stepId;
    }

    /**
     * Returns the approving user identifier.
     *
     * @return approver identifier
     */
    public UUID getApproverId() {
        return approverId;
    }

    /**
     * Returns the approval timestamp.
     *
     * @return approval timestamp
     */
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

}