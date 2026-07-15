package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStrategy;

/**
 * Contract for all approval execution strategies.
 *
 * <p>
 * An approval strategy determines how an approval workflow progresses
 * after an approval decision has been made.
 * </p>
 *
 * <p>
 * Implementations encapsulate the business rules for different
 * execution models such as:
 * </p>
 *
 * <ul>
 *     <li>Sequential</li>
 *     <li>Parallel</li>
 *     <li>First Response Wins</li>
 *     <li>Quorum</li>
 *     <li>Unanimous</li>
 * </ul>
 *
 * <p>
 * Each strategy implementation is responsible only for progressing
 * workflow execution according to its own rules.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalStrategyExecutor {

    /**
     * Returns the approval strategy supported by this executor.
     *
     * @return supported approval strategy
     */
    ApprovalStrategy getStrategy();

    /**
     * Executes workflow progression after the supplied approval step
     * has completed.
     *
     * <p>
     * Implementations may activate additional approval steps,
     * complete the workflow, reject the workflow or perform
     * other strategy-specific progression logic.
     * </p>
     *
     * @param workflow approval workflow
     * @param completedStep completed approval step
     */
    void execute(
            ApprovalWorkflow workflow,
            ApprovalStep completedStep);

    /**
     * Determines whether this executor supports the supplied strategy.
     *
     * @param strategy approval strategy
     * @return {@code true} if supported
     */
    default boolean supports(
            final ApprovalStrategy strategy) {

        return getStrategy() == strategy;
    }

}