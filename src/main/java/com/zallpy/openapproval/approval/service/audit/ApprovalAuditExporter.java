package com.zallpy.openapproval.approval.service.audit;

import java.util.UUID;

/**
 * Exports approval audit history.
 *
 * <p>
 * Future implementations may support PDF,
 * Excel, CSV and JSON export.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalAuditExporter {

    /**
     * Exports workflow audit history.
     *
     * @param workflowId workflow identifier
     * @return exported content
     */
    byte[] exportWorkflowHistory(
            UUID workflowId);

}