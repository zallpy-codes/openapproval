package com.zallpy.openapproval.approval.event;

import java.util.UUID;

/**
 * Domain event published when a new approval
 * request is created.
 *
 * <p>
 * This event is emitted immediately after the
 * approval request has been successfully created.
 * </p>
 *
 * <p>
 * Consumers include:
 * <ul>
 * <li>Audit Engine</li>
 * <li>Notification Engine</li>
 * <li>Metrics & Analytics</li>
 * <li>Workflow Monitoring</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalCreatedEvent
        extends AbstractApprovalDomainEvent {

    /**
     * Approval workflow identifier.
     */
    private final UUID workflowId;

    /**
     * Approval request identifier.
     */
    private final UUID requestId;

    /**
     * Creates a new approval created event.
     *
     * @param workflowId approval workflow identifier
     * @param requestId  approval request identifier
     */
    public ApprovalCreatedEvent(
            final UUID workflowId,
            final UUID requestId) {

        this.workflowId = workflowId;
        this.requestId = requestId;
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
     * Returns the approval request identifier.
     *
     * @return request identifier
     */
    public UUID getRequestId() {
        return requestId;
    }

}