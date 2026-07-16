package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalEscalationResolver}.
 *
 * <p>
 * Resolves the escalation recipient for an approval step.
 * </p>
 *
 * <p>
 * This default implementation returns the escalation recipient
 * already assigned to the approval step. Future implementations
 * may resolve managers, supervisors or approval hierarchy
 * dynamically.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalEscalationResolverImpl
        implements ApprovalEscalationResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID resolveEscalatedApprover(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            throw new ApprovalValidationException(
                    "Approval step is required.");
        }

        UUID escalatedTo = approvalStep.getEscalatedTo();

        if (escalatedTo == null) {
            throw new ApprovalValidationException(
                    "No escalation recipient configured.");
        }

        return escalatedTo;
    }

}