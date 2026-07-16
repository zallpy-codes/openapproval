package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Publishes escalation events.
 *
 * <p>
 * Implementations publish
 * {@code ApprovalStepEscalatedEvent}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEscalationPublisher {

    /**
     * Publishes an escalation event.
     *
     * @param approvalStep approval step
     */
    void publishEscalation(
            ApprovalStep approvalStep);

}