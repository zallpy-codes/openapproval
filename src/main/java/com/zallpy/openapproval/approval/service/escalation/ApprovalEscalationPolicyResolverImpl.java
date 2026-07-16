package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalEscalationPolicyResolver}.
 *
 * <p>
 * Determines whether an approval step is eligible
 * for escalation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalEscalationPolicyResolverImpl
        implements ApprovalEscalationPolicyResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldEscalate(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return false;
        }

        return approvalStep.canEscalate()
                && approvalStep.isOverdue();
    }

}