package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionAudit}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution audit records.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionAuditRepository extends
        JpaRepository<ApprovalExecutionAudit, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionAudit> {

    // -------------------------------------------------------------------------
    // Reference
    // -------------------------------------------------------------------------

    Optional<ApprovalExecutionAudit> findByAuditReference(
            String auditReference);

    boolean existsByAuditReference(
            String auditReference);

    void deleteByAuditReference(
            String auditReference);

    List<ApprovalExecutionAudit> findByAuditReferenceContainingIgnoreCase(
            String auditReference);

    // -------------------------------------------------------------------------
    // Execution
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    List<ApprovalExecutionAudit> findByApprovalExecutionOrderByRecordedAtAsc(
            ApprovalExecution approvalExecution);

    List<ApprovalExecutionAudit> findByApprovalExecutionOrderByRecordedAtDesc(
            ApprovalExecution approvalExecution);

    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    // -------------------------------------------------------------------------
    // Stage
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    List<ApprovalExecutionAudit> findByApprovalExecutionStageOrderByRecordedAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    List<ApprovalExecutionAudit> findByApprovalExecutionStageOrderByRecordedAtDesc(
            ApprovalExecutionStage approvalExecutionStage);

    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    // -------------------------------------------------------------------------
    // Task
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    List<ApprovalExecutionAudit> findByApprovalExecutionTaskOrderByRecordedAtAsc(
            ApprovalExecutionTask approvalExecutionTask);

    List<ApprovalExecutionAudit> findByApprovalExecutionTaskOrderByRecordedAtDesc(
            ApprovalExecutionTask approvalExecutionTask);

    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    // -------------------------------------------------------------------------
    // Action
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByAuditAction(
            ApprovalAuditAction auditAction);

    List<ApprovalExecutionAudit> findByAuditActionOrderByRecordedAtDesc(
            ApprovalAuditAction auditAction);

    long countByAuditAction(
            ApprovalAuditAction auditAction);

    // -------------------------------------------------------------------------
    // Actor
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByActorType(
            ApprovalAuditActorType actorType);

    List<ApprovalExecutionAudit> findByActorId(
            String actorId);

    List<ApprovalExecutionAudit> findByActorNameContainingIgnoreCase(
            String actorName);

    List<ApprovalExecutionAudit> findByActorEmail(
            String actorEmail);

    List<ApprovalExecutionAudit> findByActorTypeOrderByRecordedAtDesc(
            ApprovalAuditActorType actorType);

    List<ApprovalExecutionAudit> findByActorIdOrderByRecordedAtDesc(
            String actorId);

    long countByActorType(
            ApprovalAuditActorType actorType);

    long countByActorId(
            String actorId);

    // -------------------------------------------------------------------------
    // Active
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByActive(
            boolean active);

    long countByActive(
            boolean active);

    // -------------------------------------------------------------------------
    // Combined Queries
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByApprovalExecutionAndAuditAction(
            ApprovalExecution approvalExecution,
            ApprovalAuditAction auditAction);

    List<ApprovalExecutionAudit> findByApprovalExecutionAndActorType(
            ApprovalExecution approvalExecution,
            ApprovalAuditActorType actorType);

    List<ApprovalExecutionAudit> findByApprovalExecutionAndActive(
            ApprovalExecution approvalExecution,
            boolean active);

    List<ApprovalExecutionAudit> findByApprovalExecutionStageAndAuditAction(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalAuditAction auditAction);

    List<ApprovalExecutionAudit> findByApprovalExecutionTaskAndAuditAction(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalAuditAction auditAction);

    long countByApprovalExecutionAndAuditAction(
            ApprovalExecution approvalExecution,
            ApprovalAuditAction auditAction);

    long countByApprovalExecutionAndActorType(
            ApprovalExecution approvalExecution,
            ApprovalAuditActorType actorType);

    long countByApprovalExecutionAndActive(
            ApprovalExecution approvalExecution,
            boolean active);

    // -------------------------------------------------------------------------
    // Date Queries
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findByRecordedAtBefore(
            LocalDateTime recordedAt);

    List<ApprovalExecutionAudit> findByRecordedAtAfter(
            LocalDateTime recordedAt);

    List<ApprovalExecutionAudit> findByRecordedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    List<ApprovalExecutionAudit> findByCreatedDateAfter(
            LocalDateTime createdDate);

    List<ApprovalExecutionAudit> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    List<ApprovalExecutionAudit> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    List<ApprovalExecutionAudit> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    // -------------------------------------------------------------------------
    // Ordering
    // -------------------------------------------------------------------------

    List<ApprovalExecutionAudit> findAllByOrderByRecordedAtAsc();

    List<ApprovalExecutionAudit> findAllByOrderByRecordedAtDesc();

    List<ApprovalExecutionAudit> findAllByOrderByCreatedDateDesc();

    // -------------------------------------------------------------------------
    // Top Records
    // -------------------------------------------------------------------------

    Optional<ApprovalExecutionAudit> findTopByOrderByRecordedAtDesc();

    Optional<ApprovalExecutionAudit> findTopByOrderByCreatedDateDesc();

    Optional<ApprovalExecutionAudit> findTopByApprovalExecutionOrderByRecordedAtDesc(
            ApprovalExecution approvalExecution);

    Optional<ApprovalExecutionAudit> findTopByApprovalExecutionStageOrderByRecordedAtDesc(
            ApprovalExecutionStage approvalExecutionStage);

    Optional<ApprovalExecutionAudit> findTopByApprovalExecutionTaskOrderByRecordedAtDesc(
            ApprovalExecutionTask approvalExecutionTask);

    Optional<ApprovalExecutionAudit> findTopByAuditActionOrderByRecordedAtDesc(
            ApprovalAuditAction auditAction);

    Optional<ApprovalExecutionAudit> findTopByActorIdOrderByRecordedAtDesc(
            String actorId);

    Optional<ApprovalExecutionAudit> findTopByActiveOrderByCreatedDateDesc(
            boolean active);

    // -------------------------------------------------------------------------
    // UUID
    // -------------------------------------------------------------------------

    /**
     * Finds an audit record by its unique UUID.
     *
     * @param auditUuid audit UUID
     * @return matching audit record
     */
    Optional<ApprovalExecutionAudit> findByAuditUuid(
            UUID auditUuid);

    /**
     * Returns whether an audit UUID exists.
     *
     * @param auditUuid audit UUID
     * @return true if found
     */
    boolean existsByAuditUuid(
            UUID auditUuid);

    // -------------------------------------------------------------------------
    // Parent + Reference
    // -------------------------------------------------------------------------

    /**
     * Finds an audit record by approval execution and audit reference.
     *
     * @param approvalExecution approval execution
     * @param auditReference    audit reference
     * @return matching audit record
     */
    Optional<ApprovalExecutionAudit> findByApprovalExecutionAndAuditReference(
            ApprovalExecution approvalExecution,
            String auditReference);

    /**
     * Finds an audit record by execution stage and audit reference.
     *
     * @param approvalExecutionStage execution stage
     * @param auditReference         audit reference
     * @return matching audit record
     */
    Optional<ApprovalExecutionAudit> findByApprovalExecutionStageAndAuditReference(
            ApprovalExecutionStage approvalExecutionStage,
            String auditReference);

    /**
     * Finds an audit record by execution task and audit reference.
     *
     * @param approvalExecutionTask execution task
     * @param auditReference        audit reference
     * @return matching audit record
     */
    Optional<ApprovalExecutionAudit> findByApprovalExecutionTaskAndAuditReference(
            ApprovalExecutionTask approvalExecutionTask,
            String auditReference);

    // -------------------------------------------------------------------------
    // Search
    // -------------------------------------------------------------------------

    /**
     * Finds audit records whose summary contains the supplied text.
     *
     * @param summary summary fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findBySummaryContainingIgnoreCase(
            String summary);

    /**
     * Finds audit records whose details contain the supplied text.
     *
     * @param details details fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByDetailsContainingIgnoreCase(
            String details);

    /**
     * Finds audit records whose actor email contains the supplied text.
     *
     * @param actorEmail actor email fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByActorEmailContainingIgnoreCase(
            String actorEmail);

    /**
     * Finds audit records by client IP address.
     *
     * @param ipAddress client IP address
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByIpAddress(
            String ipAddress);

    /**
     * Finds audit records whose client IP address contains the supplied text.
     *
     * @param ipAddress IP address fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByIpAddressContainingIgnoreCase(
            String ipAddress);

    /**
     * Finds audit records whose user agent contains the supplied text.
     *
     * @param userAgent user agent fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByUserAgentContainingIgnoreCase(
            String userAgent);

    /**
     * Finds audit records whose metadata contains the supplied text.
     *
     * @param metadata metadata fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByMetadataContainingIgnoreCase(
            String metadata);

    // -------------------------------------------------------------------------
    // Ordered Search
    // -------------------------------------------------------------------------

    /**
     * Finds audit records whose actor name contains the supplied text,
     * ordered by recorded date descending.
     *
     * @param actorName actor name fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByActorNameContainingIgnoreCaseOrderByRecordedAtDesc(
            String actorName);

    /**
     * Finds audit records whose summary contains the supplied text,
     * ordered by recorded date descending.
     *
     * @param summary summary fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findBySummaryContainingIgnoreCaseOrderByRecordedAtDesc(
            String summary);

    /**
     * Finds audit records whose reference contains the supplied text,
     * ordered by recorded date descending.
     *
     * @param auditReference audit reference fragment
     * @return matching audit records
     */
    List<ApprovalExecutionAudit> findByAuditReferenceContainingIgnoreCaseOrderByRecordedAtDesc(
            String auditReference);

    // -------------------------------------------------------------------------
    // Combined Counts
    // -------------------------------------------------------------------------

    /**
     * Counts audit records for an execution stage by audit action.
     *
     * @param approvalExecutionStage execution stage
     * @param auditAction            audit action
     * @return audit record count
     */
    long countByApprovalExecutionStageAndAuditAction(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalAuditAction auditAction);

    /**
     * Counts audit records for an execution task by audit action.
     *
     * @param approvalExecutionTask execution task
     * @param auditAction           audit action
     * @return audit record count
     */
    long countByApprovalExecutionTaskAndAuditAction(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalAuditAction auditAction);

            
}