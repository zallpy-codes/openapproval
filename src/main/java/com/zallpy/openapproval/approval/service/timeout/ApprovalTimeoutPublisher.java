package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;

/**
 * Publishes timeout events.
 *
 * <p>
 * Implementations publish
 * {@code ApprovalStepTimedOutEvent}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimeoutPublisher {

    /**
     * Publishes a timeout event.
     *
     * @param approvalStep approval step
     */
    void publishTimeout(
            ApprovalStep approvalStep);

}