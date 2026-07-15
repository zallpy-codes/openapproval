package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * First Response approval strategy executor.
 *
 * <p>
 * Executes approval workflows using the First Response strategy.
 * </p>
 *
 * <p>
 * Under this strategy, the first approver to make a decision
 * determines the outcome of the approval stage. Remaining
 * pending approval steps become obsolete once the first
 * decision has been recorded.
 * </p>
 *
 * <p>
 * This implementation prepares the workflow for completion.
 * The actual workflow completion remains the responsibility
 * of the workflow progressor.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class FirstResponseStrategyExecutor
        implements ApprovalStrategyExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategy getStrategy() {
        return ApprovalStrategy.FIRST_RESPONSE;
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
         * The first completed approval step determines
         * the outcome of this approval stage.
         *
         * Remaining pending approval steps will be
         * cancelled/closed by the execution engine in
         * a later milestone.
         */
    }

}