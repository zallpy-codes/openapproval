package com.zallpy.openapproval.approval.service.delegation;

import java.util.UUID;

/**
 * Resolves and validates approval delegates.
 *
 * <p>
 * Implementations are responsible for ensuring that a delegate
 * exists and is eligible to receive delegated approval work.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDelegateResolver {

    /**
     * Resolves a delegate.
     *
     * @param delegateId delegate user identifier
     * @return resolved delegate identifier
     */
    UUID resolve(UUID delegateId);

}