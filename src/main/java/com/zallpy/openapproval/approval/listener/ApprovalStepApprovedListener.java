package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalStepApprovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalStepApprovedEvent}.
 *
 * <p>
 * Handles post-processing after an approval step
 * has been approved.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notify the workflow requester</li>
 *     <li>Notify workflow administrators</li>
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
public class ApprovalStepApprovedListener {

    /**
     * Handles approval step approved events.
     *
     * @param event approval step approved event
     */
    @EventListener
    public void handle(
            final ApprovalStepApprovedEvent event) {

        log.info(
                "Approval step approved. WorkflowId={}, StepId={}, ApproverId={}, Comment={}",
                event.getWorkflowId(),
                event.getStepId(),
                event.getApproverId(),
                event.getComment());

        /*
         * Future milestones:
         *
         * - Notify requester
         * - Notify next approver
         * - Persist audit trail
         * - Publish webhook
         * - Update analytics
         * - Trigger workflow progression if required
         */
    }

}