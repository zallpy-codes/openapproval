package com.zallpy.openapproval.approval.service.timeline;

import com.zallpy.openapproval.approval.timeline.ApprovalTimeline;
import com.zallpy.openapproval.approval.timeline.ApprovalTimelineBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * Default implementation of {@link ApprovalTimelineService}.
 *
 * <p>
 * This service acts as the application-layer entry point
 * for retrieving approval workflow timelines.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Validate incoming requests</li>
 *     <li>Delegate timeline construction to
 *         {@link ApprovalTimelineBuilder}</li>
 *     <li>Provide a stable API for controllers and
 *         other application services</li>
 * </ul>
 * </p>
 *
 * <p>
 * Business logic related to timeline construction is
 * intentionally delegated to the timeline builder.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalTimelineServiceImpl
        implements ApprovalTimelineService {

    /**
     * Timeline builder.
     */
    private final ApprovalTimelineBuilder timelineBuilder;

    /**
     * Creates a new timeline service.
     *
     * @param timelineBuilder timeline builder
     */
    public ApprovalTimelineServiceImpl(
            final ApprovalTimelineBuilder timelineBuilder) {

        this.timelineBuilder = Objects.requireNonNull(
                timelineBuilder,
                "timelineBuilder must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalTimeline getTimeline(
            final UUID workflowId) {

        Objects.requireNonNull(
                workflowId,
                "workflowId must not be null");

        return timelineBuilder.build(workflowId);
    }

}