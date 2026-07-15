package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the runtime execution of an approval request.
 *
 * <p>
 * An {@code ApprovalWorkflow} is created when an
 * {@link ApprovalRequest} is submitted to the approval engine.
 * It is responsible for tracking workflow execution,
 * approval progress and runtime statistics until completion.
 * </p>
 *
 * <p>
 * A workflow owns one or more {@link ApprovalStep}s which are
 * executed according to the configured {@link ApprovalPolicy}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_workflow", indexes = {
        @Index(name = "idx_workflow_reference", columnList = "workflow_reference"),
        @Index(name = "idx_workflow_status", columnList = "status"),
        @Index(name = "idx_workflow_started_at", columnList = "started_at")
})
public class ApprovalWorkflow extends BaseEntity {

    /**
     * Business approval request.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_request_id", nullable = false)
    private ApprovalRequest approvalRequest;

    /**
     * Workflow reference.
     */
    @Column(name = "workflow_reference",
            nullable = false,
            unique = true,
            length = 100)
    private String workflowReference;

    /**
     * Current workflow status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            nullable = false,
            length = 50)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    /**
     * Workflow start timestamp.
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * Workflow completion timestamp.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * User that completed the workflow.
     */
    @Column(name = "completed_by")
    private UUID completedBy;

    /**
     * Indicates whether this workflow has completed.
     */
    @Column(name = "completed",
            nullable = false)
    private boolean completed = false;

    /**
     * Indicates whether this workflow has been cancelled.
     */
    @Column(name = "cancelled",
            nullable = false)
    private boolean cancelled = false;

    /**
     * Workflow cancellation timestamp.
     */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    /**
     * User that cancelled the workflow.
     */
    @Column(name = "cancelled_by")
    private UUID cancelledBy;

    /**
     * Cancellation reason.
     */
    @Column(name = "cancellation_reason",
            length = 2000)
    private String cancellationReason;

    /**
     * Indicates whether the workflow has been rejected.
     */
    @Column(name = "rejected",
            nullable = false)
    private boolean rejected = false;

    /**
     * Workflow rejection timestamp.
     */
    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    /**
     * User that rejected the workflow.
     */
    @Column(name = "rejected_by")
    private UUID rejectedBy;

    /**
     * Rejection reason.
     */
    @Column(name = "rejection_reason",
            length = 2000)
    private String rejectionReason;

    /**
     * Total number of approval steps.
     */
    @Column(name = "total_steps",
            nullable = false)
    private Integer totalSteps = 0;

    /**
     * Number of pending approval steps.
     */
    @Column(name = "pending_steps",
            nullable = false)
    private Integer pendingSteps = 0;

    /**
     * Number of approved steps.
     */
    @Column(name = "approved_steps",
            nullable = false)
    private Integer approvedSteps = 0;

    /**
     * Number of rejected steps.
     */
    @Column(name = "rejected_steps",
            nullable = false)
    private Integer rejectedSteps = 0;

    /**
     * Number of delegated steps.
     */
    @Column(name = "delegated_steps",
            nullable = false)
    private Integer delegatedSteps = 0;

    /**
     * Number of escalated steps.
     */
    @Column(name = "escalated_steps",
            nullable = false)
    private Integer escalatedSteps = 0;

    /**
     * Runtime approval steps.
     */
    @OneToMany(
            mappedBy = "approvalWorkflow",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<ApprovalStep> approvalSteps = new LinkedHashSet<>();

        /* ==========================================================
     * Getters
     * ==========================================================
     */

    /**
     * Returns the approval request.
     *
     * @return approval request
     */
    public ApprovalRequest getApprovalRequest() {
        return approvalRequest;
    }

    /**
     * Returns the workflow reference.
     *
     * @return workflow reference
     */
    public String getWorkflowReference() {
        return workflowReference;
    }

    /**
     * Returns the workflow status.
     *
     * @return workflow status
     */
    public ApprovalStatus getStatus() {
        return status;
    }

    /**
     * Returns the workflow start time.
     *
     * @return started at
     */
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    /**
     * Returns the workflow completion time.
     *
     * @return completed at
     */
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    /**
     * Returns the completion user.
     *
     * @return completed by
     */
    public UUID getCompletedBy() {
        return completedBy;
    }

    /**
     * Returns whether the workflow is completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns whether the workflow is cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns whether the workflow is rejected.
     *
     * @return true if rejected
     */
    public boolean isRejected() {
        return rejected;
    }

    /**
     * Returns the approval steps.
     *
     * @return approval steps
     */
    public Set<ApprovalStep> getApprovalSteps() {
        return approvalSteps;
    }

    /**
     * Returns the total number of steps.
     *
     * @return total steps
     */
    public Integer getTotalSteps() {
        return totalSteps;
    }

    /**
     * Returns the number of pending steps.
     *
     * @return pending steps
     */
    public Integer getPendingSteps() {
        return pendingSteps;
    }

    /**
     * Returns the number of approved steps.
     *
     * @return approved steps
     */
    public Integer getApprovedSteps() {
        return approvedSteps;
    }

    /**
     * Returns the number of rejected steps.
     *
     * @return rejected steps
     */
    public Integer getRejectedSteps() {
        return rejectedSteps;
    }

    /**
     * Returns the number of delegated steps.
     *
     * @return delegated steps
     */
    public Integer getDelegatedSteps() {
        return delegatedSteps;
    }

    /**
     * Returns the number of escalated steps.
     *
     * @return escalated steps
     */
    public Integer getEscalatedSteps() {
        return escalatedSteps;
    }

    /* ==========================================================
     * Controlled Setters
     * ==========================================================
     */

    /**
     * Assigns the approval request while maintaining the
     * bidirectional relationship.
     *
     * @param approvalRequest approval request
     */
    public void setApprovalRequest(final ApprovalRequest approvalRequest) {

        this.approvalRequest = approvalRequest;

        if (approvalRequest != null
                && approvalRequest.getWorkflow() != this) {
            approvalRequest.setWorkflow(this);
        }
    }

    /**
     * Updates the workflow status.
     *
     * @param status workflow status
     */
    public void setStatus(final ApprovalStatus status) {

        if (status != null) {
            this.status = status;
        }
    }

    /**
     * Updates the workflow reference.
     *
     * @param workflowReference workflow reference
     */
    public void setWorkflowReference(final String workflowReference) {
        this.workflowReference = workflowReference;
    }

    /* ==========================================================
     * Relationship Management
     * ==========================================================
     */

    /**
     * Adds an approval step.
     *
     * @param approvalStep approval step
     */
    public void addApprovalStep(final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        if (this.approvalSteps.contains(approvalStep)) {
            return;
        }

        approvalStep.setApprovalWorkflow(this);
        this.approvalSteps.add(approvalStep);

        recalculateStatistics();
    }

    /**
     * Removes an approval step.
     *
     * @param approvalStep approval step
     */
    public void removeApprovalStep(final ApprovalStep approvalStep) {

        if (approvalStep == null) {
            return;
        }

        if (!this.approvalSteps.contains(approvalStep)) {
            return;
        }

        approvalStep.setApprovalWorkflow(null);
        this.approvalSteps.remove(approvalStep);

        recalculateStatistics();
    }

    /**
     * Removes all approval steps.
     */
    public void clearApprovalSteps() {

        this.approvalSteps.forEach(step ->
                step.setApprovalWorkflow(null));

        this.approvalSteps.clear();

        recalculateStatistics();
    }

        /* ==========================================================
     * Lifecycle
     * ==========================================================
     */

    /**
     * Starts workflow execution.
     */
    public void start() {

        if (this.startedAt == null) {
            this.startedAt = LocalDateTime.now();
        }

        this.status = ApprovalStatus.IN_PROGRESS;

        activateNextStep();

        recalculateStatistics();
    }

    /**
     * Completes the workflow.
     *
     * @param completedBy user completing the workflow
     */
    public void complete(final UUID completedBy) {

        this.completed = true;
        this.completedAt = LocalDateTime.now();
        this.completedBy = completedBy;
        this.status = ApprovalStatus.APPROVED;

        approvalSteps.forEach(ApprovalStep::deactivate);

        recalculateStatistics();
    }

    /**
     * Rejects the workflow.
     *
     * @param rejectedBy rejecting user
     * @param reason rejection reason
     */
    public void reject(final UUID rejectedBy,
                       final String reason) {

        this.rejected = true;
        this.rejectedBy = rejectedBy;
        this.rejectedAt = LocalDateTime.now();
        this.rejectionReason = reason;
        this.status = ApprovalStatus.REJECTED;

        approvalSteps.forEach(ApprovalStep::deactivate);

        recalculateStatistics();
    }

    /**
     * Cancels the workflow.
     *
     * @param cancelledBy cancelling user
     * @param reason cancellation reason
     */
    public void cancel(final UUID cancelledBy,
                       final String reason) {

        this.cancelled = true;
        this.cancelledBy = cancelledBy;
        this.cancelledAt = LocalDateTime.now();
        this.cancellationReason = reason;
        this.status = ApprovalStatus.CANCELLED;

        approvalSteps.forEach(ApprovalStep::deactivate);

        recalculateStatistics();
    }

    /* ==========================================================
     * Runtime Execution
     * ==========================================================
     */

    /**
     * Returns the current active approval step.
     *
     * @return current approval step or {@code null}
     */
    public ApprovalStep getCurrentStep() {

        return approvalSteps.stream()
                .filter(ApprovalStep::isCurrentStep)
                .findFirst()
                .orElse(null);
    }

    /**
     * Activates the next pending approval step.
     *
     * @return activated approval step or {@code null}
     */
    public ApprovalStep activateNextStep() {

        approvalSteps.forEach(ApprovalStep::deactivate);

        ApprovalStep nextStep = approvalSteps.stream()
                .filter(ApprovalStep::isPending)
                .sorted((left, right) ->
                        Integer.compare(
                                left.getStageOrder(),
                                right.getStageOrder()))
                .findFirst()
                .orElse(null);

        if (nextStep != null) {
            nextStep.activate();
        }

        recalculateStatistics();

        return nextStep;
    }

    /**
     * Determines whether a current active step exists.
     *
     * @return true if a current step exists
     */
    public boolean hasCurrentStep() {
        return getCurrentStep() != null;
    }

    /**
     * Determines whether pending approval steps exist.
     *
     * @return true if pending steps exist
     */
    public boolean hasPendingSteps() {

        return approvalSteps.stream()
                .anyMatch(ApprovalStep::isPending);
    }

    /**
     * Determines whether all approval steps
     * have completed execution.
     *
     * @return true if all steps are completed
     */
    public boolean allStepsCompleted() {

        return approvalSteps.stream()
                .allMatch(ApprovalStep::isCompleted);
    }

        /* ==========================================================
     * Business Rules
     * ==========================================================
     */

    /**
     * Determines whether this workflow can be executed.
     *
     * @return {@code true} if executable
     */
    public boolean isExecutable() {

        return approvalRequest != null
                && !completed
                && !cancelled
                && !rejected
                && !approvalSteps.isEmpty();
    }

    /**
     * Determines whether this workflow is currently running.
     *
     * @return {@code true} if running
     */
    public boolean isRunning() {

        return startedAt != null
                && !completed
                && !cancelled
                && !rejected;
    }

    /**
     * Determines whether this workflow has reached
     * a terminal state.
     *
     * @return {@code true} if terminal
     */
    public boolean isTerminal() {
        return completed || cancelled || rejected;
    }

    /**
     * Determines whether this workflow contains no approval steps.
     *
     * @return {@code true} if empty
     */
    public boolean isEmpty() {
        return approvalSteps.isEmpty();
    }

    /**
     * Determines whether workflow execution can advance.
     *
     * @return {@code true} if another step can be activated
     */
    public boolean canAdvance() {

        return isRunning()
                && hasPendingSteps();
    }

    /**
     * Determines whether the workflow can be completed.
     *
     * @return {@code true} if completion is allowed
     */
    public boolean canComplete() {

        return !approvalSteps.isEmpty()
                && allStepsCompleted()
                && rejectedSteps == 0;
    }

    /**
     * Determines whether the workflow belongs to
     * the supplied approval request.
     *
     * @param approvalRequest approval request
     * @return {@code true} if matching
     */
    public boolean belongsTo(final ApprovalRequest approvalRequest) {

        return approvalRequest != null
                && approvalRequest.equals(this.approvalRequest);
    }

    /* ==========================================================
     * Internal Helpers
     * ==========================================================
     */

    /**
     * Recalculates workflow statistics from the current
     * approval step collection.
     *
     * <p>
     * This method is the single source of truth for runtime
     * workflow statistics.
     * </p>
     */
    private void recalculateStatistics() {

        this.totalSteps = approvalSteps.size();

        this.pendingSteps = (int) approvalSteps.stream()
                .filter(ApprovalStep::isPending)
                .count();

        this.approvedSteps = (int) approvalSteps.stream()
                .filter(ApprovalStep::isApproved)
                .count();

        this.rejectedSteps = (int) approvalSteps.stream()
                .filter(ApprovalStep::isRejected)
                .count();

        this.delegatedSteps = (int) approvalSteps.stream()
                .filter(ApprovalStep::isDelegated)
                .count();

        this.escalatedSteps = (int) approvalSteps.stream()
                .filter(ApprovalStep::isEscalated)
                .count();
    }

}
