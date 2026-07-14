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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents the runtime execution of an approval process.
 *
 * <p>
 * Every {@link ApprovalRequest} owns exactly one ApprovalWorkflow.
 * The workflow manages the execution of approval steps from submission
 * until final approval, rejection, cancellation, or recall.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_workflow", indexes = {
        @Index(name = "idx_workflow_status", columnList = "status"),
        @Index(name = "idx_workflow_current_stage", columnList = "current_stage_order"),
        @Index(name = "idx_workflow_started_at", columnList = "started_at")
})
public class ApprovalWorkflow extends BaseEntity {

    /**
     * Parent approval request.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_request_id", nullable = false, unique = true)
    private ApprovalRequest approvalRequest;

    /**
     * Runtime approval steps.
     */
    @OneToMany(mappedBy = "approvalWorkflow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ApprovalStep> approvalSteps = new LinkedHashSet<>();

    /**
     * Workflow status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    /**
     * Current stage number.
     */
    @Column(name = "current_stage_order")
    private Integer currentStageOrder = 1;

    /**
     * Workflow start time.
     */
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    /**
     * Workflow completion time.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * User that completed the workflow.
     */
    @Column(name = "completed_by")
    private UUID completedBy;

    /**
     * Indicates whether the workflow has completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    /**
     * Indicates whether the workflow has been cancelled.
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;

    /**
     * Date and time the workflow was cancelled.
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
    @Column(name = "cancellation_reason", length = 2000)
    private String cancellationReason;

    /**
     * Indicates whether the workflow has been recalled.
     */
    @Column(name = "recalled", nullable = false)
    private boolean recalled = false;

    /**
     * Date and time the workflow was recalled.
     */
    @Column(name = "recalled_at")
    private LocalDateTime recalledAt;

    /**
     * User that recalled the workflow.
     */
    @Column(name = "recalled_by")
    private UUID recalledBy;

    /**
     * Recall reason.
     */
    @Column(name = "recall_reason", length = 2000)
    private String recallReason;

    /**
     * Workflow due date.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Indicates whether the workflow has timed out.
     */
    @Column(name = "timed_out", nullable = false)
    private boolean timedOut = false;

    /**
     * Date and time the workflow timed out.
     */
    @Column(name = "timed_out_at")
    private LocalDateTime timedOutAt;

    /**
     * Number of completed approval steps.
     */
    @Column(name = "approved_steps", nullable = false)
    private Integer approvedSteps = 0;

    /**
     * Number of rejected approval steps.
     */
    @Column(name = "rejected_steps", nullable = false)
    private Integer rejectedSteps = 0;

    /**
     * Number of pending approval steps.
     */
    @Column(name = "pending_steps", nullable = false)
    private Integer pendingSteps = 0;

    /**
     * Total number of approval steps.
     */
    @Column(name = "total_steps", nullable = false)
    private Integer totalSteps = 0;

    /**
     * Current approver responsible for the active step.
     */
    @Column(name = "current_approver_id")
    private UUID currentApproverId;

    /**
     * Indicates whether the workflow is currently waiting
     * for an approver action.
     */
    @Column(name = "awaiting_approval", nullable = false)
    private boolean awaitingApproval = true;

    /**
     * Additional runtime metadata stored by the engine.
     *
     * <p>
     * Typically serialized as JSON.
     * </p>
     */
    @Lob
    @Column(name = "runtime_metadata")
    private String runtimeMetadata;

    /**
     * Assigns the approval request.
     *
     * @param approvalRequest approval request
     */
    public void setApprovalRequest(final ApprovalRequest approvalRequest) {

        this.approvalRequest = approvalRequest;

        if (approvalRequest != null && approvalRequest.getWorkflow() != this) {
            approvalRequest.setWorkflow(this);
        }
    }

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
        this.totalSteps = this.approvalSteps.size();
        this.pendingSteps = this.totalSteps - this.approvedSteps - this.rejectedSteps;
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

        approvalStep.setApprovalWorkflow(null);
        this.approvalSteps.remove(approvalStep);
        this.totalSteps = this.approvalSteps.size();
        this.pendingSteps = this.totalSteps - this.approvedSteps - this.rejectedSteps;
    }

    /**
     * Marks the workflow as completed.
     *
     * @param completedBy user that completed the workflow
     */
    public void complete(final UUID completedBy) {

        this.completed = true;
        this.completedAt = LocalDateTime.now();
        this.completedBy = completedBy;
        this.awaitingApproval = false;
    }

    /**
     * Cancels the workflow.
     *
     * @param cancelledBy user cancelling the workflow
     * @param reason      cancellation reason
     */
    public void cancel(final UUID cancelledBy,
            final String reason) {

        this.cancelled = true;
        this.cancelledAt = LocalDateTime.now();
        this.cancelledBy = cancelledBy;
        this.cancellationReason = reason;
        this.awaitingApproval = false;
    }

    /**
     * Recalls the workflow.
     *
     * @param recalledBy user recalling the workflow
     * @param reason     recall reason
     */
    public void recall(final UUID recalledBy,
            final String reason) {

        this.recalled = true;
        this.recalledAt = LocalDateTime.now();
        this.recalledBy = recalledBy;
        this.recallReason = reason;
        this.awaitingApproval = false;
    }

    /**
     * Marks the workflow as timed out.
     */
    public void timeout() {

        this.timedOut = true;
        this.timedOutAt = LocalDateTime.now();
        this.awaitingApproval = false;
    }

    /**
     * Increments the number of approved steps.
     */
    public void incrementApprovedSteps() {

        this.approvedSteps++;
        this.pendingSteps = Math.max(0, this.pendingSteps - 1);
    }

    /**
     * Increments the number of rejected steps.
     */
    public void incrementRejectedSteps() {

        this.rejectedSteps++;
        this.pendingSteps = Math.max(0, this.pendingSteps - 1);
    }

    /**
     * Advances to the next stage.
     */
    public void advanceToNextStage() {

        if (this.currentStageOrder == null) {
            this.currentStageOrder = 1;
        } else {
            this.currentStageOrder++;
        }
    }

    /**
     * Returns whether the workflow is active.
     *
     * @return true if active
     */
    public boolean isActive() {

        return !completed
                && !cancelled
                && !recalled
                && !timedOut;
    }

    /**
     * Returns whether the workflow is in a terminal state.
     *
     * @return true if terminal
     */
    public boolean isTerminal() {

        return completed
                || cancelled
                || recalled
                || timedOut;
    }

    /**
     * Returns whether the workflow is executable.
     *
     * @return true if executable
     */
    public boolean isExecutable() {

        return approvalRequest != null
                && !approvalSteps.isEmpty()
                && isActive();
    }

    /**
     * Returns whether the workflow has a due date.
     *
     * @return true if due date exists
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Returns whether the workflow is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !isTerminal();
    }

    /**
     * Returns whether the workflow is awaiting approval.
     *
     * @return true if awaiting approval
     */
    public boolean isAwaitingApproval() {
        return awaitingApproval;
    }

    public ApprovalRequest getApprovalRequest() {
        return approvalRequest;
    }

    /**
     * Returns whether this workflow has completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns whether this workflow has been cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }
}