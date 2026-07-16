package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalRejectedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalRejectedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval request or approval step is rejected.
 * </p>
 *
 * <p>
 * The listener is invoked only after the enclosing
 * transaction has successfully committed.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Listen for {@link ApprovalRejectedEvent}</li>
 *     <li>Delegate audit recording to the audit service</li>
 *     <li>Ensure audit records represent committed transactions</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalRejectedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval rejected events.
     *
     * <p>
     * The audit record is persisted only after the
     * surrounding transaction commits successfully.
     * </p>
     *
     * @param event approval rejected event
     */
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalRejected(
            final ApprovalRejectedEvent event) {

        approvalAuditService.record(event);
    }

}