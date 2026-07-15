package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalStepTimedOutEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalStepTimedOutEvent}.
 *
 * <p>
 * Handles post-processing after an approval step
 * has timed out.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Notify the workflow owner</li>
 *     <li>Notify the assigned approver</li>
 *     <li>Create audit records</li>
 *     <li>Publish integration events</li>
 *     <li>Update SLA and timeout analytics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalStepTimedOutListener {

    /**
     * Handles approval step timed out events.
     *
     * @param event approval step timed out event
     */
    @EventListener
    public void handle(
            final ApprovalStepTimedOutEvent event) {

        log.info(
                "Approval step timed out. WorkflowId={}, StepId={}, ApproverId={}, TimedOutAt={}",
                event.getWorkflowId(),
                event.getStepId(),
                event.getApproverId(),
                event.getTimedOutAt());

        /*
         * Future milestones:
         *
         * - Notify assigned approver
         * - Notify workflow owner
         * - Persist audit trail
         * - Publish webhook
         * - Update SLA metrics
         * - Trigger escalation workflow
         */
    }

}