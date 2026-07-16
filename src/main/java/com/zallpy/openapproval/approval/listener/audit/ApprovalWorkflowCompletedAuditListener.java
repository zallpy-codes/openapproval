package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalWorkflowCompletedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalWorkflowCompletedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval workflow reaches its terminal state.
 * </p>
 *
 * <p>
 * A completed workflow may represent a successful
 * approval, a rejection, a cancellation or any
 * other terminal outcome defined by the workflow
 * engine.
 * </p>
 *
 * <p>
 * The listener executes only after the enclosing
 * transaction has successfully committed,
 * ensuring that only committed workflow
 * completions are recorded in the audit trail.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Listen for {@link ApprovalWorkflowCompletedEvent}</li>
 * <li>Delegate audit persistence to the audit service</li>
 * <li>Maintain an immutable workflow completion history</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalWorkflowCompletedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles workflow completed events.
     *
     * <p>
     * The audit record is created only after the
     * surrounding transaction has been committed
     * successfully.
     * </p>
     *
     * @param event workflow completed event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalWorkflowCompleted(
            final ApprovalWorkflowCompletedEvent event) {

        approvalAuditService.record(event);
    }

}