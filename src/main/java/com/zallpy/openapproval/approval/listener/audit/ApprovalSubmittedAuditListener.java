package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalSubmittedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalSubmittedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval request is submitted for processing.
 * </p>
 *
 * <p>
 * The listener executes only after the enclosing
 * transaction has successfully committed.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalSubmittedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval submitted events.
     *
     * @param event approval submitted event
     */
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalSubmitted(
            final ApprovalSubmittedEvent event) {

        approvalAuditService.record(event);
    }

}