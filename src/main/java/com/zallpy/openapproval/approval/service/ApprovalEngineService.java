package com.zallpy.openapproval.approval.service;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.dto.response.ApprovalResult;
import com.zallpy.openapproval.approval.dto.response.ApprovalWorkflowResult;

import java.util.UUID;

/**
 * Primary entry point into the OpenApproval execution engine.
 *
 * <p>All approval operations are orchestrated through this service.</p>
 *
 * <p>No controller or external component should directly manipulate
 * approval entities.</p>
 *
 * @author Zallpy
 */
public interface ApprovalEngineService {

    /**
     * Creates and starts a new approval workflow.
     *
     * @param request submission request
     * @return workflow result
     */
    ApprovalWorkflowResult submitRequest(
            SubmitApprovalRequest request);

    /**
     * Approves an approval step.
     *
     * @param stepId approval step
     * @param comment approval comment
     * @return execution result
     */
    ApprovalResult approve(
            UUID stepId,
            String comment);

    /**
     * Rejects an approval step.
     *
     * @param stepId approval step
     * @param comment rejection reason
     * @return execution result
     */
    ApprovalResult reject(
            UUID stepId,
            String comment);

    /**
     * Delegates a step.
     *
     * @param stepId step identifier
     * @param delegateUserId new approver
     * @param reason delegation reason
     * @return execution result
     */
    ApprovalResult delegate(
            UUID stepId,
            UUID delegateUserId,
            String reason);

    /**
     * Returns a request for correction.
     *
     * @param stepId approval step
     * @param comment return reason
     * @return execution result
     */
    ApprovalResult returnForCorrection(
            UUID stepId,
            String comment);

    /**
     * Cancels an approval request.
     *
     * @param requestId request identifier
     * @param reason cancellation reason
     * @return execution result
     */
    ApprovalResult cancel(
            UUID requestId,
            String reason);

    /**
     * Recalls an approval request.
     *
     * @param requestId request identifier
     * @param reason recall reason
     * @return execution result
     */
    ApprovalResult recall(
            UUID requestId,
            String reason);

    /**
     * Executes timeout processing.
     */
    void processTimeouts();

    /**
     * Executes escalation processing.
     */
    void processEscalations();

}