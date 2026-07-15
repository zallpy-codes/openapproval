package com.zallpy.openapproval.approval.service.execution;

import java.util.UUID;

import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

/**
 * Completes approval workflows.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalCompletionService {

    /**
     * Completes the supplied workflow.
     *
     * @param workflow approval workflow
     */
    void complete(
            ApprovalWorkflow workflow,
            UUID completedBy);

}