package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Coordinates timeout processing for approval steps.
 *
 * <p>
 * Implementations determine whether an approval step
 * should be timed out and execute the timeout workflow.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimeoutService {

    /**
     * Processes timeout for the supplied approval step.
     *
     * @param approvalStep approval step
     */
    void timeout(
            ApprovalStep approvalStep);

}