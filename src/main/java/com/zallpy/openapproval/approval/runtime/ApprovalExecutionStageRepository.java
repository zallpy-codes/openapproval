package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalExecutionStageStatus;
import com.zallpy.openapproval.approval.enums.ApprovalMode;
import com.zallpy.openapproval.approval.enums.ApprovalPriority;
import com.zallpy.openapproval.approval.enums.ApprovalStageType;
import com.zallpy.openapproval.approval.policy.ApprovalPolicyStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionStage}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution stages.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionStageRepository extends
        JpaRepository<ApprovalExecutionStage, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionStage> {

    /**
     * Finds a runtime stage by its UUID.
     *
     * @param stageUuid stage UUID
     * @return matching runtime stage
     */
    Optional<ApprovalExecutionStage> findByStageUuid(
            UUID stageUuid);

    /**
     * Returns whether a runtime stage exists.
     *
     * @param stageUuid stage UUID
     * @return true if found
     */
    boolean existsByStageUuid(
            UUID stageUuid);

    /**
     * Finds all runtime stages belonging to an approval request.
     *
     * @param approvalRequest approval request
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Finds all runtime stages belonging to a policy stage.
     *
     * @param approvalPolicyStage approval policy stage
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalPolicyStage(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Finds runtime stages by execution status.
     *
     * @param status execution stage status
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByStatus(
            ApprovalExecutionStageStatus status);

    /**
     * Finds runtime stages by stage type.
     *
     * @param stageType stage type
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByStageType(
            ApprovalStageType stageType);

    /**
     * Finds runtime stages by approval mode.
     *
     * @param approvalMode approval mode
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalMode(
            ApprovalMode approvalMode);

    /**
     * Finds runtime stages by priority.
     *
     * @param priority approval priority
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByPriority(
            ApprovalPriority priority);

    /**
     * Finds runtime stages by stage order.
     *
     * @param stageOrder stage execution order
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByStageOrder(
            Integer stageOrder);

    /**
     * Finds runtime stages by stage code.
     *
     * @param stageCode stage code
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByStageCode(
            String stageCode);

    /**
     * Finds runtime stages by stage name.
     *
     * @param stageName stage name
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByStageName(
            String stageName);

    /**
     * Finds runtime stages due before the specified date.
     *
     * @param dueAt due date
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByDueAtBefore(
            LocalDateTime dueAt);

    /**
     * Finds runtime stages due after the specified date.
     *
     * @param dueAt due date
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByDueAtAfter(
            LocalDateTime dueAt);

    /**
     * Finds active runtime stages.
     *
     * @param active active flag
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByActive(
            boolean active);

    /**
     * Finds runtime stages by approval request ordered by stage order.
     *
     * @param approvalRequest approval request
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequestOrderByStageOrderAsc(
            ApprovalRequest approvalRequest);

    /**
     * Finds runtime stages by approval request and status.
     *
     * @param approvalRequest approval request
     * @param status          execution stage status
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequestAndStatus(
            ApprovalRequest approvalRequest,
            ApprovalExecutionStageStatus status);

    /**
     * Finds runtime stages by approval request and active flag.
     *
     * @param approvalRequest approval request
     * @param active          active flag
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequestAndActive(
            ApprovalRequest approvalRequest,
            boolean active);

    /**
     * Finds runtime stages by approval policy stage and status.
     *
     * @param approvalPolicyStage approval policy stage
     * @param status              execution stage status
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalPolicyStageAndStatus(
            ApprovalPolicyStage approvalPolicyStage,
            ApprovalExecutionStageStatus status);

    /**
     * Finds runtime stages by approval policy stage ordered by stage order.
     *
     * @param approvalPolicyStage approval policy stage
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalPolicyStageOrderByStageOrderAsc(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Finds runtime stages ordered by stage order.
     *
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findAllByOrderByStageOrderAsc();

    /**
     * Finds runtime stages ordered by due date.
     *
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findAllByOrderByDueAtAsc();

    /**
     * Finds runtime stages ordered by creation date.
     *
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findAllByOrderByCreatedDateDesc();

    /**
     * Finds runtime stages by status ordered by stage order.
     *
     * @param status execution stage status
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByStatusOrderByStageOrderAsc(
            ApprovalExecutionStageStatus status);

    /**
     * Finds runtime stages by priority ordered by stage order.
     *
     * @param priority approval priority
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByPriorityOrderByStageOrderAsc(
            ApprovalPriority priority);

    /**
     * Finds runtime stages due within the specified period.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return runtime stages
     */
    List<ApprovalExecutionStage> findByDueAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts runtime stages by execution status.
     *
     * @param status execution stage status
     * @return stage count
     */
    long countByStatus(
            ApprovalExecutionStageStatus status);

    /**
     * Counts runtime stages for an approval request.
     *
     * @param approvalRequest approval request
     * @return stage count
     */
    long countByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Counts runtime stages for a policy stage.
     *
     * @param approvalPolicyStage approval policy stage
     * @return stage count
     */
    long countByApprovalPolicyStage(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Counts runtime stages by approval request and status.
     *
     * @param approvalRequest approval request
     * @param status          execution stage status
     * @return stage count
     */
    long countByApprovalRequestAndStatus(
            ApprovalRequest approvalRequest,
            ApprovalExecutionStageStatus status);

    /**
     * Counts active runtime stages.
     *
     * @param active active flag
     * @return stage count
     */
    long countByActive(
            boolean active);

    /**
     * Returns whether a runtime stage exists for the approval request.
     *
     * @param approvalRequest approval request
     * @return true if found
     */
    boolean existsByApprovalRequest(
            ApprovalRequest approvalRequest);

    /**
     * Returns whether a runtime stage exists for the policy stage.
     *
     * @param approvalPolicyStage approval policy stage
     * @return true if found
     */
    boolean existsByApprovalPolicyStage(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Finds the first runtime stage for an approval request.
     *
     * @param approvalRequest approval request
     * @return first runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByApprovalRequestOrderByStageOrderAsc(
            ApprovalRequest approvalRequest);

    /**
     * Finds the last runtime stage for an approval request.
     *
     * @param approvalRequest approval request
     * @return last runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByApprovalRequestOrderByStageOrderDesc(
            ApprovalRequest approvalRequest);

    /**
     * Finds the first runtime stage for a policy stage.
     *
     * @param approvalPolicyStage approval policy stage
     * @return first runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByApprovalPolicyStageOrderByStageOrderAsc(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Finds the most recently created runtime stage.
     *
     * @return latest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByOrderByCreatedDateDesc();

    /**
     * Finds the oldest runtime stage.
     *
     * @return oldest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByOrderByCreatedDateAsc();

    /**
     * Deletes a runtime stage by UUID.
     *
     * @param stageUuid stage UUID
     */
    void deleteByStageUuid(
            UUID stageUuid);

    /**
     * Finds runtime stages by stage code ignoring case.
     *
     * @param stageCode stage code
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByStageCodeIgnoreCase(
            String stageCode);

    /**
     * Finds runtime stages whose stage name contains the supplied text.
     *
     * @param stageName stage name fragment
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByStageNameContainingIgnoreCase(
            String stageName);

    /**
     * Finds runtime stages by approval request and stage order.
     *
     * @param approvalRequest approval request
     * @param stageOrder      stage order
     * @return matching runtime stage
     */
    Optional<ApprovalExecutionStage> findByApprovalRequestAndStageOrder(
            ApprovalRequest approvalRequest,
            Integer stageOrder);

    /**
     * Finds runtime stages by approval request and stage code.
     *
     * @param approvalRequest approval request
     * @param stageCode       stage code
     * @return matching runtime stage
     */
    Optional<ApprovalExecutionStage> findByApprovalRequestAndStageCode(
            ApprovalRequest approvalRequest,
            String stageCode);

    /**
     * Finds runtime stages by approval request and stage UUID.
     *
     * @param approvalRequest approval request
     * @param stageUuid       stage UUID
     * @return matching runtime stage
     */
    Optional<ApprovalExecutionStage> findByApprovalRequestAndStageUuid(
            ApprovalRequest approvalRequest,
            UUID stageUuid);

    /**
     * Finds runtime stages by status ordered by due date.
     *
     * @param status execution stage status
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByStatusOrderByDueAtAsc(
            ApprovalExecutionStageStatus status);

    /**
     * Finds runtime stages by approval request ordered by due date.
     *
     * @param approvalRequest approval request
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequestOrderByDueAtAsc(
            ApprovalRequest approvalRequest);

    /**
     * Finds runtime stages by approval request ordered by creation date.
     *
     * @param approvalRequest approval request
     * @return ordered runtime stages
     */
    List<ApprovalExecutionStage> findByApprovalRequestOrderByCreatedDateAsc(
            ApprovalRequest approvalRequest);

    /**
     * Finds runtime stages created after the specified date.
     *
     * @param createdDate creation date
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByCreatedDateAfter(
            LocalDateTime createdDate);

    /**
     * Finds runtime stages created between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds runtime stages modified after the specified date.
     *
     * @param lastModifiedDate last modification date
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    /**
     * Finds runtime stages modified between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching runtime stages
     */
    List<ApprovalExecutionStage> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts runtime stages by approval request and active flag.
     *
     * @param approvalRequest approval request
     * @param active          active flag
     * @return stage count
     */
    long countByApprovalRequestAndActive(
            ApprovalRequest approvalRequest,
            boolean active);

    /**
     * Counts runtime stages by policy stage and status.
     *
     * @param approvalPolicyStage approval policy stage
     * @param status              execution stage status
     * @return stage count
     */
    long countByApprovalPolicyStageAndStatus(
            ApprovalPolicyStage approvalPolicyStage,
            ApprovalExecutionStageStatus status);

    /**
     * Counts runtime stages by status and active flag.
     *
     * @param status execution stage status
     * @param active active flag
     * @return stage count
     */
    long countByStatusAndActive(
            ApprovalExecutionStageStatus status,
            boolean active);

    /**
     * Finds the latest runtime stage for an approval request.
     *
     * @param approvalRequest approval request
     * @return latest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByApprovalRequestOrderByCreatedDateDesc(
            ApprovalRequest approvalRequest);

    /**
     * Finds the latest runtime stage for a policy stage.
     *
     * @param approvalPolicyStage approval policy stage
     * @return latest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByApprovalPolicyStageOrderByCreatedDateDesc(
            ApprovalPolicyStage approvalPolicyStage);

    /**
     * Finds the latest runtime stage by execution status.
     *
     * @param status execution stage status
     * @return latest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByStatusOrderByCreatedDateDesc(
            ApprovalExecutionStageStatus status);

    /**
     * Finds the latest active runtime stage.
     *
     * @param active active flag
     * @return latest runtime stage
     */
    Optional<ApprovalExecutionStage> findTopByActiveOrderByCreatedDateDesc(
            boolean active);
}