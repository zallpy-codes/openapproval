package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalStepEscalatedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalStepEscalatedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval step is automatically or manually
 * escalated to another approver.
 * </p>
 *
 * <p>
 * Escalations may occur due to:
 * <ul>
 * <li>Approval timeout</li>
 * <li>SLA breach</li>
 * <li>Workflow escalation rules</li>
 * <li>Administrative intervention</li>
 * </ul>
 * </p>
 *
 * <p>
 * The listener executes only after the enclosing
 * transaction has successfully committed,
 * guaranteeing that audit records reflect only
 * committed business operations.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalEscalatedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval step escalated events.
     *
     * <p>
     * Delegates audit recording to the
     * {@link ApprovalAuditService}.
     * </p>
     *
     * @param event approval step escalated event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalStepEscalated(
            final ApprovalStepEscalatedEvent event) {

        approvalAuditService.record(event);
    }

}