package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.zallpy.openapproval.approval.enums.ApprovalTaskStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.Objects;

import java.io.Serial;

/**
 * Represents an executable approval task assigned to a single approver
 * during the execution of an approval stage.
 *
 * <p>
 * Every approver participating in an approval stage receives an
 * independent runtime task. The task records assignment information,
 * execution progress and completion details.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_tasks", indexes = {
        @Index(name = "idx_execution_task_execution", columnList = "approval_execution_id"),
        @Index(name = "idx_execution_task_stage", columnList = "approval_execution_stage_id"),
        @Index(name = "idx_execution_task_reference", columnList = "task_reference"),
        @Index(name = "idx_execution_task_active", columnList = "active")
})
public class ApprovalExecutionTask extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval execution.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_id", nullable = false)
    private ApprovalExecution approvalExecution;

    /**
     * Parent execution stage.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_stage_id", nullable = false)
    private ApprovalExecutionStage approvalExecutionStage;

    /**
     * Unique task reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "task_reference", nullable = false, unique = true, length = 100)
    private String taskReference;

    /**
     * Task title.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "task_name", nullable = false, length = 200)
    private String taskName;

    /**
     * Task description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Assigned approver identifier.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "assignee_id", nullable = false, length = 100)
    private String assigneeId;

    /**
     * Assigned approver name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "assignee_name", nullable = false, length = 200)
    private String assigneeName;

    /**
     * Assigned approver email.
     */
    @Size(max = 255)
    @Column(name = "assignee_email", length = 255)
    private String assigneeEmail;

    /**
     * Current task status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false, length = 40)
    private ApprovalTaskStatus taskStatus = ApprovalTaskStatus.PENDING;

    /**
     * Task assignment time.
     */
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    /**
     * Task start time.
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * Task completion time.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Task due date/time.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Number of reminders sent.
     */
    @Column(name = "reminder_count", nullable = false)
    private Integer reminderCount = 0;

    /**
     * Number of escalations performed.
     */
    @Column(name = "escalation_count", nullable = false)
    private Integer escalationCount = 0;

    /**
     * Indicates whether the task has been delegated.
     */
    @Column(name = "delegated", nullable = false)
    private boolean delegated;

    /**
     * Indicates whether the task has been escalated.
     */
    @Column(name = "escalated", nullable = false)
    private boolean escalated;

    /**
     * Completion remarks.
     */
    @Size(max = 4000)
    @Column(name = "completion_remarks", length = 4000)
    private String completionRemarks;

    /**
     * Default constructor.
     */
    public ApprovalExecutionTask() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionTask other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    // -------------------------------------------------------------------------
    // Business Methods
    // -------------------------------------------------------------------------

    /**
     * Assigns this task.
     */
    public void assign() {

        this.assignedAt = LocalDateTime.now();
        this.taskStatus = ApprovalTaskStatus.ASSIGNED;
    }

    /**
     * Starts processing this task.
     */
    public void start() {

        if (startedAt != null) {
            return;
        }

        this.startedAt = LocalDateTime.now();
        this.taskStatus = ApprovalTaskStatus.IN_PROGRESS;
    }

    /**
     * Marks this task as approved.
     *
     * @param remarks completion remarks
     */
    public void approve(final String remarks) {

        this.completedAt = LocalDateTime.now();
        this.taskStatus = ApprovalTaskStatus.APPROVED;
        this.completionRemarks = remarks;
    }

    /**
     * Marks this task as rejected.
     *
     * @param remarks rejection remarks
     */
    public void reject(final String remarks) {

        this.completedAt = LocalDateTime.now();
        this.taskStatus = ApprovalTaskStatus.REJECTED;
        this.completionRemarks = remarks;
    }

    /**
     * Cancels this task.
     *
     * @param remarks cancellation remarks
     */
    public void cancel(final String remarks) {

        this.completedAt = LocalDateTime.now();
        this.taskStatus = ApprovalTaskStatus.CANCELLED;
        this.completionRemarks = remarks;
    }

    /**
     * Delegates this task.
     */
    public void delegate() {

        this.delegated = true;
        this.taskStatus = ApprovalTaskStatus.DELEGATED;
    }

    /**
     * Escalates this task.
     */
    public void escalate() {

        this.escalated = true;
        this.escalationCount++;
        this.taskStatus = ApprovalTaskStatus.ESCALATED;
    }

    /**
     * Records that a reminder has been sent.
     */
    public void sendReminder() {
        this.reminderCount++;
    }

    /**
     * Returns whether this task has been assigned.
     *
     * @return true if assigned
     */
    public boolean isAssigned() {
        return assignedAt != null;
    }

    /**
     * Returns whether this task has been completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completedAt != null;
    }

    /**
     * Returns whether this task is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !isCompleted();
    }

    /**
     * Returns whether this task can still be processed.
     *
     * @return true if actionable
     */
    public boolean isActionable() {

        return taskStatus != ApprovalTaskStatus.APPROVED
                && taskStatus != ApprovalTaskStatus.REJECTED
                && taskStatus != ApprovalTaskStatus.CANCELLED;
    }

    /**
     * Returns whether this task has been delegated.
     *
     * @return true if delegated
     */
    public boolean hasBeenDelegated() {
        return delegated;
    }

    /**
     * Returns whether this task has been escalated.
     *
     * @return true if escalated
     */
    public boolean hasBeenEscalated() {
        return escalated;
    }

    /**
     * Returns whether any reminder has been sent.
     *
     * @return true if at least one reminder has been sent
     */
    public boolean hasReminders() {
        return reminderCount != null && reminderCount > 0;
    }

    /**
     * Returns whether any escalation has occurred.
     *
     * @return true if at least one escalation has occurred
     */
    public boolean hasEscalations() {
        return escalationCount != null && escalationCount > 0;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalExecution getApprovalExecution() {
        return approvalExecution;
    }

    public void setApprovalExecution(ApprovalExecution approvalExecution) {
        this.approvalExecution = approvalExecution;
    }

    public ApprovalExecutionStage getApprovalExecutionStage() {
        return approvalExecutionStage;
    }

    public void setApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage) {
        this.approvalExecutionStage = approvalExecutionStage;
    }

    public String getTaskReference() {
        return taskReference;
    }

    public void setTaskReference(String taskReference) {
        this.taskReference = taskReference;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    public ApprovalTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(ApprovalTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public Integer getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(Integer reminderCount) {
        this.reminderCount = reminderCount;
    }

    public Integer getEscalationCount() {
        return escalationCount;
    }

    public void setEscalationCount(Integer escalationCount) {
        this.escalationCount = escalationCount;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public void setDelegated(boolean delegated) {
        this.delegated = delegated;
    }

    public boolean isEscalated() {
        return escalated;
    }

    public void setEscalated(boolean escalated) {
        this.escalated = escalated;
    }

    public String getCompletionRemarks() {
        return completionRemarks;
    }

    public void setCompletionRemarks(String completionRemarks) {
        this.completionRemarks = completionRemarks;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates the task configuration.
     *
     * @throws IllegalStateException if the task configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalExecution == null) {
            throw new IllegalStateException(
                    "Approval execution is required.");
        }

        if (approvalExecutionStage == null) {
            throw new IllegalStateException(
                    "Approval execution stage is required.");
        }

        if (taskReference == null || taskReference.isBlank()) {
            throw new IllegalStateException(
                    "Task reference is required.");
        }

        if (taskName == null || taskName.isBlank()) {
            throw new IllegalStateException(
                    "Task name is required.");
        }

        if (assigneeId == null || assigneeId.isBlank()) {
            throw new IllegalStateException(
                    "Assignee ID is required.");
        }

        if (assigneeName == null || assigneeName.isBlank()) {
            throw new IllegalStateException(
                    "Assignee name is required.");
        }

        if (taskStatus == null) {
            throw new IllegalStateException(
                    "Task status is required.");
        }

        if (reminderCount != null && reminderCount < 0) {
            throw new IllegalStateException(
                    "Reminder count cannot be negative.");
        }

        if (escalationCount != null && escalationCount < 0) {
            throw new IllegalStateException(
                    "Escalation count cannot be negative.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this task has a due date.
     *
     * @return {@code true} if a due date has been configured
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Returns the total number of activities performed
     * on this task.
     *
     * @return activity count
     */
    public int getActivityCount() {

        return (reminderCount == null ? 0 : reminderCount)
                + (escalationCount == null ? 0 : escalationCount);
    }

    /**
     * Returns whether this task has completion remarks.
     *
     * @return {@code true} if remarks exist
     */
    public boolean hasCompletionRemarks() {
        return completionRemarks != null
                && !completionRemarks.isBlank();
    }

    @Override
    public String toString() {
        return "ApprovalExecutionTask{" +
                "id=" + getId() +
                ", taskReference='" + taskReference + '\'' +
                ", taskName='" + taskName + '\'' +
                ", assigneeId='" + assigneeId + '\'' +
                ", assigneeName='" + assigneeName + '\'' +
                ", taskStatus=" + taskStatus +
                ", delegated=" + delegated +
                ", escalated=" + escalated +
                ", reminderCount=" + reminderCount +
                ", escalationCount=" + escalationCount +
                ", active=" + isActive() +
                '}';
    }
}