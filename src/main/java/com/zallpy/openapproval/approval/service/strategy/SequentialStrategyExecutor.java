package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Sequential approval strategy executor.
 *
 * <p>
 * Executes approval workflows using a sequential strategy where
 * only one approval step is active at any given time.
 * </p>
 *
 * <p>
 * When the current step completes, the next pending step is
 * activated. If no pending steps remain, the workflow is left for
 * the workflow progressor to complete.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class SequentialStrategyExecutor
        implements ApprovalStrategyExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategy getStrategy() {
        return ApprovalStrategy.SEQUENTIAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(
            final ApprovalWorkflow workflow,
            final ApprovalStep completedStep) {

        if (workflow == null || completedStep == null) {
            return;
        }

        /*
         * Refresh workflow statistics after the completed step.
         */
        workflow.recalculateStatistics();

        /*
         * Activate the next pending step if one exists.
         */
        if (workflow.hasNextStep()) {
            workflow.activateNextStep();
        }
    }

}