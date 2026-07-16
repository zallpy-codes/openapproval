package com.zallpy.openapproval.approval.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain event published when an approval step
 * or approval request has been rejected.
 *
 * <p>
 * This event is emitted immediately after an
 * approver rejects a pending approval step.
 * </p>
 *
 * <p>
 * Consumers include:
 * <ul>
 * <li>Audit Engine</li>
 * <li>Notification Engine</li>
 * <li>Workflow Engine</li>
 * <li>Reporting & Analytics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalRejectedEvent
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
     * User that rejected the approval.
     */
    private final UUID approverId;

    /**
     * Reason for rejection.
     */
    private final String rejectionReason;

    /**
     * Time the rejection occurred.
     */
    private final LocalDateTime rejectedAt;

    /**
     * Creates a new approval rejected event.
     *
     * @param workflowId      approval workflow identifier
     * @param stepId          approval step identifier
     * @param approverId      rejecting user
     * @param rejectionReason rejection reason
     * @param rejectedAt      rejection timestamp
     */
    public ApprovalRejectedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID approverId,
            final String rejectionReason,
            final LocalDateTime rejectedAt) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.approverId = approverId;
        this.rejectionReason = rejectionReason;
        this.rejectedAt = rejectedAt;
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
     * Returns the rejecting user identifier.
     *
     * @return approver identifier
     */
    public UUID getApproverId() {
        return approverId;
    }

    /**
     * Returns the rejection reason.
     *
     * @return rejection reason
     */
    public String getRejectionReason() {
        return rejectionReason;
    }

    /**
     * Returns the rejection timestamp.
     *
     * @return rejection timestamp
     */
    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

}