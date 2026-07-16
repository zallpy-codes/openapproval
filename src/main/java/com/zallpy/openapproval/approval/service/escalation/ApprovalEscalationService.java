package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Coordinates escalation processing for approval steps.
 *
 * <p>
 * Implementations determine whether an approval step should
 * be escalated and execute the escalation workflow.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEscalationService {

    /**
     * Processes escalation for the supplied approval step.
     *
     * @param approvalStep approval step
     */
    void escalate(
            ApprovalStep approvalStep);

}