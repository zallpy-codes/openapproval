package com.zallpy.openapproval.approval.service.execution;

import java.util.UUID;

/**
 * Executes approval decisions for approval workflows.
 *
 * <p>
 * This service is the application entry point for
 * approving and rejecting workflow steps.
 * </p>
 *
 * <p>
 * Implementations coordinate:
 * <ul>
 *     <li>Workflow loading</li>
 *     <li>Current step resolution</li>
 *     <li>Decision validation</li>
 *     <li>Workflow progression</li>
 *     <li>Domain event publication</li>
 *     <li>Persistence</li>
 * </ul>
 * </p>
 *
 * Business rules remain inside the domain model.
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
     * @param comment rejection reason
     */
    void reject(
            UUID workflowId,
            UUID approverId,
            String comment);

}