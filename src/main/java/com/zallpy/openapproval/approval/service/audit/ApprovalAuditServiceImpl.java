package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.entity.ApprovalAuditRepository;
import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalAuditService}.
 *
 * <p>
 * Records immutable audit history for approval
 * domain events.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Validate incoming events</li>
 * <li>Map domain events to audit entities</li>
 * <li>Persist audit history</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalAuditServiceImpl
        implements ApprovalAuditService {

    /**
     * Audit repository.
     */
    private final ApprovalAuditRepository approvalAuditRepository;

    /**
     * Audit mapper.
     */
    private final ApprovalAuditMapper approvalAuditMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void record(
            final ApprovalDomainEvent event) {

        if (event == null) {
            return;
        }

        ApprovalAudit audit = approvalAuditMapper.toEntity(event);

        if (audit == null) {
            return;
        }

        approvalAuditRepository.save(audit);
    }

}