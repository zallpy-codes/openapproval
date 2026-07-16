package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

import java.util.UUID;

/**
 * Resolves the escalation recipient for an approval step.
 *
 * <p>
 * Implementations may determine the escalation target from
 * organizational hierarchy, reporting lines, approval policy,
 * or custom business rules.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEscalationResolver {

    /**
     * Resolves the escalation recipient.
     *
     * @param approvalStep approval step
     * @return escalation recipient
     */
    UUID resolveEscalatedApprover(
            ApprovalStep approvalStep);

}