package com.zallpy.openapproval.approval.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalPolicy}.
 *
 * <p>
 * Provides persistence operations for approval policy configuration.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalPolicyRepository
        extends JpaRepository<ApprovalPolicy, UUID>,
                JpaSpecificationExecutor<ApprovalPolicy> {

    /**
     * Finds an approval policy by its unique policy code.
     *
     * @param policyCode policy code
     * @return approval policy
     */
    Optional<ApprovalPolicy> findByPolicyCode(String policyCode);

    /**
     * Finds an active approval policy by its policy code.
     *
     * @param policyCode policy code
     * @return approval policy
     */
    Optional<ApprovalPolicy> findByPolicyCodeAndActiveTrue(String policyCode);

    /**
     * Determines whether a policy with the specified code exists.
     *
     * @param policyCode policy code
     * @return true if the policy exists
     */
    boolean existsByPolicyCode(String policyCode);

    /**
     * Determines whether an active policy with the specified code exists.
     *
     * @param policyCode policy code
     * @return true if the policy exists
     */
    boolean existsByPolicyCodeAndActiveTrue(String policyCode);

    /**
     * Returns all active approval policies ordered by name.
     *
     * @return active approval policies
     */
    List<ApprovalPolicy> findByActiveTrueOrderByPolicyNameAsc();

    /**
     * Returns all inactive approval policies ordered by name.
     *
     * @return inactive approval policies
     */
    List<ApprovalPolicy> findByActiveFalseOrderByPolicyNameAsc();

    /**
     * Returns all approval policies belonging to a resource type.
     *
     * @param resourceType resource type
     * @return approval policies
     */
    List<ApprovalPolicy> findByResourceTypeOrderByPolicyNameAsc(
            String resourceType);

    /**
     * Returns all active approval policies for a resource type.
     *
     * @param resourceType resource type
     * @return approval policies
     */
    List<ApprovalPolicy> findByResourceTypeAndActiveTrueOrderByPolicyNameAsc(
            String resourceType);

}