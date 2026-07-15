package com.zallpy.openapproval.approval.service.strategy;

import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Default implementation of {@link ApprovalStrategyFactory}.
 *
 * <p>
 * Discovers all available {@link ApprovalStrategyExecutor}
 * implementations from the Spring container and resolves the
 * appropriate executor based on the requested
 * {@link ApprovalStrategy}.
 * </p>
 *
 * <p>
 * This implementation follows the Open/Closed Principle.
 * New strategy executors can be added without modifying
 * this factory.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApprovalStrategyFactoryImpl
        implements ApprovalStrategyFactory {

    /**
     * Registered strategy executors.
     */
    private final List<ApprovalStrategyExecutor> executors;

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalStrategyExecutor getExecutor(
            final ApprovalStrategy strategy) {

        if (strategy == null) {
            throw new ApprovalValidationException(
                    "Approval strategy is required.");
        }

        return executors.stream()
                .filter(executor -> executor.supports(strategy))
                .findFirst()
                .orElseThrow(() ->
                        new ApprovalValidationException(
                                "No approval strategy executor found for strategy ["
                                        + strategy + "]."));
    }

}