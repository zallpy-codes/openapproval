package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Domain event published when an approval step is delegated
 * to another approver.
 *
 * <p>
 * This event is emitted after a successful delegation and can
 * be consumed by notification, audit, analytics, and integration
 * components.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalStepDelegatedEvent
        extends AbstractApprovalDomainEvent {

    /**
     * Workflow identifier.
     */
    private final UUID workflowId;

    /**
     * Approval step identifier.
     */
    private final UUID stepId;

    /**
     * User who performed the delegation.
     */
    private final UUID delegatedBy;

    /**
     * User receiving the delegated approval.
     */
    private final UUID delegatedTo;

    /**
     * Delegation reason.
     */
    private final String reason;

    /**
     * Creates a new delegation event.
     *
     * @param workflowId workflow identifier
     * @param stepId approval step identifier
     * @param delegatedBy delegating user
     * @param delegatedTo delegate user
     * @param reason delegation reason
     */
    public ApprovalStepDelegatedEvent(
            final UUID workflowId,
            final UUID stepId,
            final UUID delegatedBy,
            final UUID delegatedTo,
            final String reason) {

        this.workflowId = workflowId;
        this.stepId = stepId;
        this.delegatedBy = delegatedBy;
        this.delegatedTo = delegatedTo;
        this.reason = reason;
    }

    /**
     * Returns the workflow identifier.
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
     * Returns the user that delegated the step.
     *
     * @return delegating user
     */
    public UUID getDelegatedBy() {
        return delegatedBy;
    }

    /**
     * Returns the delegate user.
     *
     * @return delegated user
     */
    public UUID getDelegatedTo() {
        return delegatedTo;
    }

    /**
     * Returns the delegation reason.
     *
     * @return delegation reason
     */
    public String getReason() {
        return reason;
    }

}