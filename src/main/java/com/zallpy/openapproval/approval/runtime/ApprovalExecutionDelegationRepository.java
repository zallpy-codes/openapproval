package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalDelegationReason;
import com.zallpy.openapproval.approval.enums.ApprovalDelegationStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionDelegation}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution delegations.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionDelegationRepository extends
        JpaRepository<ApprovalExecutionDelegation, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionDelegation> {

    /**
     * Finds a delegation by its business reference.
     *
     * @param delegationReference delegation reference
     * @return matching delegation
     */
    Optional<ApprovalExecutionDelegation> findByDelegationReference(
            String delegationReference);

    /**
     * Finds a delegation by its UUID.
     *
     * @param delegationUuid delegation UUID
     * @return matching delegation
     */
    Optional<ApprovalExecutionDelegation> findByDelegationUuid(
            UUID delegationUuid);

    /**
     * Returns whether a delegation reference exists.
     *
     * @param delegationReference delegation reference
     * @return true if found
     */
    boolean existsByDelegationReference(
            String delegationReference);

    /**
     * Returns whether a delegation UUID exists.
     *
     * @param delegationUuid delegation UUID
     * @return true if found
     */
    boolean existsByDelegationUuid(
            UUID delegationUuid);

    /**
     * Finds delegations belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Finds delegations belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds delegations belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds delegations by status.
     *
     * @param status delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByStatus(
            ApprovalDelegationStatus status);

    /**
     * Finds delegations by reason.
     *
     * @param delegationReason delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegationReason(
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations by source approver.
     *
     * @param fromApproverId source approver identifier
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByFromApproverId(
            String fromApproverId);

    /**
     * Finds delegations by delegate approver.
     *
     * @param toApproverId delegate approver identifier
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverId(
            String toApproverId);

    /**
     * Finds delegations by delegate email.
     *
     * @param toApproverEmail delegate email
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverEmail(
            String toApproverEmail);

    /**
     * Finds active delegations.
     *
     * @param active active flag
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByActive(
            boolean active);

    /**
     * Finds delegations created before the specified time.
     *
     * @param delegatedAt delegation timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegatedAtBefore(
            LocalDateTime delegatedAt);

    /**
     * Finds delegations created after the specified time.
     *
     * @param delegatedAt delegation timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegatedAtAfter(
            LocalDateTime delegatedAt);

    /**
     * Finds delegations accepted before the specified time.
     *
     * @param acceptedAt acceptance timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByAcceptedAtBefore(
            LocalDateTime acceptedAt);

    /**
     * Finds delegations accepted after the specified time.
     *
     * @param acceptedAt acceptance timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByAcceptedAtAfter(
            LocalDateTime acceptedAt);

    /**
     * Finds delegations completed before the specified time.
     *
     * @param completedAt completion timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByCompletedAtBefore(
            LocalDateTime completedAt);

    /**
     * Finds delegations completed after the specified time.
     *
     * @param completedAt completion timestamp
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByCompletedAtAfter(
            LocalDateTime completedAt);

    /**
     * Finds delegations ordered by delegation time.
     *
     * @param approvalExecution approval execution
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionOrderByDelegatedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds stage delegations ordered by delegation time.
     *
     * @param approvalExecutionStage execution stage
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionStageOrderByDelegatedAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds task delegations ordered by delegation time.
     *
     * @param approvalExecutionTask execution task
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionTaskOrderByDelegatedAtAsc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds delegations by source approver ordered by delegation time.
     *
     * @param fromApproverId source approver identifier
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByFromApproverIdOrderByDelegatedAtDesc(
            String fromApproverId);

    /**
     * Finds delegations by delegate approver ordered by delegation time.
     *
     * @param toApproverId delegate approver identifier
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverIdOrderByDelegatedAtDesc(
            String toApproverId);

    /**
     * Finds delegations by status ordered by delegation time.
     *
     * @param status delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByStatusOrderByDelegatedAtDesc(
            ApprovalDelegationStatus status);

    /**
     * Finds delegations by reason ordered by delegation time.
     *
     * @param delegationReason delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegationReasonOrderByDelegatedAtDesc(
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations occurring between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegatedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds delegations for an approval execution by status.
     *
     * @param approvalExecution approval execution
     * @param status            delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalDelegationStatus status);

    /**
     * Finds delegations for an execution stage by status.
     *
     * @param approvalExecutionStage execution stage
     * @param status                 delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionStageAndStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalDelegationStatus status);

    /**
     * Finds delegations for an execution task by status.
     *
     * @param approvalExecutionTask execution task
     * @param status                delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionTaskAndStatus(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalDelegationStatus status);

    /**
     * Finds delegations for an approval execution by delegation reason.
     *
     * @param approvalExecution approval execution
     * @param delegationReason  delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionAndDelegationReason(
            ApprovalExecution approvalExecution,
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations for an execution stage by delegation reason.
     *
     * @param approvalExecutionStage execution stage
     * @param delegationReason       delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionStageAndDelegationReason(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations for an execution task by delegation reason.
     *
     * @param approvalExecutionTask execution task
     * @param delegationReason      delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionTaskAndDelegationReason(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations for the specified source approver and status.
     *
     * @param fromApproverId source approver identifier
     * @param status         delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByFromApproverIdAndStatus(
            String fromApproverId,
            ApprovalDelegationStatus status);

    /**
     * Finds delegations for the specified delegate approver and status.
     *
     * @param toApproverId delegate approver identifier
     * @param status       delegation status
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverIdAndStatus(
            String toApproverId,
            ApprovalDelegationStatus status);

    /**
     * Finds delegations for the specified source approver and reason.
     *
     * @param fromApproverId   source approver identifier
     * @param delegationReason delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByFromApproverIdAndDelegationReason(
            String fromApproverId,
            ApprovalDelegationReason delegationReason);

    /**
     * Finds delegations for the specified delegate approver and reason.
     *
     * @param toApproverId     delegate approver identifier
     * @param delegationReason delegation reason
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverIdAndDelegationReason(
            String toApproverId,
            ApprovalDelegationReason delegationReason);

    /**
     * Counts delegations by status.
     *
     * @param status delegation status
     * @return delegation count
     */
    long countByStatus(
            ApprovalDelegationStatus status);

    /**
     * Counts delegations by delegation reason.
     *
     * @param delegationReason delegation reason
     * @return delegation count
     */
    long countByDelegationReason(
            ApprovalDelegationReason delegationReason);

    /**
     * Counts delegations belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return delegation count
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts delegations belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return delegation count
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts delegations belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return delegation count
     */
    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Counts delegations by source approver.
     *
     * @param fromApproverId source approver identifier
     * @return delegation count
     */
    long countByFromApproverId(
            String fromApproverId);

    /**
     * Counts delegations by delegate approver.
     *
     * @param toApproverId delegate approver identifier
     * @return delegation count
     */
    long countByToApproverId(
            String toApproverId);

    /**
     * Counts active delegations.
     *
     * @param active active flag
     * @return delegation count
     */
    long countByActive(
            boolean active);

    /**
     * Returns whether delegations exist for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return true if found
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether delegations exist for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return true if found
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns whether delegations exist for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return true if found
     */
    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Returns whether delegations exist for the specified source approver.
     *
     * @param fromApproverId source approver identifier
     * @return true if found
     */
    boolean existsByFromApproverId(
            String fromApproverId);

    /**
     * Returns whether delegations exist for the specified delegate approver.
     *
     * @param toApproverId delegate approver identifier
     * @return true if found
     */
    boolean existsByToApproverId(
            String toApproverId);

    /**
     * Returns whether delegations exist for the specified status.
     *
     * @param status delegation status
     * @return true if found
     */
    boolean existsByStatus(
            ApprovalDelegationStatus status);

    /**
     * Finds the earliest recorded delegation.
     *
     * @return earliest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByOrderByDelegatedAtAsc();

    /**
     * Finds the most recently recorded delegation.
     *
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByOrderByDelegatedAtDesc();

    /**
     * Finds the earliest delegation for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return earliest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByApprovalExecutionOrderByDelegatedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest delegation for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByApprovalExecutionOrderByDelegatedAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest delegation for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByApprovalExecutionStageOrderByDelegatedAtDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the latest delegation for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByApprovalExecutionTaskOrderByDelegatedAtDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds the latest delegation performed by a source approver.
     *
     * @param fromApproverId source approver identifier
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByFromApproverIdOrderByDelegatedAtDesc(
            String fromApproverId);

    /**
     * Finds the latest delegation received by a delegate approver.
     *
     * @param toApproverId delegate approver identifier
     * @return latest delegation
     */
    Optional<ApprovalExecutionDelegation> findTopByToApproverIdOrderByDelegatedAtDesc(
            String toApproverId);

    /**
     * Deletes a delegation by its business reference.
     *
     * @param delegationReference delegation reference
     */
    void deleteByDelegationReference(
            String delegationReference);

    /**
     * Finds delegations whose reference contains the supplied text.
     *
     * @param delegationReference delegation reference fragment
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByDelegationReferenceContainingIgnoreCase(
            String delegationReference);

    /**
     * Finds delegations whose source approver name contains the supplied text.
     *
     * @param fromApproverName source approver name
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByFromApproverNameContainingIgnoreCase(
            String fromApproverName);

    /**
     * Finds delegations whose delegate approver name contains the supplied text.
     *
     * @param toApproverName delegate approver name
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverNameContainingIgnoreCase(
            String toApproverName);

    /**
     * Finds delegations whose delegate email contains the supplied text.
     *
     * @param toApproverEmail delegate email
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByToApproverEmailContainingIgnoreCase(
            String toApproverEmail);

    /**
     * Finds delegations ordered by creation date.
     *
     * @param approvalExecution approval execution
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionOrderByCreatedDateDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds stage delegations ordered by creation date.
     *
     * @param approvalExecutionStage execution stage
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionStageOrderByCreatedDateDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds task delegations ordered by creation date.
     *
     * @param approvalExecutionTask execution task
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByApprovalExecutionTaskOrderByCreatedDateDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds delegations created after the specified date.
     *
     * @param createdDate creation date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByCreatedDateAfter(
            LocalDateTime createdDate);

    /**
     * Finds delegations created between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds delegations modified after the specified date.
     *
     * @param lastModifiedDate modification date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    /**
     * Finds delegations modified between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds delegations accepted between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByAcceptedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds delegations completed between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching delegations
     */
    List<ApprovalExecutionDelegation> findByCompletedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts delegations for an approval execution by status.
     *
     * @param approvalExecution approval execution
     * @param status            delegation status
     * @return delegation count
     */
    long countByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalDelegationStatus status);

    /**
     * Counts delegations for an execution stage by status.
     *
     * @param approvalExecutionStage execution stage
     * @param status                 delegation status
     * @return delegation count
     */
    long countByApprovalExecutionStageAndStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalDelegationStatus status);

    /**
     * Counts delegations for an execution task by status.
     *
     * @param approvalExecutionTask execution task
     * @param status                delegation status
     * @return delegation count
     */
    long countByApprovalExecutionTaskAndStatus(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalDelegationStatus status);

    /**
     * Counts delegations for an approval execution by reason.
     *
     * @param approvalExecution approval execution
     * @param delegationReason  delegation reason
     * @return delegation count
     */
    long countByApprovalExecutionAndDelegationReason(
            ApprovalExecution approvalExecution,
            ApprovalDelegationReason delegationReason);

    /**
     * Counts delegations for an execution stage by reason.
     *
     * @param approvalExecutionStage execution stage
     * @param delegationReason       delegation reason
     * @return delegation count
     */
    long countByApprovalExecutionStageAndDelegationReason(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalDelegationReason delegationReason);

    /**
     * Counts delegations for an execution task by reason.
     *
     * @param approvalExecutionTask execution task
     * @param delegationReason      delegation reason
     * @return delegation count
     */
    long countByApprovalExecutionTaskAndDelegationReason(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalDelegationReason delegationReason);

}