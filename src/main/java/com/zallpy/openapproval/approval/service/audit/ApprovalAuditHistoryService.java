package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Provides audit history queries.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalAuditHistoryService {

        /**
         * Returns workflow audit history.
         *
         * @param workflowId workflow identifier
         * @return audit history
         */
        List<ApprovalAudit> findWorkflowHistory(
                        UUID workflowId);

        /**
         * Returns approval request audit history.
         *
         * @param requestId approval request identifier
         * @return audit history
         */
        List<ApprovalAudit> findRequestHistory(
                        UUID requestId);

        /**
         * Returns approver audit history.
         *
         * @param approverId approver identifier
         * @return audit history
         */
        List<ApprovalAudit> findApproverHistory(
                        UUID approverId);

        /**
         * Returns the complete audit history for an approval step.
         *
         * <p>
         * The returned history contains every audit entry
         * associated with the supplied approval step,
         * ordered chronologically.
         * </p>
         *
         * @param stepId approval step identifier
         * @return approval step audit history
         */
        List<ApprovalAudit> findStepHistory(
                        UUID stepId);

        /**
         * Returns the complete audit history for an approval policy.
         *
         * <p>
         * This method is primarily intended for
         * administrative review and reporting.
         * </p>
         *
         * @param policyId approval policy identifier
         * @return approval policy audit history
         */
        List<ApprovalAudit> findPolicyHistory(
                        UUID policyId);

        /**
         * Returns every audit record matching the
         * supplied audit action.
         *
         * <p>
         * Examples include:
         * <ul>
         * <li>Approved</li>
         * <li>Rejected</li>
         * <li>Delegated</li>
         * <li>Escalated</li>
         * </ul>
         * </p>
         *
         * @param action audit action
         * @return matching audit records
         */
        List<ApprovalAudit> findByAction(
                        ApprovalAuditAction action);

        /**
         * Returns every audit record created by
         * the supplied actor type.
         *
         * <p>
         * This allows separation of actions
         * performed by users, administrators,
         * schedulers and workflow automation.
         * </p>
         *
         * @param actorType audit actor type
         * @return matching audit records
         */
        List<ApprovalAudit> findByActorType(
                        ApprovalAuditActorType actorType);

        /**
         * Returns every audit record matching the
         * supplied event type.
         *
         * <p>
         * Event types correspond to approval
         * domain events published by the system.
         * </p>
         *
         * @param eventType domain event type
         * @return matching audit records
         */
        List<ApprovalAudit> findByEventType(
                        String eventType);

        /**
         * Returns all audit records that occurred
         * between the supplied dates.
         *
         * <p>
         * Either boundary may be {@code null},
         * allowing open-ended searches.
         * </p>
         *
         * @param from start date
         * @param to   end date
         * @return matching audit records
         */
        List<ApprovalAudit> findBetween(
                        LocalDateTime from,
                        LocalDateTime to);

        /**
         * Performs a keyword search across
         * approval audit history.
         *
         * <p>
         * The search implementation performs
         * a case-insensitive lookup across
         * commonly queried audit fields.
         * </p>
         *
         * @param keyword search keyword
         * @return matching audit records
         */
        List<ApprovalAudit> search(
                        String keyword);

}