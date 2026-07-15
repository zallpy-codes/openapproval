package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalWorkflowProgressor}.
 *
 * <p>
 * Advances approval workflows after every decision.
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

    private final ApprovalCompletionService completionService;

    @Override
    public void progress(
            final ApprovalWorkflow workflow,
            final UUID completedBy) {

        workflow.recalculateStatistics();

        if (workflow.canComplete()) {
            completionService.complete(
                    workflow,
                    completedBy);
            return;
        }

        if (workflow.hasNextStep()) {
            workflow.activateNextStep();
        }
    }

}