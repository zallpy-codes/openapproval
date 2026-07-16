package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.enums.ApprovalStageStatus;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the complete execution timeline of an
 * approval workflow.
 *
 * <p>
 * This object is a read-only projection built from
 * {@link com.zallpy.openapproval.approval.entity.ApprovalWorkflow}
 * and its immutable audit history.
 * </p>
 *
 * <p>
 * It is intended for:
 * <ul>
 * <li>Timeline APIs</li>
 * <li>Workflow dashboards</li>
 * <li>Audit history screens</li>
 * <li>Execution monitoring</li>
 * <li>Reporting</li>
 * </ul>
 * </p>
 *
 * <p>
 * The timeline contains workflow metadata together
 * with an ordered collection of
 * {@link ApprovalTimelineItem} objects describing
 * every significant business event.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalTimeline
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Workflow identifier.
     */
    private UUID workflowId;

    /**
     * Approval request identifier.
     */
    private UUID requestId;

    /**
     * Workflow reference.
     */
    private String workflowReference;

    /**
     * Workflow start timestamp.
     */
    private LocalDateTime startedAt;

    /**
     * Workflow completion timestamp.
     */
    private LocalDateTime completedAt;

    /**
     * Total workflow execution duration.
     */
    private Duration duration;

    /**
     * Indicates whether the workflow has completed.
     */
    private boolean completed;

    /**
     * Indicates whether the workflow was cancelled.
     */
    private boolean cancelled;

    /**
     * Indicates whether the workflow was rejected.
     */
    private boolean rejected;

    /**
     * Current lifecycle status of the approval
     * workflow.
     *
     * <p>
     * This represents the overall business state
     * of the approval request.
     * </p>
     */
    private ApprovalStatus currentStatus;

    /**
     * Current status of the active approval step.
     *
     * <p>
     * This represents the runtime status of the
     * current approval step executing within the
     * approval workflow.
     * </p>
     */
    private ApprovalStatus currentStage;

    /**
     * Identifier of the current approval step.
     */
    private UUID currentStepId;

    /**
     * Current approval stage order.
     */
    private Integer currentStageOrder;

    /**
     * Total approval steps.
     */
    private Integer totalSteps = 0;

    /**
     * Pending approval steps.
     */
    private Integer pendingSteps = 0;

    /**
     * Approved approval steps.
     */
    private Integer approvedSteps = 0;

    /**
     * Rejected approval steps.
     */
    private Integer rejectedSteps = 0;

    /**
     * Delegated approval steps.
     */
    private Integer delegatedSteps = 0;

    /**
     * Escalated approval steps.
     */
    private Integer escalatedSteps = 0;

    /**
     * Total timeline events.
     */
    private Integer totalEvents = 0;

    /**
     * Ordered timeline items.
     */
    private final List<ApprovalTimelineItem> items = new ArrayList<>();

    /**
     * Default constructor.
     */
    public ApprovalTimeline() {
    }

    /**
     * Populates workflow metadata from the supplied
     * current approval step.
     *
     * @param currentStep current approval step
     */
    public void setCurrentStep(
            final ApprovalStep currentStep) {

        if (currentStep == null) {
            this.currentStepId = null;
            this.currentStage = null;
            this.currentStageOrder = null;
            return;
        }

        this.currentStepId = currentStep.getId();
        this.currentStage = currentStep.getStatus();
        this.currentStageOrder = currentStep.getStageOrder();
    }

    /**
     * Adds a timeline item.
     *
     * @param item timeline item
     */
    public void addItem(
            final ApprovalTimelineItem item) {

        if (item != null) {
            this.items.add(item);
            this.totalEvents = this.items.size();
        }
    }

    /**
     * Adds multiple timeline items.
     *
     * @param items timeline items
     */
    public void addItems(
            final List<ApprovalTimelineItem> items) {

        if (items == null || items.isEmpty()) {
            return;
        }

        this.items.addAll(items);
        this.totalEvents = this.items.size();
    }

    /**
     * Removes every timeline item.
     */
    public void clearItems() {

        this.items.clear();
        this.totalEvents = 0;
    }

    /**
     * Returns whether the timeline contains
     * any items.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns the number of timeline items.
     *
     * @return timeline size
     */
    public int size() {
        return items.size();
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(final UUID workflowId) {
        this.workflowId = workflowId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(final UUID requestId) {
        this.requestId = requestId;
    }

    public String getWorkflowReference() {
        return workflowReference;
    }

    public void setWorkflowReference(
            final String workflowReference) {
        this.workflowReference = workflowReference;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(
            final LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(
            final LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(
            final Duration duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(
            final boolean completed) {
        this.completed = completed;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(
            final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(
            final boolean rejected) {
        this.rejected = rejected;
    }

    public ApprovalStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(
            final ApprovalStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ApprovalStatus getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(
            final ApprovalStatus currentStage) {
        this.currentStage = currentStage;
    }

    public UUID getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(
            final UUID currentStepId) {
        this.currentStepId = currentStepId;
    }

    public Integer getCurrentStageOrder() {
        return currentStageOrder;
    }

    public void setCurrentStageOrder(
            final Integer currentStageOrder) {
        this.currentStageOrder = currentStageOrder;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(
            final Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Integer getPendingSteps() {
        return pendingSteps;
    }

    public void setPendingSteps(
            final Integer pendingSteps) {
        this.pendingSteps = pendingSteps;
    }

    public Integer getApprovedSteps() {
        return approvedSteps;
    }

    public void setApprovedSteps(
            final Integer approvedSteps) {
        this.approvedSteps = approvedSteps;
    }

    public Integer getRejectedSteps() {
        return rejectedSteps;
    }

    public void setRejectedSteps(
            final Integer rejectedSteps) {
        this.rejectedSteps = rejectedSteps;
    }

    public Integer getDelegatedSteps() {
        return delegatedSteps;
    }

    public void setDelegatedSteps(
            final Integer delegatedSteps) {
        this.delegatedSteps = delegatedSteps;
    }

    public Integer getEscalatedSteps() {
        return escalatedSteps;
    }

    public void setEscalatedSteps(
            final Integer escalatedSteps) {
        this.escalatedSteps = escalatedSteps;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(
            final Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    public List<ApprovalTimelineItem> getItems() {
        return items;
    }

}