package com.zallpy.openapproval.approval.service;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.dto.response.ApprovalResult;
import com.zallpy.openapproval.approval.dto.response.ApprovalWorkflowResult;

import java.util.UUID;

/**
 * Primary entry point into the OpenApproval execution engine.
 *
 * <p>
 * All approval workflow operations are orchestrated through this service.
 * External applications should interact only with this interface rather
 * than manipulating approval entities directly.
 * </p>
 *
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Submitting approval requests</li>
 *     <li>Processing approval decisions</li>
 *     <li>Delegating approval tasks</li>
 *     <li>Returning requests for correction</li>
 *     <li>Cancelling and recalling workflows</li>
 *     <li>Executing timeout processing</li>
 *     <li>Executing escalation processing</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalEngineService {

    /**
     * Creates and starts a new approval workflow.
     *
     * @param request approval submission request
     * @return workflow execution result
     */
    ApprovalWorkflowResult submitRequest(SubmitApprovalRequest request);

    /**
     * Approves the specified approval step.
     *
     * @param stepId approval step identifier
     * @param comment approval comment
     * @return operation result
     */
    ApprovalResult approve(UUID stepId, String comment);

    /**
     * Rejects the specified approval step.
     *
     * @param stepId approval step identifier
     * @param comment rejection reason
     * @return operation result
     */
    ApprovalResult reject(UUID stepId, String comment);

    /**
     * Delegates an approval step to another user.
     *
     * @param stepId approval step identifier
     * @param delegateUserId delegate user identifier
     * @param reason delegation reason
     * @return operation result
     */
    ApprovalResult delegate(UUID stepId,
                            UUID delegateUserId,
                            String reason);

    /**
     * Returns the approval request for correction.
     *
     * @param stepId approval step identifier
     * @param comment return comment
     * @return operation result
     */
    ApprovalResult returnForCorrection(UUID stepId,
                                       String comment);

    /**
     * Cancels an approval workflow.
     *
     * @param requestId approval request identifier
     * @param reason cancellation reason
     * @return operation result
     */
    ApprovalResult cancel(UUID requestId,
                          String reason);

    /**
     * Recalls an approval workflow.
     *
     * @param requestId approval request identifier
     * @param reason recall reason
     * @return operation result
     */
    ApprovalResult recall(UUID requestId,
                          String reason);

    /**
     * Processes all approval requests that have exceeded
     * their configured timeout.
     */
    void processTimeouts();

    /**
     * Processes all approval requests that require escalation.
     */
    void processEscalations();

}