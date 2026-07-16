package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

/**
 * Maps approval domain events into
 * {@link ApprovalAudit} entities.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalAuditMapper {

    /**
     * Maps a domain event into an audit entity.
     *
     * @param event approval domain event
     * @return audit entity
     */
    ApprovalAudit toEntity(
            ApprovalDomainEvent event);

}