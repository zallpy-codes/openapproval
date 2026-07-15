package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

import java.util.UUID;

/**
 * Coordinates workflow progression after an approval decision.
 *
 * <p>
 * The progressor delegates workflow advancement to the configured
 * approval strategy before determining whether the workflow can
 * be completed.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalWorkflowProgressor {

    /**
     * Progresses the supplied approval workflow after a step
     * has completed execution.
     *
     * @param workflow approval workflow
     * @param completedStep completed approval step
     * @param completedBy user that completed the step
     */
    void progress(
            ApprovalWorkflow workflow,
            ApprovalStep completedStep,
            UUID completedBy);

}