package com.zallpy.openapproval.approval.execution;

import com.zallpy.openapproval.approval.enums.ApprovalExecutionStatus;
import com.zallpy.openapproval.approval.policy.ApprovalPolicy;
import com.zallpy.openapproval.approval.runtime.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecution}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval executions.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionRepository extends
        JpaRepository<ApprovalExecution, UUID>,
        JpaSpecificationExecutor<ApprovalExecution> {

    /**
     * Finds an execution by UUID.
     *
     * @param executionUuid execution UUID
     * @return matching execution
     */
    Optional<ApprovalExecution> findByExecutionUuid(UUID executionUuid);

    /**
     * Finds an execution by business reference.
     *
     * @param executionReference execution reference
     * @return matching execution
     */
    Optional<ApprovalExecution> findByExecutionReference(
            String executionReference);

    /**
     * Returns whether an execution UUID exists.
     *
     * @param executionUuid execution UUID
     * @return true if found
     */
    boolean existsByExecutionUuid(UUID executionUuid);

    /**
     * Returns whether an execution reference exists.
     *
     * @param executionReference execution reference
     * @return true if found
     */
    boolean existsByExecutionReference(
            String executionReference);

    /**
     * Finds all executions for an approval request.
     *
     * @param approvalRequest approval request
     * @return execution list
     */
    List<ApprovalExecution> findByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Finds all executions for an approval policy.
     *
     * @param approvalPolicy approval policy
     * @return execution list
     */
    List<ApprovalExecution> findByApprovalPolicy(
            ApprovalPolicy approvalPolicy);

    /**
     * Finds executions by status.
     *
     * @param executionStatus execution status
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionStatus(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds active executions by status.
     *
     * @param executionStatus execution status
     * @param active          active flag
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionStatusAndActive(
            ApprovalExecutionStatus executionStatus,
            boolean active);

    /**
     * Finds executions that have started.
     *
     * @param started started flag
     * @return execution list
     */
    List<ApprovalExecution> findByStarted(
            boolean started);

    /**
     * Finds completed executions.
     *
     * @param completed completed flag
     * @return execution list
     */
    List<ApprovalExecution> findByCompleted(
            boolean completed);

    /**
     * Finds cancelled executions.
     *
     * @param cancelled cancelled flag
     * @return execution list
     */
    List<ApprovalExecution> findByCancelled(
            boolean cancelled);

    /**
     * Finds suspended executions.
     *
     * @param suspended suspended flag
     * @return execution list
     */
    List<ApprovalExecution> findBySuspended(
            boolean suspended);

    /**
     * Finds executions due before the specified date/time.
     *
     * @param dueAt due date/time
     * @return execution list
     */
    List<ApprovalExecution> findByDueAtBefore(
            LocalDateTime dueAt);

    /**
     * Finds executions due after the specified date/time.
     *
     * @param dueAt due date/time
     * @return execution list
     */
    List<ApprovalExecution> findByDueAtAfter(
            LocalDateTime dueAt);

    /**
     * Finds executions currently at the specified stage order.
     *
     * @param currentStageOrder current stage order
     * @return matching executions
     */
    List<ApprovalExecution> findByCurrentStageOrder(
            Integer currentStageOrder);

    /**
     * Finds executions currently at the specified stage code.
     *
     * @param currentStageCode current stage code
     * @return matching executions
     */
    List<ApprovalExecution> findByCurrentStageCode(
            String currentStageCode);

    /**
     * Finds executions currently at the specified stage name.
     *
     * @param currentStageName current stage name
     * @return matching executions
     */
    List<ApprovalExecution> findByCurrentStageName(
            String currentStageName);

    /**
     * Finds executions by approval request ordered by creation date.
     *
     * @param approvalRequest approval request
     * @return ordered execution list
     */
    List<ApprovalExecution> findByApprovalRequestOrderByCreatedDateDesc(
            ApprovalRequest approvalRequest);

    /**
     * Finds executions by approval policy ordered by creation date.
     *
     * @param approvalPolicy approval policy
     * @return ordered execution list
     */
    List<ApprovalExecution> findByApprovalPolicyOrderByCreatedDateDesc(
            ApprovalPolicy approvalPolicy);

    /**
     * Finds executions ordered by due date.
     *
     * @return ordered execution list
     */
    List<ApprovalExecution> findAllByOrderByDueAtAsc();

    /**
     * Finds executions ordered by creation date.
     *
     * @return ordered execution list
     */
    List<ApprovalExecution> findAllByOrderByCreatedDateDesc();

    /**
     * Finds active executions ordered by creation date.
     *
     * @param active active flag
     * @return ordered execution list
     */
    List<ApprovalExecution> findByActiveOrderByCreatedDateDesc(
            boolean active);

    /**
     * Finds executions by status ordered by due date.
     *
     * @param executionStatus execution status
     * @return ordered execution list
     */
    List<ApprovalExecution> findByExecutionStatusOrderByDueAtAsc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds executions started within the specified period.
     *
     * @param startDate start date/time
     * @param endDate   end date/time
     * @return matching executions
     */
    List<ApprovalExecution> findByStartedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds executions completed within the specified period.
     *
     * @param startDate start date/time
     * @param endDate   end date/time
     * @return matching executions
     */
    List<ApprovalExecution> findByCompletedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds executions due within the specified period.
     *
     * @param startDate start date/time
     * @param endDate   end date/time
     * @return matching executions
     */
    List<ApprovalExecution> findByDueAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts executions by status.
     *
     * @param executionStatus execution status
     * @return execution count
     */
    long countByExecutionStatus(
            ApprovalExecutionStatus executionStatus);

    /**
     * Counts executions for an approval policy.
     *
     * @param approvalPolicy approval policy
     * @return execution count
     */
    long countByApprovalPolicy(
            ApprovalPolicy approvalPolicy);

    /**
     * Counts executions for an approval request.
     *
     * @param approvalRequest approval request
     * @return execution count
     */
    long countByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Counts active executions.
     *
     * @param active active flag
     * @return execution count
     */
    long countByActive(
            boolean active);

    /**
     * Returns whether an execution exists for the approval request.
     *
     * @param approvalRequest approval request
     * @return true if found
     */
    boolean existsByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Returns whether an execution exists for the execution reference.
     *
     * @param executionReference execution reference
     * @return true if found
     */
    boolean existsByExecutionReferenceIgnoreCase(
            String executionReference);

    /**
     * Finds the most recently created execution.
     *
     * @return latest execution
     */
    Optional<ApprovalExecution> findTopByOrderByCreatedDateDesc();

    /**
     * Finds the earliest created execution.
     *
     * @return oldest execution
     */
    Optional<ApprovalExecution> findTopByOrderByCreatedDateAsc();

    /**
     * Finds the most recently started execution.
     *
     * @return latest started execution
     */
    Optional<ApprovalExecution> findTopByStartedAtIsNotNullOrderByStartedAtDesc();

    /**
     * Finds the most recently completed execution.
     *
     * @return latest completed execution
     */
    Optional<ApprovalExecution> findTopByCompletedAtIsNotNullOrderByCompletedAtDesc();

    /**
     * Deletes an execution by UUID.
     *
     * @param executionUuid execution UUID
     */
    void deleteByExecutionUuid(
            UUID executionUuid);

    /**
     * Deletes an execution by execution reference.
     *
     * @param executionReference execution reference
     */
    void deleteByExecutionReference(
            String executionReference);

    /**
     * Finds all active executions.
     *
     * @param active active flag
     * @return execution list
     */
    List<ApprovalExecution> findByActive(
            boolean active);

    /**
     * Finds active executions by approval policy.
     *
     * @param approvalPolicy approval policy
     * @param active         active flag
     * @return execution list
     */
    List<ApprovalExecution> findByApprovalPolicyAndActive(
            ApprovalPolicy approvalPolicy,
            boolean active);

    /**
     * Finds active executions by approval request.
     *
     * @param approvalRequest approval request
     * @param active          active flag
     * @return execution list
     */
    List<ApprovalExecution> findByApprovalRequestAndActive(
            ApprovalRequest approvalRequest,
            boolean active);

    /**
     * Finds executions by status ordered by creation date.
     *
     * @param executionStatus execution status
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionStatusOrderByCreatedDateDesc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds executions by status ordered by start time.
     *
     * @param executionStatus execution status
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionStatusOrderByStartedAtAsc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds executions by status ordered by completion time.
     *
     * @param executionStatus execution status
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionStatusOrderByCompletedAtDesc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds executions created after the specified date.
     *
     * @param createdDate creation date
     * @return execution list
     */
    List<ApprovalExecution> findByCreatedDateAfter(
            LocalDateTime createdDate);

    /**
     * Finds executions created before the specified date.
     *
     * @param createdDate creation date
     * @return execution list
     */
    List<ApprovalExecution> findByCreatedDateBefore(
            LocalDateTime createdDate);

    /**
     * Finds executions created within the specified period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return execution list
     */
    List<ApprovalExecution> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds executions updated after the specified date.
     *
     * @param lastModifiedDate last modified date
     * @return execution list
     */
    List<ApprovalExecution> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    /**
     * Finds executions updated within the specified period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return execution list
     */
    List<ApprovalExecution> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds executions whose reference contains the supplied value.
     *
     * @param executionReference execution reference fragment
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionReferenceContainingIgnoreCase(
            String executionReference);

    /**
     * Finds executions whose name contains the supplied value.
     *
     * @param executionName execution name fragment
     * @return execution list
     */
    List<ApprovalExecution> findByExecutionNameContainingIgnoreCase(
            String executionName);

    /**
     * Finds executions whose completion remarks contain the supplied value.
     *
     * @param completionRemarks completion remarks fragment
     * @return execution list
     */
    List<ApprovalExecution> findByCompletionRemarksContainingIgnoreCase(
            String completionRemarks);

    /**
     * Counts executions for the specified status and active flag.
     *
     * @param executionStatus execution status
     * @param active          active flag
     * @return execution count
     */
    long countByExecutionStatusAndActive(
            ApprovalExecutionStatus executionStatus,
            boolean active);

    /**
     * Counts executions for the specified approval policy and status.
     *
     * @param approvalPolicy  approval policy
     * @param executionStatus execution status
     * @return execution count
     */
    long countByApprovalPolicyAndExecutionStatus(
            ApprovalPolicy approvalPolicy,
            ApprovalExecutionStatus executionStatus);

    /**
     * Counts executions for the specified approval request and status.
     *
     * @param approvalRequest approval request
     * @param executionStatus execution status
     * @return execution count
     */
    long countByApprovalRequestAndExecutionStatus(
            ApprovalRequest approvalRequest,
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds the latest execution for an approval request.
     *
     * @param approvalRequest approval request
     * @return latest execution
     */
    Optional<ApprovalExecution> findTopByApprovalRequestOrderByCreatedDateDesc(
            ApprovalRequest approvalRequest);

    /**
     * Finds the latest execution for an approval policy.
     *
     * @param approvalPolicy approval policy
     * @return latest execution
     */
    Optional<ApprovalExecution> findTopByApprovalPolicyOrderByCreatedDateDesc(
            ApprovalPolicy approvalPolicy);

    /**
     * Finds the latest execution by execution status.
     *
     * @param executionStatus execution status
     * @return latest execution
     */
    Optional<ApprovalExecution> findTopByExecutionStatusOrderByCreatedDateDesc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds the oldest execution by execution status.
     *
     * @param executionStatus execution status
     * @return oldest execution
     */
    Optional<ApprovalExecution> findTopByExecutionStatusOrderByCreatedDateAsc(
            ApprovalExecutionStatus executionStatus);

    /**
     * Finds the latest active execution.
     *
     * @param active active flag
     * @return latest execution
     */
    Optional<ApprovalExecution> findTopByActiveOrderByCreatedDateDesc(
            boolean active);
}