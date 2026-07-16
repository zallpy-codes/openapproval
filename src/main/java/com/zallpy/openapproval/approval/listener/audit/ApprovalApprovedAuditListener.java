package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalApprovedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalApprovedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever an
 * approval request or approval step is approved.
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
public class ApprovalApprovedAuditListener {

    /**
     * Approval audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval approved events.
     *
     * @param event approval approved event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalApproved(
            final ApprovalApprovedEvent event) {

        approvalAuditService.record(event);
    }

}