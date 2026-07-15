package com.zallpy.openapproval.approval.service.delegation;

import java.util.UUID;

/**
 * Service responsible for approval delegation.
 *
 * <p>
 * Delegation allows an assigned approver to transfer
 * an approval step to another authorized approver.
 * </p>
 *
 * <p>
 * The implementation is responsible for validating
 * delegation rules, updating the approval step and
 * recording delegation history.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalDelegationService {

    /**
     * Delegates an approval step to another approver.
     *
     * @param workflowId workflow identifier
     * @param stepId approval step identifier
     * @param delegatedBy user performing the delegation
     * @param delegatedTo new approver
     * @param reason delegation reason
     */
    void delegate(
            UUID workflowId,
            UUID stepId,
            UUID delegatedBy,
            UUID delegatedTo,
            String reason);

}