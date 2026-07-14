package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalWorkflow}.
 *
 * <p>
 * Provides persistence operations for approval workflows.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalWorkflowRepository
        extends JpaRepository<ApprovalWorkflow, UUID>,
        JpaSpecificationExecutor<ApprovalWorkflow> {

    /**
     * Finds the workflow for an approval request.
     *
     * @param approvalRequest approval request
     * @return approval workflow
     */
    Optional<ApprovalWorkflow> findByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Finds workflows by status.
     *
     * @param status approval status
     * @return approval workflows
     */
    List<ApprovalWorkflow> findByStatus(
            ApprovalStatus status);

    /**
     * Finds all active workflows.
     *
     * @return active workflows
     */
    List<ApprovalWorkflow> findByCompletedFalseAndCancelledFalseAndRecalledFalse();

    /**
     * Finds workflows awaiting approval.
     *
     * @return workflows awaiting approval
     */
    List<ApprovalWorkflow> findByAwaitingApprovalTrue();

    /**
     * Finds workflows assigned to the specified approver.
     *
     * @param currentApproverId approver identifier
     * @return approval workflows
     */
    List<ApprovalWorkflow> findByCurrentApproverId(
            UUID currentApproverId);

    /**
     * Finds overdue workflows.
     *
     * @param dueAt due date
     * @return overdue workflows
     */
    List<ApprovalWorkflow> findByDueAtBeforeAndCompletedFalse(
            LocalDateTime dueAt);

    /**
     * Finds timed out workflows.
     *
     * @return timed out workflows
     */
    List<ApprovalWorkflow> findByTimedOutTrue();

    /**
     * Finds workflows started after the specified date.
     *
     * @param startedAt start date
     * @return approval workflows
     */
    List<ApprovalWorkflow> findByStartedAtAfter(
            LocalDateTime startedAt);

    /**
     * Finds workflows started between two dates.
     *
     * @param start start date
     * @param end end date
     * @return approval workflows
     */
    List<ApprovalWorkflow> findByStartedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

}