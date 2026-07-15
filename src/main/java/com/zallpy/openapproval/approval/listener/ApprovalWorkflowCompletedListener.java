package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalWorkflowCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalWorkflowCompletedEvent}.
 *
 * <p>
 * Handles post-processing after an approval workflow
 * has completed successfully.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notification dispatch</li>
 *     <li>Audit logging</li>
 *     <li>Webhook publication</li>
 *     <li>Analytics updates</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalWorkflowCompletedListener {

    /**
     * Handles workflow completed events.
     *
     * @param event workflow completed event
     */
    @EventListener
    public void handle(
            final ApprovalWorkflowCompletedEvent event) {

        log.info(
                "Approval workflow completed. WorkflowId={}, RequestId={}, CompletedBy={}",
                event.getWorkflowId(),
                event.getRequestId(),
                event.getCompletedBy());

        /*
         * Future milestones:
         *
         * - Notify requester
         * - Notify subscribers
         * - Publish webhook
         * - Record audit trail
         * - Update analytics
         */
    }

}