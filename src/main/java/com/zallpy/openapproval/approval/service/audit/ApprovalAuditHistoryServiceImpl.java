package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.entity.ApprovalAuditRepository;
import com.zallpy.openapproval.approval.entity.ApprovalAuditSpecification;
import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation of {@link ApprovalAuditHistoryService}.
 *
 * <p>
 * Provides read-only access to approval audit history.
 * </p>
 *
 * <p>
 * Query construction is delegated to
 * {@link ApprovalAuditSpecification}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalAuditHistoryServiceImpl
                implements ApprovalAuditHistoryService {

        /**
         * Audit repository.
         */
        private final ApprovalAuditRepository approvalAuditRepository;

        /**
         * Default audit ordering.
         */
        private static final Sort AUDIT_SORT = Sort.by(
                        Sort.Direction.ASC,
                        "occurredAt");

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findWorkflowHistory(
                        final UUID workflowId) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.workflowId(
                                                workflowId),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findRequestHistory(
                        final UUID requestId) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.requestId(
                                                requestId),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findApproverHistory(
                        final UUID approverId) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.performedBy(
                                                approverId),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findByAction(
                        final ApprovalAuditAction action) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.action(action),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findStepHistory(
                        final UUID stepId) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.stepId(stepId),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findPolicyHistory(
                        final UUID policyId) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.policyId(policyId),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findByActorType(
                        final ApprovalAuditActorType actorType) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.actorType(actorType),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findByEventType(
                        final String eventType) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.eventType(eventType),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> findBetween(
                        final LocalDateTime from,
                        final LocalDateTime to) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.occurredBetween(
                                                from,
                                                to),
                                AUDIT_SORT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<ApprovalAudit> search(
                        final String keyword) {

                return approvalAuditRepository.findAll(
                                ApprovalAuditSpecification.search(keyword),
                                AUDIT_SORT);
        }

}