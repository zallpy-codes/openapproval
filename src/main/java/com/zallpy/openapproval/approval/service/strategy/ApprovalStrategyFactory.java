package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.enums.ApprovalStrategy;

/**
 * Factory responsible for resolving the appropriate
 * {@link ApprovalStrategyExecutor} for a workflow.
 *
 * <p>
 * The factory decouples the execution engine from the
 * concrete strategy implementations.
 * </p>
 *
 * <p>
 * New approval strategies can be introduced without
 * modifying the execution engine.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalStrategyFactory {

    /**
     * Resolves the executor supporting the supplied strategy.
     *
     * @param strategy approval strategy
     * @return matching strategy executor
     */
    ApprovalStrategyExecutor getExecutor(
            ApprovalStrategy strategy);

}