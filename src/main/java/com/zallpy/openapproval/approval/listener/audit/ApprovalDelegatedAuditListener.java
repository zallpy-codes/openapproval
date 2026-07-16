package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalStepDelegatedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalStepDelegatedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval step is delegated from one approver
 * to another.
 * </p>
 *
 * <p>
 * The listener executes only after the enclosing
 * transaction has successfully committed,
 * ensuring the audit history reflects committed
 * business operations.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Listen for {@link ApprovalStepDelegatedEvent}</li>
 * <li>Delegate audit persistence to the audit service</li>
 * <li>Maintain an immutable audit trail</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalDelegatedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval delegation events.
     *
     * <p>
     * Records the delegation only after the
     * surrounding transaction has committed
     * successfully.
     * </p>
     *
     * @param event approval step delegated event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalDelegated(
            final ApprovalStepDelegatedEvent event) {

        approvalAuditService.record(event);
    }

}