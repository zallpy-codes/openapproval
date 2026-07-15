package com.zallpy.openapproval.approval.service.delegation;

import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalDelegateResolver}.
 *
 * <p>
 * Performs basic delegate validation.
 * </p>
 *
 * <p>
 * Integration with user management, identity providers,
 * or external directories will be introduced in future
 * milestones.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalDelegateResolverImpl
        implements ApprovalDelegateResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID resolve(final UUID delegateId) {

        if (delegateId == null) {
            throw new ApprovalValidationException(
                    "Delegate user is required.");
        }

        /*
         * Future implementations will verify:
         *
         * - User exists
         * - User is active
         * - User belongs to the correct tenant
         * - User is eligible to approve
         * - User is not suspended
         * - User has not exceeded delegation limits
         */

        return delegateId;
    }

}