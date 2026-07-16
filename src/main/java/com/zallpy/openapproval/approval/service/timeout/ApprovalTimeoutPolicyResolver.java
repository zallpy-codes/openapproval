package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Determines whether an approval step
 * should be timed out.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimeoutPolicyResolver {

    /**
     * Determines whether timeout should occur.
     *
     * @param approvalStep approval step
     * @return {@code true} if timeout should occur
     */
    boolean shouldTimeout(
            ApprovalStep approvalStep);

}