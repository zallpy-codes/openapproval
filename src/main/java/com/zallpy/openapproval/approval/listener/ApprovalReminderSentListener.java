package com.zallpy.openapproval.approval.listener;

import com.zallpy.openapproval.approval.event.ApprovalReminderSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link ApprovalReminderSentEvent}.
 *
 * <p>
 * Handles post-processing after an approval reminder
 * has been sent.
 * </p>
 *
 * <p>
 * Future responsibilities include:
 * <ul>
 *     <li>Persist reminder audit records</li>
 *     <li>Update reminder statistics</li>
 *     <li>Track reminder history</li>
 *     <li>Feed SLA analytics</li>
 *     <li>Trigger escalation threshold evaluation</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Component
public class ApprovalReminderSentListener {

    /**
     * Handles approval reminder sent events.
     *
     * @param event reminder sent event
     */
    @EventListener
    public void handle(
            final ApprovalReminderSentEvent event) {

        log.info(
                "Approval reminder sent. WorkflowId={}, StepId={}, ApproverId={}, ReminderCount={}",
                event.getWorkflowId(),
                event.getStepId(),
                event.getApproverId(),
                event.getReminderCount());

        /*
         * Future milestones:
         *
         * - Persist reminder audit
         * - Update reminder statistics
         * - Publish webhook
         * - Feed analytics engine
         * - Evaluate escalation thresholds
         */
    }

}