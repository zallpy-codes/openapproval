package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

/**
 * Records approval audit entries.
 *
 * <p>
 * Implementations transform approval domain events into
 * immutable audit history records.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalAuditService {

    /**
     * Records an audit entry for the supplied event.
     *
     * @param event approval domain event
     */
    void record(
            ApprovalDomainEvent event);

}