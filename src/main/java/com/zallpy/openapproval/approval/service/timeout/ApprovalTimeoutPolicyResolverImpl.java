package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalTimeoutPolicyResolver}.
 *
 * <p>
 * Determines whether an approval step is eligible
 * for timeout processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalTimeoutPolicyResolverImpl
        implements ApprovalTimeoutPolicyResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shouldTimeout(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return false;
        }

        return approvalStep.canTimeout();
    }

}