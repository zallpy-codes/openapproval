package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Quorum approval strategy executor.
 *
 * <p>
 * Executes approval workflows using the quorum strategy.
 * </p>
 *
 * <p>
 * A workflow configured with the quorum strategy completes
 * successfully once the required number of approvals has
 * been obtained. Remaining pending approval steps may be
 * closed without requiring further action.
 * </p>
 *
 * <p>
 * This implementation provides the execution infrastructure.
 * Quorum evaluation will be introduced in a later milestone.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class QuorumStrategyExecutor
        implements ApprovalStrategyExecutor {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategy getStrategy() {
        return ApprovalStrategy.QUORUM;
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
         * Refresh runtime statistics.
         */
        workflow.recalculateStatistics();

        /*
         * Quorum evaluation will be implemented in the
         * advanced strategy milestone.
         *
         * Future responsibilities include:
         *
         * - Calculate approval quorum
         * - Determine whether quorum has been reached
         * - Complete the workflow early when appropriate
         * - Close remaining pending approval steps
         */
    }

}