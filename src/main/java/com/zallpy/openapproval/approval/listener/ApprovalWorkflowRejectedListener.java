package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalWorkflowRejectedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalWorkflowRejectedEvent}.
 *
 * <p>
 * Handles post-processing after an approval workflow
 * has been rejected.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notify the requester</li>
 *     <li>Notify workflow administrators</li>
 *     <li>Create audit records</li>
 *     <li>Publish webhooks</li>
 *     <li>Update reporting and analytics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalWorkflowRejectedListener {

    /**
     * Handles workflow rejected events.
     *
     * @param event workflow rejected event
     */
    @EventListener
    public void handle(
            final ApprovalWorkflowRejectedEvent event) {

        log.info(
                "Approval workflow rejected. WorkflowId={}, RequestId={}, RejectedBy={}, Reason={}",
                event.getWorkflowId(),
                event.getRequestId(),
                event.getRejectedBy(),
                event.getReason());

        /*
         * Future milestones:
         *
         * - Notify requester
         * - Notify workflow owner
         * - Persist audit trail
         * - Publish webhook
         * - Update analytics
         */
    }

}