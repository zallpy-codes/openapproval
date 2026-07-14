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
 * Repository for {@link ApprovalStep}.
 *
 * <p>
 * Provides persistence operations for approval workflow steps.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalStepRepository
        extends JpaRepository<ApprovalStep, UUID>,
        JpaSpecificationExecutor<ApprovalStep> {

    /**
     * Finds all steps belonging to a workflow ordered by stage order.
     *
     * @param approvalWorkflow approval workflow
     * @return approval steps
     */
    List<ApprovalStep> findByApprovalWorkflowOrderByStageOrderAsc(
            ApprovalWorkflow approvalWorkflow);

    /**
     * Finds the current active step in a workflow.
     *
     * @param approvalWorkflow approval workflow
     * @return current approval step
     */
    Optional<ApprovalStep> findByApprovalWorkflowAndCurrentStepTrue(
            ApprovalWorkflow approvalWorkflow);

    /**
     * Finds all pending approval steps assigned to an approver.
     *
     * @param approverId approver identifier
     * @param status approval status
     * @return pending approval steps
     */
    List<ApprovalStep> findByApproverIdAndStatus(
            UUID approverId,
            ApprovalStatus status);

    /**
     * Finds all current approval steps assigned to an approver.
     *
     * @param approverId approver identifier
     * @return current approval steps
     */
    List<ApprovalStep> findByApproverIdAndCurrentStepTrue(
            UUID approverId);

    /**
     * Finds all approval steps with the specified status.
     *
     * @param status approval status
     * @return approval steps
     */
    List<ApprovalStep> findByStatus(
            ApprovalStatus status);

    /**
     * Finds all overdue approval steps.
     *
     * @param dueAt due date
     * @return overdue approval steps
     */
    List<ApprovalStep> findByDueAtBeforeAndCompletedFalse(
            LocalDateTime dueAt);

    /**
     * Finds all delegated approval steps.
     *
     * @return delegated approval steps
     */
    List<ApprovalStep> findByDelegatedTrue();

    /**
     * Finds all escalated approval steps.
     *
     * @return escalated approval steps
     */
    List<ApprovalStep> findByEscalatedTrue();

    /**
     * Finds all timed-out approval steps.
     *
     * @return timed-out approval steps
     */
    List<ApprovalStep> findByTimedOutTrue();

    /**
     * Finds all steps awaiting reminder notifications.
     *
     * @param reminderCount current reminder count
     * @param dueAt due date threshold
     * @return approval steps
     */
    List<ApprovalStep> findByReminderCountLessThanAndCompletedFalseAndDueAtBefore(
            Integer reminderCount,
            LocalDateTime dueAt);

    /**
     * Counts the number of approval steps assigned to an approver.
     *
     * @param approverId approver identifier
     * @param status approval status
     * @return number of approval steps
     */
    long countByApproverIdAndStatus(
            UUID approverId,
            ApprovalStatus status);

}