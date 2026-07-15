package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalStepRejectedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalStepRejectedEvent}.
 *
 * <p>
 * Handles post-processing after an approval step
 * has been rejected.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notify the requester</li>
 *     <li>Notify workflow owner</li>
 *     <li>Create audit records</li>
 *     <li>Publish integration events</li>
 *     <li>Update reporting and analytics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalStepRejectedListener {

    /**
     * Handles approval step rejected events.
     *
     * @param event approval step rejected event
     */
    @EventListener
    public void handle(
            final ApprovalStepRejectedEvent event) {

        log.info(
                "Approval step rejected. WorkflowId={}, StepId={}, ApproverId={}, Reason={}",
                event.getWorkflowId(),
                event.getStepId(),
                event.getApproverId(),
                event.getReason());

        /*
         * Future milestones:
         *
         * - Notify requester
         * - Notify workflow owner
         * - Persist audit trail
         * - Publish webhook
         * - Update analytics
         * - Trigger workflow rejection processing
         */
    }

}