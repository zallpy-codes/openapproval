package com.zallpy.openapproval.approval.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalStage}.
 *
 * <p>
 * Provides persistence operations for approval stages belonging
 * to an approval policy.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalStageRepository
        extends JpaRepository<ApprovalStage, UUID>,
        JpaSpecificationExecutor<ApprovalStage> {

    /**
     * Finds all stages for the specified approval policy ordered by stage order.
     *
     * @param approvalPolicy approval policy
     * @return ordered approval stages
     */
    List<ApprovalStage> findByApprovalPolicyOrderByStageOrderAsc(
            ApprovalPolicy approvalPolicy);

    /**
     * Finds all active stages for the specified approval policy.
     *
     * @param approvalPolicy approval policy
     * @return active approval stages
     */
    List<ApprovalStage> findByApprovalPolicyAndActiveTrueOrderByStageOrderAsc(
            ApprovalPolicy approvalPolicy);

    /**
     * Finds a stage by approval policy and stage code.
     *
     * @param approvalPolicy approval policy
     * @param stageCode stage code
     * @return approval stage
     */
    Optional<ApprovalStage> findByApprovalPolicyAndStageCode(
            ApprovalPolicy approvalPolicy,
            String stageCode);

    /**
     * Finds a stage by approval policy and stage order.
     *
     * @param approvalPolicy approval policy
     * @param stageOrder stage order
     * @return approval stage
     */
    Optional<ApprovalStage> findByApprovalPolicyAndStageOrder(
            ApprovalPolicy approvalPolicy,
            Integer stageOrder);

    /**
     * Determines whether a stage already exists for the specified
     * policy and stage order.
     *
     * @param approvalPolicy approval policy
     * @param stageOrder stage order
     * @return true if the stage exists
     */
    boolean existsByApprovalPolicyAndStageOrder(
            ApprovalPolicy approvalPolicy,
            Integer stageOrder);

    /**
     * Determines whether a stage code already exists within
     * the specified approval policy.
     *
     * @param approvalPolicy approval policy
     * @param stageCode stage code
     * @return true if the stage exists
     */
    boolean existsByApprovalPolicyAndStageCode(
            ApprovalPolicy approvalPolicy,
            String stageCode);

}