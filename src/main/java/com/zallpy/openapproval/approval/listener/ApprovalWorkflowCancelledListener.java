package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalWorkflowCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalWorkflowCancelledEvent}.
 *
 * <p>
 * Handles post-processing after an approval workflow
 * has been cancelled.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notify the requester</li>
 *     <li>Notify active approvers</li>
 *     <li>Create audit records</li>
 *     <li>Publish integration events</li>
 *     <li>Update workflow analytics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalWorkflowCancelledListener {

    /**
     * Handles workflow cancelled events.
     *
     * @param event workflow cancelled event
     */
    @EventListener
    public void handle(
            final ApprovalWorkflowCancelledEvent event) {

        log.info(
                "Approval workflow cancelled. WorkflowId={}, RequestId={}, CancelledBy={}, Reason={}",
                event.getWorkflowId(),
                event.getRequestId(),
                event.getCancelledBy(),
                event.getReason());

        /*
         * Future milestones:
         *
         * - Notify requester
         * - Notify pending approvers
         * - Persist audit trail
         * - Publish webhook/event
         * - Update analytics
         */
    }

}