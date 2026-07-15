package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

import java.util.UUID;

/**
 * Carries runtime information required during
 * approval strategy execution.
 *
 * <p>
 * The execution context allows new runtime data
 * to be introduced without changing the strategy
 * contract.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalExecutionContext {

    /**
     * Workflow being executed.
     */
    private final ApprovalWorkflow workflow;

    /**
     * Step that has just completed.
     */
    private final ApprovalStep completedStep;

    /**
     * User that completed the step.
     */
    private final UUID completedBy;

    /**
     * Creates a new execution context.
     *
     * @param workflow workflow
     * @param completedStep completed step
     * @param completedBy user completing the step
     */
    public ApprovalExecutionContext(
            final ApprovalWorkflow workflow,
            final ApprovalStep completedStep,
            final UUID completedBy) {

        this.workflow = workflow;
        this.completedStep = completedStep;
        this.completedBy = completedBy;
    }

    public ApprovalWorkflow getWorkflow() {
        return workflow;
    }

    public ApprovalStep getCompletedStep() {
        return completedStep;
    }

    public UUID getCompletedBy() {
        return completedBy;
    }

}