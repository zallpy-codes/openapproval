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
 * Represents a single executable approval step within an approval workflow.
 *
 * <p>
 * An {@code ApprovalStep} is the runtime representation of an
 * {@link ApprovalStage}. It is assigned to an approver and records the
 * approver's decision during workflow execution.
 * </p>
 *
 * <p>
 * Each approval workflow consists of one or more approval steps executed
 * according to the configured approval strategy.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_step", indexes = {
        @Index(name = "idx_step_workflow", columnList = "approval_workflow_id"),
        @Index(name = "idx_step_stage_order", columnList = "stage_order"),
        @Index(name = "idx_step_approver", columnList = "approver_id"),
        @Index(name = "idx_step_status", columnList = "status")
})
public class ApprovalStep extends BaseEntity {

    /**
     * Parent approval workflow.
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
     * Stage execution order.
     */
    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder;

    /**
     * User assigned to perform this approval.
     */
    @Column(name = "approver_id", nullable = false)
    private UUID approverId;

    /**
     * Display name of the approver.
     */
    @Column(name = "approver_name", length = 200)
    private String approverName;

    /**
     * Current execution status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    /**
     * Indicates whether this is the current active approval step.
     */
    @Column(name = "current_step", nullable = false)
    private boolean currentStep = false;

    /**
     * Indicates whether this step has been completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    /**
     * Date and time the approval decision was made.
     */
    @Column(name = "decision_at")
    private LocalDateTime decisionAt;

    /**
     * User that recorded the approval decision.
     */
    @Column(name = "decision_by")
    private UUID decisionBy;

    /**
     * Approval or rejection comment.
     */
    @Column(name = "decision_comment", length = 4000)
    private String decisionComment;

    /**
     * Indicates whether this step has been delegated.
     */
    @Column(name = "delegated", nullable = false)
    private boolean delegated = false;

    /**
     * User that delegated this approval.
     */
    @Column(name = "delegated_by")
    private UUID delegatedBy;

    /**
     * User to whom this approval was delegated.
     */
    @Column(name = "delegated_to")
    private UUID delegatedTo;

    /**
     * Date and time delegation occurred.
     */
    @Column(name = "delegated_at")
    private LocalDateTime delegatedAt;

    /**
     * Indicates whether this step has been escalated.
     */
    @Column(name = "escalated", nullable = false)
    private boolean escalated = false;

    /**
     * Date and time escalation occurred.
     */
    @Column(name = "escalated_at")
    private LocalDateTime escalatedAt;

    /**
     * User that received the escalated approval.
     */
    @Column(name = "escalated_to")
    private UUID escalatedTo;

    /**
     * Number of reminder notifications sent.
     */
    @Column(name = "reminder_count", nullable = false)
    private Integer reminderCount = 0;

    /**
     * Indicates whether this approval step has timed out.
     */
    @Column(name = "timed_out", nullable = false)
    private boolean timedOut = false;

    /**
     * Date and time this approval step timed out.
     */
    @Column(name = "timed_out_at")
    private LocalDateTime timedOutAt;

    /**
     * Due date for this approval step.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Additional runtime metadata.
     *
     * <p>
     * This field may contain serialized JSON produced by the
     * approval engine for auditing or integration purposes.
     * </p>
     */
    @Lob
    @Column(name = "runtime_metadata")
    private String runtimeMetadata;

    /**
     * Assigns the parent approval workflow.
     *
     * @param approvalWorkflow approval workflow
     */
    public void setApprovalWorkflow(final ApprovalWorkflow approvalWorkflow) {
        this.approvalWorkflow = approvalWorkflow;
    }

    /**
     * Assigns the approval stage.
     *
     * @param approvalStage approval stage
     */
    public void setApprovalStage(final ApprovalStage approvalStage) {
        this.approvalStage = approvalStage;
    }

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
     * Delegates this step.
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
     * @param escalatedTo escalated approver
     */
    public void escalate(final UUID escalatedTo) {

        this.escalated = true;
        this.escalatedTo = escalatedTo;
        this.escalatedAt = LocalDateTime.now();

        this.approverId = escalatedTo;
    }

    /**
     * Marks this approval step as timed out.
     */
    public void timeout() {

        this.timedOut = true;
        this.timedOutAt = LocalDateTime.now();
        this.currentStep = false;
    }

    /**
     * Increments the reminder notification count.
     */
    public void incrementReminderCount() {

        if (this.reminderCount == null) {
            this.reminderCount = 0;
        }

        this.reminderCount++;
    }

    /**
     * Marks this step as the current active step.
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

    /**
     * Determines whether this step is pending.
     *
     * @return true if pending
     */
    public boolean isPending() {
        return ApprovalStatus.PENDING.equals(this.status);
    }

    /**
     * Determines whether this step has been approved.
     *
     * @return true if approved
     */
    public boolean isApproved() {
        return ApprovalStatus.APPROVED.equals(this.status);
    }

    /**
     * Determines whether this step has been rejected.
     *
     * @return true if rejected
     */
    public boolean isRejected() {
        return ApprovalStatus.REJECTED.equals(this.status);
    }

    /**
     * Determines whether this step is executable.
     *
     * @return true if executable
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
     * @return true if due date exists
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Determines whether this step is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !completed;
    }

}