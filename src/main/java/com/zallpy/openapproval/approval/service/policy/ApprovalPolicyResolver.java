package com.zallpy.openapproval.approval.service.policy;

import java.util.UUID;

/**
 * Resolves approval policies used by the approval engine.
 *
 * <p>
 * This service is responsible for locating the appropriate approval
 * policy before a workflow is created.
 * </p>
 *
 * <p>
 * The implementation encapsulates all policy lookup logic, allowing
 * the engine to remain independent of the persistence layer.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalPolicyResolver {

    /**
     * Resolves an approval policy using its unique policy code.
     *
     * @param policyCode approval policy code
     * @return approval policy identifier
     */
    UUID resolvePolicyId(String policyCode);

    /**
     * Determines whether a policy exists.
     *
     * @param policyCode approval policy code
     * @return {@code true} if the policy exists; otherwise {@code false}
     */
    boolean policyExists(String policyCode);

    /**
     * Determines whether a policy is active.
     *
     * @param policyCode approval policy code
     * @return {@code true} if the policy is active; otherwise {@code false}
     */
    boolean isPolicyActive(String policyCode);

}