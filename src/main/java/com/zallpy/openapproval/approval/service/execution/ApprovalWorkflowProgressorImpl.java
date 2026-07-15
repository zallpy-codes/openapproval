package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zallpy.openapproval.approval.service.strategy.ApprovalStrategyExecutor;
import com.zallpy.openapproval.approval.service.strategy.ApprovalStrategyFactory;

import java.util.UUID;

/**
 * Default implementation of {@link ApprovalWorkflowProgressor}.
 *
 * <p>
 * Coordinates workflow progression after an approval decision.
 * </p>
 *
 * <p>
 * Strategy-based progression will be introduced in the next batch.
 * For now, this implementation preserves the existing workflow
 * progression behavior while adopting the new method signature.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalWorkflowProgressorImpl
        implements ApprovalWorkflowProgressor {

    /**
     * Workflow completion service.
     */
    private final ApprovalCompletionService completionService;

    /**
     * Approval strategy factory.
     */
    private final ApprovalStrategyFactory strategyFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void progress(
            final ApprovalWorkflow workflow,
            final ApprovalStep completedStep,
            final UUID completedBy) {

        if (workflow == null) {
            return;
        }

        workflow.recalculateStatistics();

        /*
         * Delegate workflow progression to the configured strategy.
         */
        ApprovalStrategyExecutor strategyExecutor = strategyFactory.getExecutor(
                workflow.getApprovalRequest()
                        .getApprovalPolicy()
                        .getApprovalStrategy());

        strategyExecutor.execute(
                workflow,
                completedStep);

        /*
         * Refresh runtime statistics after strategy execution.
         */
        workflow.recalculateStatistics();

        /*
         * Complete workflow if all completion conditions are satisfied.
         */
        if (workflow.canComplete()) {

            completionService.complete(
                    workflow,
                    completedBy);
        }
    }

}