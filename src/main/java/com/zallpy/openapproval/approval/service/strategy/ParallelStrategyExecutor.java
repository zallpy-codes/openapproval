package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Parallel approval strategy executor.
 *
 * <p>
 * Executes approval workflows using the parallel strategy.
 * </p>
 *
 * <p>
 * Multiple approval steps may be active simultaneously.
 * Completing one approval step does not activate another step.
 * The workflow progresses only after all active parallel steps
 * have reached a terminal state.
 * </p>
 *
 * <p>
 * The workflow completion decision is delegated to the
 * workflow progressor.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ParallelStrategyExecutor
        implements ApprovalStrategyExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategy getStrategy() {
        return ApprovalStrategy.PARALLEL;
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
         * Refresh workflow statistics.
         */
        workflow.recalculateStatistics();

        /*
         * Parallel execution does not activate additional steps.
         *
         * All eligible approval steps should already be active.
         *
         * Workflow completion is handled by the
         * ApprovalWorkflowProgressor.
         */
    }

}