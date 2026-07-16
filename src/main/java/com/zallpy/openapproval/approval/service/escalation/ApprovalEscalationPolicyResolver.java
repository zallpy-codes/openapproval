package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Determines whether an approval step
 * should be escalated.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEscalationPolicyResolver {

    /**
     * Determines whether escalation should occur.
     *
     * @param approvalStep approval step
     * @return {@code true} if escalation should occur
     */
    boolean shouldEscalate(
            ApprovalStep approvalStep);

}