package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;

import java.util.List;
import java.util.UUID;

/**
 * Builds a complete approval timeline from
 * approval audit records.
 *
 * <p>
 * The timeline builder transforms immutable
 * {@link ApprovalAudit} entities into a
 * presentation-friendly {@link ApprovalTimeline}
 * suitable for REST APIs, dashboards and workflow
 * history screens.
 * </p>
 *
 * <p>
 * Implementations are responsible for:
 * <ul>
 * <li>Ordering timeline events chronologically</li>
 * <li>Computing workflow duration</li>
 * <li>Determining the current workflow status</li>
 * <li>Determining the current stage status</li>
 * <li>Building timeline items</li>
 * <li>Populating workflow statistics</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimelineBuilder {

    /**
     * Builds a complete timeline for the supplied
     * approval workflow using its audit history.
     *
     * @param workflow     approval workflow
     * @param auditHistory ordered audit history
     *
     * @return completed approval timeline
     */
    ApprovalTimeline build(
            ApprovalWorkflow workflow,
            List<ApprovalAudit> auditHistory);

    /**
     * Builds a timeline for the supplied workflow.
     *
     * <p>
     * Implementations are expected to retrieve the
     * audit history internally.
     * </p>
     *
     * @param workflowId workflow identifier
     *
     * @return approval timeline
     */
    ApprovalTimeline build(
            UUID workflowId);

}