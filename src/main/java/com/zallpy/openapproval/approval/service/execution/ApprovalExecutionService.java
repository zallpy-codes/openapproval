package com.zallpy.openapproval.approval.service.execution;

import java.util.UUID;

/**
 * Executes approval actions on approval workflows.
 *
 * <p>
 * This service represents the primary runtime entry point of the
 * OpenApproval execution engine.
 * It coordinates approval execution while delegating workflow
 * progression, strategy evaluation and completion to specialized
 * services.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalExecutionService {

    /**
     * Approves the current approval step.
     *
     * @param workflowId workflow identifier
     * @param approverId approving user
     * @param comment approval comment
     */
    void approve(
            UUID workflowId,
            UUID approverId,
            String comment);

    /**
     * Rejects the current approval step.
     *
     * @param workflowId workflow identifier
     * @param approverId rejecting user
     * @param comment rejection comment
     */
    void reject(
            UUID workflowId,
            UUID approverId,
            String comment);

}