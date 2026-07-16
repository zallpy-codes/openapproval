package com.zallpy.openapproval.approval.service.timeline;

import com.zallpy.openapproval.approval.timeline.ApprovalTimeline;

import java.util.UUID;

/**
 * Application service responsible for providing
 * approval workflow timelines.
 *
 * <p>
 * This service exposes a simplified API for retrieving
 * a complete chronological history of an approval workflow.
 * It delegates timeline construction to the
 * {@code ApprovalTimelineBuilder}.
 * </p>
 *
 * <p>
 * Responsibilities include:
 * <ul>
 *     <li>Retrieving workflow timelines</li>
 *     <li>Providing a stable application API</li>
 *     <li>Serving REST controllers and other application services</li>
 * </ul>
 * </p>
 *
 * <p>
 * Timeline construction itself is delegated to the
 * timeline builder implementation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimelineService {

    /**
     * Retrieves the complete timeline for an
     * approval workflow.
     *
     * @param workflowId approval workflow identifier
     *
     * @return approval timeline
     */
    ApprovalTimeline getTimeline(
            UUID workflowId);

}