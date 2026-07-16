package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.service.execution.ApprovalWorkflowProgressor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalTimeoutService}.
 *
 * <p>
 * Coordinates timeout processing for approval steps.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Validate timeout policy</li>
 * <li>Execute timeout</li>
 * <li>Publish timeout event</li>
 * <li>Progress the approval workflow</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalTimeoutServiceImpl
        implements ApprovalTimeoutService {

    /**
     * Timeout policy resolver.
     */
    private final ApprovalTimeoutPolicyResolver policyResolver;

    /**
     * Timeout publisher.
     */
    private final ApprovalTimeoutPublisher timeoutPublisher;

    /**
     * Workflow progressor.
     */
    private final ApprovalWorkflowProgressor workflowProgressor;

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeout(
            final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        if (!policyResolver.shouldTimeout(
                approvalStep)) {
            return;
        }

        approvalStep.timeout();

        timeoutPublisher.publishTimeout(
                approvalStep);

        ApprovalWorkflow workflow = approvalStep.getApprovalWorkflow();

        if (workflow != null) {

            workflowProgressor.progress(
                    workflow,
                    approvalStep,
                    approvalStep.getApproverId());
        }
    }

}