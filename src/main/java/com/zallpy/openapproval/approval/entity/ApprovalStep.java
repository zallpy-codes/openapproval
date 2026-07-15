package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Runtime approval step executed within an {@link ApprovalWorkflow}.
 *
 * <p>
 * An {@code ApprovalStep} is created from an {@link ApprovalStage} when an
 * approval workflow starts. It represents the actual work assigned to a
 * specific approver.
 * </p>
 *
 * <p>
 * Unlike {@link ApprovalStage}, which is configuration, this entity stores
 * runtime execution information such as approval decisions, delegation,
 * escalation, reminders and timeout processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_step", indexes = {
        @Index(name = "idx_step_workflow", columnList = "approval_workflow_id"),
        @Index(name = "idx_step_stage", columnList = "approval_stage_id"),
        @Index(name = "idx_step_stage_order", columnList = "stage_order"),
        @Index(name = "idx_step_approver", columnList = "approver_id"),
        @Index(name = "idx_step_status", columnList = "status")
})
public class ApprovalStep extends BaseEntity {

    /**
     * Parent workflow.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_workflow_id", nullable = false)
    private ApprovalWorkflow approvalWorkflow;

    /**
     * Source approval stage.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_stage_id", nullable = false)
    private ApprovalStage approvalStage;

    /**
     * Execution order.
     */
    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder;

    /**
     * User responsible for this approval.
     */
    @Column(name = "approver_id", nullable = false)
    private UUID approverId;

    /**
     * Approver display name.
     */
    @Column(name = "approver_name", length = 200)
    private String approverName;

    /**
     * Runtime approval status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    /**
     * Indicates whether this is the active workflow step.
     */
    @Column(name = "current_step", nullable = false)
    private boolean currentStep = false;

    /**
     * Indicates whether execution has completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    /**
     * Decision timestamp.
     */
    @Column(name = "decision_at")
    private LocalDateTime decisionAt;

    /**
     * User that made the decision.
     */
    @Column(name = "decision_by")
    private UUID decisionBy;

    /**
     * Approval or rejection comment.
     */
    @Column(name = "decision_comment", length = 4000)
    private String decisionComment;

    /**
     * Indicates whether delegation occurred.
     */
    @Column(name = "delegated", nullable = false)
    private boolean delegated = false;

    /**
     * User that delegated the step.
     */
    @Column(name = "delegated_by")
    private UUID delegatedBy;

    /**
     * New approver after delegation.
     */
    @Column(name = "delegated_to")
    private UUID delegatedTo;

    /**
     * Delegation timestamp.
     */
    @Column(name = "delegated_at")
    private LocalDateTime delegatedAt;

    /**
     * Indicates whether escalation occurred.
     */
    @Column(name = "escalated", nullable = false)
    private boolean escalated = false;

    /**
     * Escalation timestamp.
     */
    @Column(name = "escalated_at")
    private LocalDateTime escalatedAt;

    /**
     * User receiving the escalation.
     */
    @Column(name = "escalated_to")
    private UUID escalatedTo;

    /**
     * Number of reminder notifications sent.
     */
    @Column(name = "reminder_count", nullable = false)
    private Integer reminderCount = 0;

    /**
     * Indicates whether timeout occurred.
     */
    @Column(name = "timed_out", nullable = false)
    private boolean timedOut = false;

    /**
     * Timeout timestamp.
     */
    @Column(name = "timed_out_at")
    private LocalDateTime timedOutAt;

    /**
     * Due date for this approval.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Runtime metadata.
     */
    @Lob
    @Column(name = "runtime_metadata")
    private String runtimeMetadata;

    /*
     * ==========================================================
     * Relationship Management
     * ==========================================================
     */

    /**
     * Assigns the parent workflow.
     *
     * @param approvalWorkflow parent workflow
     */
    public void setApprovalWorkflow(final ApprovalWorkflow approvalWorkflow) {
        this.approvalWorkflow = approvalWorkflow;
    }

    /**
     * Assigns the source approval stage.
     *
     * @param approvalStage approval stage
     */
    public void setApprovalStage(final ApprovalStage approvalStage) {
        this.approvalStage = approvalStage;
    }

    /**
     * Assigns the execution order.
     *
     * @param stageOrder execution order
     */
    public void setStageOrder(final Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    /**
     * Assigns the approver.
     *
     * @param approverId approver identifier
     */
    public void setApproverId(final UUID approverId) {
        this.approverId = approverId;
    }

    /**
     * Assigns the approver display name.
     *
     * @param approverName approver name
     */
    public void setApproverName(final String approverName) {
        this.approverName = approverName;
    }

    /**
     * Assigns the due date.
     *
     * @param dueAt due date
     */
    public void setDueAt(final LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    /**
     * Assigns runtime metadata.
     *
     * @param runtimeMetadata runtime metadata
     */
    public void setRuntimeMetadata(final String runtimeMetadata) {
        this.runtimeMetadata = runtimeMetadata;
    }

    /*
     * ==========================================================
     * Getters
     * ==========================================================
     */

    /**
     * Returns the parent workflow.
     *
     * @return approval workflow
     */
    public ApprovalWorkflow getApprovalWorkflow() {
        return approvalWorkflow;
    }

    /**
     * Returns the source approval stage.
     *
     * @return approval stage
     */
    public ApprovalStage getApprovalStage() {
        return approvalStage;
    }

    /**
     * Returns the stage execution order.
     *
     * @return stage order
     */
    public Integer getStageOrder() {
        return stageOrder;
    }

    /**
     * Returns the assigned approver.
     *
     * @return approver identifier
     */
    public UUID getApproverId() {
        return approverId;
    }

    /**
     * Returns the approver name.
     *
     * @return approver name
     */
    public String getApproverName() {
        return approverName;
    }

    /**
     * Returns the approval status.
     *
     * @return approval status
     */
    public ApprovalStatus getStatus() {
        return status;
    }

    /**
     * Returns the decision timestamp.
     *
     * @return decision time
     */
    public LocalDateTime getDecisionAt() {
        return decisionAt;
    }

    /**
     * Returns the decision maker.
     *
     * @return decision maker
     */
    public UUID getDecisionBy() {
        return decisionBy;
    }

    /**
     * Returns the decision comment.
     *
     * @return decision comment
     */
    public String getDecisionComment() {
        return decisionComment;
    }

    /**
     * Returns the reminder count.
     *
     * @return reminder count
     */
    public Integer getReminderCount() {
        return reminderCount;
    }

    /**
     * Returns the due date.
     *
     * @return due date
     */
    public LocalDateTime getDueAt() {
        return dueAt;
    }

    /**
     * Returns runtime metadata.
     *
     * @return runtime metadata
     */
    public String getRuntimeMetadata() {
        return runtimeMetadata;
    }

    /*
     * ==========================================================
     * Runtime Lifecycle
     * ==========================================================
     */

    /**
     * Approves this step.
     *
     * @param approverId approving user
     * @param comment    approval comment
     */
    public void approve(final UUID approverId,
            final String comment) {

        this.status = ApprovalStatus.APPROVED;
        this.completed = true;
        this.currentStep = false;
        this.decisionBy = approverId;
        this.decisionAt = LocalDateTime.now();
        this.decisionComment = comment;
    }

    /**
     * Rejects this step.
     *
     * @param approverId rejecting user
     * @param comment    rejection comment
     */
    public void reject(final UUID approverId,
            final String comment) {

        this.status = ApprovalStatus.REJECTED;
        this.completed = true;
        this.currentStep = false;
        this.decisionBy = approverId;
        this.decisionAt = LocalDateTime.now();
        this.decisionComment = comment;
    }

    /**
     * Delegates this approval step.
     *
     * @param delegatedBy delegating user
     * @param delegatedTo new approver
     */
    public void delegate(final UUID delegatedBy,
            final UUID delegatedTo) {

        this.delegated = true;
        this.delegatedBy = delegatedBy;
        this.delegatedTo = delegatedTo;
        this.delegatedAt = LocalDateTime.now();

        this.approverId = delegatedTo;
    }

    /**
     * Escalates this approval step.
     *
     * @param escalatedTo new approver
     */
    public void escalate(final UUID escalatedTo) {

        this.escalated = true;
        this.escalatedTo = escalatedTo;
        this.escalatedAt = LocalDateTime.now();

        this.approverId = escalatedTo;
    }

    /**
     * Marks this step as timed out.
     */
    public void timeout() {

        this.timedOut = true;
        this.timedOutAt = LocalDateTime.now();
        this.currentStep = false;
    }

    /**
     * Increments reminder count.
     */
    public void incrementReminderCount() {

        if (this.reminderCount == null) {
            this.reminderCount = 0;
        }

        this.reminderCount++;
    }

    /**
     * Activates this step.
     */
    public void activate() {
        this.currentStep = true;
    }

    /**
     * Deactivates this step.
     */
    public void deactivate() {
        this.currentStep = false;
    }

    /*
     * ==========================================================
     * Business Helpers
     * ==========================================================
     */

    /**
     * Determines whether this is the current active step.
     *
     * @return {@code true} if current
     */
    public boolean isCurrentStep() {
        return currentStep;
    }

    /**
     * Determines whether this step has completed.
     *
     * @return {@code true} if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Determines whether this step has been delegated.
     *
     * @return {@code true} if delegated
     */
    public boolean isDelegated() {
        return delegated;
    }

    /**
     * Determines whether this step has been escalated.
     *
     * @return {@code true} if escalated
     */
    public boolean isEscalated() {
        return escalated;
    }

    /**
     * Determines whether this step has timed out.
     *
     * @return {@code true} if timed out
     */
    public boolean isTimedOut() {
        return timedOut;
    }

    /**
     * Determines whether this step is pending.
     *
     * @return {@code true} if pending
     */
    public boolean isPending() {
        return ApprovalStatus.PENDING.equals(status);
    }

    /**
     * Determines whether this step has been approved.
     *
     * @return {@code true} if approved
     */
    public boolean isApproved() {
        return ApprovalStatus.APPROVED.equals(status);
    }

    /**
     * Determines whether this step has been rejected.
     *
     * @return {@code true} if rejected
     */
    public boolean isRejected() {
        return ApprovalStatus.REJECTED.equals(status);
    }

    /**
     * Determines whether this step is executable.
     *
     * @return {@code true} if executable
     */
    public boolean isExecutable() {

        return approvalWorkflow != null
                && approvalStage != null
                && currentStep
                && !completed
                && !timedOut;
    }

    /**
     * Determines whether this step has a due date.
     *
     * @return {@code true} if a due date exists
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Determines whether this step is overdue.
     *
     * @return {@code true} if overdue
     */
    public boolean isOverdue() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !completed;
    }

    /**
     * Determines whether this step can accept a decision.
     *
     * @return {@code true} if a decision can be recorded
     */
    public boolean canExecute() {

        return isExecutable()
                && isPending();
    }

    /**
     * Determines whether delegation is allowed.
     *
     * @return {@code true} if delegation is allowed
     */
    public boolean canDelegate() {

        return isPending()
                && !completed
                && !delegated;
    }

    /**
     * Determines whether escalation is allowed.
     *
     * @return {@code true} if escalation is allowed
     */
    public boolean canEscalate() {

        return isPending()
                && !completed
                && !escalated;
    }

    /**
     * Determines whether timeout processing is allowed.
     *
     * @return {@code true} if timeout can occur
     */
    public boolean canTimeout() {

        return isPending()
                && !completed
                && !timedOut
                && hasDueDate()
                && isOverdue();
    }

    /*
     * ==========================================================
     * Additional Getters
     * ==========================================================
     */

    /**
     * Returns the user that received delegation.
     *
     * @return delegated user
     */
    public UUID getDelegatedTo() {
        return delegatedTo;
    }

    /**
     * Returns the user that performed delegation.
     *
     * @return delegating user
     */
    public UUID getDelegatedBy() {
        return delegatedBy;
    }

    /**
     * Returns delegation timestamp.
     *
     * @return delegation timestamp
     */
    public LocalDateTime getDelegatedAt() {
        return delegatedAt;
    }

    /**
     * Returns escalation recipient.
     *
     * @return escalated user
     */
    public UUID getEscalatedTo() {
        return escalatedTo;
    }

    /**
     * Returns escalation timestamp.
     *
     * @return escalation timestamp
     */
    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }

    /**
     * Returns timeout timestamp.
     *
     * @return timeout timestamp
     */
    public LocalDateTime getTimedOutAt() {
        return timedOutAt;
    }



    /**
     * Returns the current approval status.
     *
     * @return approval status
     */
    public ApprovalStatus getApprovalStatus() {
        return status;
    }

    /**
     * Returns the workflow identifier.
     *
     * @return workflow identifier or {@code null}
     */
    public UUID getWorkflowId() {

        return approvalWorkflow == null
                ? null
                : approvalWorkflow.getId();
    }

    /**
     * Returns the approval stage identifier.
     *
     * @return approval stage identifier or {@code null}
     */
    public UUID getStageId() {

        return approvalStage == null
                ? null
                : approvalStage.getId();
    }

    /**
     * Determines whether this step belongs to the supplied workflow.
     *
     * @param workflow approval workflow
     * @return {@code true} if it belongs to the workflow
     */
    public boolean belongsTo(final ApprovalWorkflow workflow) {

        return workflow != null
                && workflow.equals(this.approvalWorkflow);
    }

    /**
     * Determines whether this step belongs to the supplied stage.
     *
     * @param stage approval stage
     * @return {@code true} if it belongs to the stage
     */
    public boolean belongsTo(final ApprovalStage stage) {

        return stage != null
                && stage.equals(this.approvalStage);
    }

    /**
     * Clears all runtime execution information.
     *
     * <p>
     * Intended for workflow rebuilding or administrative recovery.
     * </p>
     */
    public void reset() {

        this.status = ApprovalStatus.PENDING;

        this.currentStep = false;
        this.completed = false;

        this.decisionAt = null;
        this.decisionBy = null;
        this.decisionComment = null;

        this.delegated = false;
        this.delegatedAt = null;
        this.delegatedBy = null;
        this.delegatedTo = null;

        this.escalated = false;
        this.escalatedAt = null;
        this.escalatedTo = null;

        this.timedOut = false;
        this.timedOutAt = null;

        this.reminderCount = 0;
    }

}