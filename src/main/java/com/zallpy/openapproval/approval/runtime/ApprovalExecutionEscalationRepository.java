package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalEscalationStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import com.zallpy.openapproval.approval.policy.ApprovalEscalationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionEscalation}.
 *
 * <p>
 * Provides persistence operations for runtime approval
 * escalations including execution, stage, task, policy,
 * approver and status-based queries.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionEscalationRepository extends
        JpaRepository<ApprovalExecutionEscalation, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionEscalation> {

    /**
     * Finds an escalation by UUID.
     *
     * @param escalationUuid escalation UUID
     * @return matching escalation
     */
    Optional<ApprovalExecutionEscalation> findByEscalationUuid(
            UUID escalationUuid);

    /**
     * Finds an escalation by business reference.
     *
     * @param escalationReference escalation reference
     * @return matching escalation
     */
    Optional<ApprovalExecutionEscalation> findByEscalationReference(
            String escalationReference);

    /**
     * Returns all escalations belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns all escalations belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns all escalations belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Returns all escalations created from the specified policy.
     *
     * @param approvalEscalationPolicy escalation policy
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalEscalationPolicy(
            ApprovalEscalationPolicy approvalEscalationPolicy);

    /**
     * Returns all escalations having the specified status.
     *
     * @param status escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByStatus(
            ApprovalEscalationStatus status);

    /**
     * Returns all active escalations.
     *
     * @return active escalations
     */
    List<ApprovalExecutionEscalation> findByActiveTrue();

    /**
     * Returns all inactive escalations.
     *
     * @return inactive escalations
     */
    List<ApprovalExecutionEscalation> findByActiveFalse();

    /**
     * Returns all escalations ordered by scheduled date descending.
     *
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findAllByOrderByScheduledAtDesc();

    /**
     * Returns all escalations originating from the specified approver.
     *
     * @param fromApproverId source approver identifier
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByFromApproverId(
            String fromApproverId);

    /**
     * Returns all escalations assigned to the specified approver.
     *
     * @param toApproverId target approver identifier
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByToApproverId(
            String toApproverId);

    /**
     * Returns all escalations assigned to the specified approver email.
     *
     * @param toApproverEmail approver email
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByToApproverEmail(
            String toApproverEmail);

    /**
     * Returns all escalations scheduled after the specified date.
     *
     * @param scheduledAt scheduled date
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByScheduledAtAfter(
            LocalDateTime scheduledAt);

    /**
     * Returns all escalations scheduled before the specified date.
     *
     * @param scheduledAt scheduled date
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByScheduledAtBefore(
            LocalDateTime scheduledAt);

    /**
     * Returns all escalations executed after the specified date.
     *
     * @param escalatedAt escalation date
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByEscalatedAtAfter(
            LocalDateTime escalatedAt);

    /**
     * Returns all escalations executed before the specified date.
     *
     * @param escalatedAt escalation date
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByEscalatedAtBefore(
            LocalDateTime escalatedAt);

    /**
     * Returns all escalations ordered by scheduled date descending.
     *
     * @param approvalExecution approval execution
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionOrderByScheduledAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Returns all escalations ordered by execution date descending.
     *
     * @param approvalExecution approval execution
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionOrderByEscalatedAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Returns all escalations assigned to the specified approver
     * having the given status.
     *
     * @param toApproverId target approver
     * @param status       escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByToApproverIdAndStatus(
            String toApproverId,
            ApprovalEscalationStatus status);

    /**
     * Counts all escalations belonging to an execution.
     *
     * @param approvalExecution approval execution
     * @return escalation count
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts all escalations belonging to a stage.
     *
     * @param approvalExecutionStage execution stage
     * @return escalation count
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts all escalations belonging to a task.
     *
     * @param approvalExecutionTask execution task
     * @return escalation count
     */
    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Counts all escalations having the specified status.
     *
     * @param status escalation status
     * @return escalation count
     */
    long countByStatus(
            ApprovalEscalationStatus status);

    /**
     * Counts all escalations created from the specified policy.
     *
     * @param approvalEscalationPolicy escalation policy
     * @return escalation count
     */
    long countByApprovalEscalationPolicy(
            ApprovalEscalationPolicy approvalEscalationPolicy);

    /**
     * Returns whether an escalation reference already exists.
     *
     * @param escalationReference escalation reference
     * @return true if it exists
     */
    boolean existsByEscalationReference(
            String escalationReference);

    /**
     * Returns whether an escalation UUID already exists.
     *
     * @param escalationUuid escalation UUID
     * @return true if it exists
     */
    boolean existsByEscalationUuid(
            UUID escalationUuid);

    /**
     * Returns the latest escalation for an execution.
     *
     * @param approvalExecution approval execution
     * @return latest escalation
     */
    Optional<ApprovalExecutionEscalation> findFirstByApprovalExecutionOrderByScheduledAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Returns the oldest escalation for an execution.
     *
     * @param approvalExecution approval execution
     * @return oldest escalation
     */
    Optional<ApprovalExecutionEscalation> findFirstByApprovalExecutionOrderByScheduledAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Returns the latest completed escalation.
     *
     * @return latest completed escalation
     */
    Optional<ApprovalExecutionEscalation> findFirstByStatusOrderByEscalatedAtDesc(
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations ordered by escalation date descending.
     *
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findAllByOrderByEscalatedAtDesc();

    /**
     * Returns all escalations ordered by scheduled date ascending.
     *
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findAllByOrderByScheduledAtAsc();

    /**
     * Returns all escalations for an execution having the specified status.
     *
     * @param approvalExecution approval execution
     * @param status            escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations for a stage having the specified status.
     *
     * @param approvalExecutionStage execution stage
     * @param status                 escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionStageAndStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations for a task having the specified status.
     *
     * @param approvalExecutionTask execution task
     * @param status                escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionTaskAndStatus(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations generated from the specified policy
     * having the given status.
     *
     * @param approvalEscalationPolicy escalation policy
     * @param status                   escalation status
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalEscalationPolicyAndStatus(
            ApprovalEscalationPolicy approvalEscalationPolicy,
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations assigned to the specified target approver
     * ordered by scheduled date descending.
     *
     * @param toApproverId target approver
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByToApproverIdOrderByScheduledAtDesc(
            String toApproverId);

    /**
     * Returns all escalations originating from the specified approver
     * ordered by scheduled date descending.
     *
     * @param fromApproverId source approver
     * @return matching escalations
     */
    List<ApprovalExecutionEscalation> findByFromApproverIdOrderByScheduledAtDesc(
            String fromApproverId);

    /**
     * Returns all completed escalations.
     *
     * @return completed escalations
     */
    List<ApprovalExecutionEscalation> findByStatusOrderByEscalatedAtDesc(
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations that have not yet been executed.
     *
     * @return pending escalations
     */
    List<ApprovalExecutionEscalation> findByEscalatedAtIsNull();

    /**
     * Returns all successfully executed escalations.
     *
     * @return executed escalations
     */
    List<ApprovalExecutionEscalation> findByEscalatedAtIsNotNull();

    /**
     * Returns all escalations that contain failure information.
     *
     * @return failed escalations
     */
    List<ApprovalExecutionEscalation> findByFailureReasonIsNotNull();

    /**
     * Returns all escalations that have an escalation reason.
     *
     * @return escalations with reasons
     */
    List<ApprovalExecutionEscalation> findByEscalationReasonIsNotNull();

    /**
     * Returns all escalations that contain runtime metadata.
     *
     * @return escalations with metadata
     */
    List<ApprovalExecutionEscalation> findByMetadataIsNotNull();

    /**
     * Returns whether the specified execution has any escalations.
     *
     * @param approvalExecution approval execution
     * @return true if escalations exist
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether the specified stage has any escalations.
     *
     * @param approvalExecutionStage execution stage
     * @return true if escalations exist
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns whether the specified task has any escalations.
     *
     * @param approvalExecutionTask execution task
     * @return true if escalations exist
     */
    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Returns whether the specified policy has generated
     * runtime escalations.
     *
     * @param approvalEscalationPolicy escalation policy
     * @return true if runtime escalations exist
     */
    boolean existsByApprovalEscalationPolicy(
            ApprovalEscalationPolicy approvalEscalationPolicy);

    /**
     * Counts escalations assigned to the specified approver.
     *
     * @param toApproverId target approver identifier
     * @return escalation count
     */
    long countByToApproverId(
            String toApproverId);

    /**
     * Counts escalations originating from the specified approver.
     *
     * @param fromApproverId source approver identifier
     * @return escalation count
     */
    long countByFromApproverId(
            String fromApproverId);

    /**
     * Counts escalations assigned to the specified approver
     * having the given status.
     *
     * @param toApproverId target approver
     * @param status       escalation status
     * @return escalation count
     */
    long countByToApproverIdAndStatus(
            String toApproverId,
            ApprovalEscalationStatus status);

    /**
     * Counts escalations for an approval execution
     * having the specified status.
     *
     * @param approvalExecution approval execution
     * @param status            escalation status
     * @return escalation count
     */
    long countByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalEscalationStatus status);

    /**
     * Returns all escalations for an execution ordered by
     * scheduled date ascending.
     *
     * @param approvalExecution approval execution
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionOrderByScheduledAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Returns all escalations for an execution ordered by
     * escalation date ascending.
     *
     * @param approvalExecution approval execution
     * @return ordered escalations
     */
    List<ApprovalExecutionEscalation> findByApprovalExecutionOrderByEscalatedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Deletes all escalations belonging to the specified execution.
     *
     * @param approvalExecution approval execution
     */
    void deleteByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Deletes all escalations belonging to the specified stage.
     *
     * @param approvalExecutionStage execution stage
     */
    void deleteByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Deletes all escalations belonging to the specified task.
     *
     * @param approvalExecutionTask execution task
     */
    void deleteByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

}