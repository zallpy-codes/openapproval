package com.zallpy.openapproval.approval.service.execution;

import java.util.UUID;

import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

/**
 * Advances approval workflows after approval decisions.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalWorkflowProgressor {

    /**
     * Advances the supplied workflow.
     *
     * @param workflow approval workflow
     */
    void progress(
            ApprovalWorkflow workflow,
            UUID completedBy);

}