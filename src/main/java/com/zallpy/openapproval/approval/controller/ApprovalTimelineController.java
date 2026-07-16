package com.zallpy.openapproval.approval.controller;

import com.zallpy.openapproval.approval.service.timeline.ApprovalTimelineService;
import com.zallpy.openapproval.approval.timeline.ApprovalTimeline;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing approval workflow timeline APIs.
 *
 * <p>
 * Provides read-only endpoints for retrieving the
 * chronological execution history of approval workflows.
 * </p>
 *
 * <p>
 * Timeline data is intended for:
 * <ul>
 * <li>Workflow history screens</li>
 * <li>Approval dashboards</li>
 * <li>Administrative investigation</li>
 * <li>Audit visualization</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/approval/workflows")
@Validated
@Tag(name = "Approval Timeline", description = "Approval workflow timeline endpoints")
public class ApprovalTimelineController {

    /**
     * Timeline application service.
     */
    private final ApprovalTimelineService timelineService;

    /**
     * Creates a new controller.
     *
     * @param timelineService timeline service
     */
    public ApprovalTimelineController(
            final ApprovalTimelineService timelineService) {

        this.timelineService = timelineService;
    }

    /**
     * Retrieves the complete timeline for an approval workflow.
     *
     * @param workflowId workflow identifier
     *
     * @return approval timeline
     */
    @GetMapping("/{workflowId}/timeline")
    @Operation(summary = "Retrieve approval workflow timeline", description = """
            Returns the complete chronological
            execution history of an approval workflow,
            including workflow metadata,
            runtime statistics and audit events.
            """)
    public ResponseEntity<ApprovalTimeline> getTimeline(
            @Parameter(description = "Approval workflow identifier", required = true) @PathVariable @NotNull final UUID workflowId) {

        ApprovalTimeline timeline = timelineService.getTimeline(workflowId);

        return ResponseEntity.ok(timeline);
    }

}