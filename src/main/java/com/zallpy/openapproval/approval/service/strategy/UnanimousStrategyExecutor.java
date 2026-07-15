package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Unanimous approval strategy executor.
 *
 * <p>
 * Executes approval workflows using the unanimous strategy.
 * </p>
 *
 * <p>
 * Under this strategy, every assigned approver must approve
 * before the workflow can be completed successfully.
 * A single rejection immediately causes workflow rejection.
 * </p>
 *
 * <p>
 * This implementation establishes the execution infrastructure.
 * The unanimous decision algorithm will be implemented during
 * the advanced strategy milestone.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class UnanimousStrategyExecutor
        implements ApprovalStrategyExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategy getStrategy() {
        return ApprovalStrategy.UNANIMOUS;
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
         * Unanimous evaluation will be implemented in the
         * advanced strategy milestone.
         *
         * Future responsibilities include:
         *
         * - Verify that every approval step has been approved.
         * - Detect any rejection and terminate the workflow.
         * - Complete the workflow when all approvals are received.
         */
    }

}