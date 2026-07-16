package com.zallpy.openapproval.approval.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for {@link ApprovalAudit}.
 *
 * <p>
 * Provides persistence operations for immutable
 * approval audit history.
 * </p>
 *
 * <p>
 * Complex querying is delegated to
 * {@link ApprovalAuditSpecification}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalAuditRepository
        extends JpaRepository<ApprovalAudit, UUID>,
                JpaSpecificationExecutor<ApprovalAudit> {

}