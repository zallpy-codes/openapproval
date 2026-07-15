package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

/**
 * Resolves executable approval steps from a workflow.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalStepResolver {

    /**
     * Resolves the current executable approval step.
     *
     * @param workflow approval workflow
     * @return current approval step
     */
    ApprovalStep resolveCurrentStep(
            ApprovalWorkflow workflow);

}