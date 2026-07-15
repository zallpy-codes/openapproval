package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zallpy.openapproval.approval.event.ApprovalDomainEventPublisher;
import com.zallpy.openapproval.approval.event.ApprovalWorkflowCompletedEvent;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * Default implementation of {@link ApprovalCompletionService}.
 *
 * <p>
 * Handles terminal workflow completion.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalCompletionServiceImpl
        implements ApprovalCompletionService {

    private final ApprovalDomainEventPublisher eventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void complete(
            final ApprovalWorkflow workflow,
            final UUID completedBy) {

        workflow.complete(completedBy);

        ApprovalRequest request = workflow.getApprovalRequest();

        if (request != null) {

            request.complete(completedBy);

            eventPublisher.publish(
                    new ApprovalWorkflowCompletedEvent(
                            workflow.getId(),
                            request.getId(),
                            completedBy));
        }
    }

}