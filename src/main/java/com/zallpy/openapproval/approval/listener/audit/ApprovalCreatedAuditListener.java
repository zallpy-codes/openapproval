package com.zallpy.openapproval.approval.listener.audit;

import com.zallpy.openapproval.approval.event.ApprovalCreatedEvent;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Audit listener for {@link ApprovalCreatedEvent}.
 *
 * <p>
 * Records an immutable audit entry whenever a new
 * approval request is created.
 * </p>
 *
 * <p>
 * The listener executes only after the surrounding
 * transaction has successfully committed.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalCreatedAuditListener {

    /**
     * Audit service.
     */
    private final ApprovalAuditService approvalAuditService;

    /**
     * Handles approval creation events.
     *
     * @param event approval created event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApprovalCreated(
            final ApprovalCreatedEvent event) {

        approvalAuditService.record(event);
    }

}