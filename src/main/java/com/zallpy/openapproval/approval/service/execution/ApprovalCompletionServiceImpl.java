package com.zallpy.openapproval.approval.service.execution;

import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Transactional
public class ApprovalCompletionServiceImpl
        implements ApprovalCompletionService {

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
        }
    }

}