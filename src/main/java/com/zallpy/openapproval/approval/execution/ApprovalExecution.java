package com.zallpy.openapproval.approval.execution;

import com.zallpy.openapproval.approval.enums.ApprovalExecutionStatus;
import com.zallpy.openapproval.approval.policy.ApprovalPolicy;
import com.zallpy.openapproval.approval.runtime.ApprovalRequest;
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

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a runtime execution of an approval policy.
 *
 * <p>
 * An ApprovalExecution is created whenever an
 * {@link ApprovalRequest} enters the approval engine.
 *
 * <p>
 * It is a snapshot of the approval policy at the time
 * execution begins and serves as the aggregate root for
 * all runtime approval activities.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(
        name = "approval_executions",
        indexes = {
                @Index(
                        name = "idx_execution_request",
                        columnList = "approval_request_id"
                ),
                @Index(
                        name = "idx_execution_policy",
                        columnList = "approval_policy_id"
                ),
                @Index(
                        name = "idx_execution_status",
                        columnList = "execution_status"
                ),
                @Index(
                        name = "idx_execution_reference",
                        columnList = "execution_reference"
                ),
                @Index(
                        name = "idx_execution_active",
                        columnList = "active"
                )
        }
)
public class ApprovalExecution extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Approval request being executed.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_request_id", nullable = false)
    private ApprovalRequest approvalRequest;

    /**
     * Policy used for this execution.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Unique execution identifier.
     */
    @NotNull
    @Column(name = "execution_uuid", nullable = false, unique = true)
    private UUID executionUuid = UUID.randomUUID();

    /**
     * Human-readable execution reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(
            name = "execution_reference",
            nullable = false,
            unique = true,
            length = 100
    )
    private String executionReference;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "execution_name", nullable = false, length = 200)
    private String executionName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Current execution status.
     */
    @NotNull
    @Column(name = "execution_status", nullable = false, length = 40)
    private ApprovalExecutionStatus executionStatus =
            ApprovalExecutionStatus.CREATED;

    /**
     * Execution start time.
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * Execution completion time.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Execution due time.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;
        /**
     * Current stage number being executed.
     */
    @Column(name = "current_stage_order")
    private Integer currentStageOrder;

    /**
     * Current stage code.
     */
    @Size(max = 80)
    @Column(name = "current_stage_code", length = 80)
    private String currentStageCode;

    /**
     * Current stage name.
     */
    @Size(max = 150)
    @Column(name = "current_stage_name", length = 150)
    private String currentStageName;

    /**
     * Total number of stages.
     */
    @NotNull
    @Column(name = "total_stages", nullable = false)
    private Integer totalStages = 0;

    /**
     * Number of completed stages.
     */
    @NotNull
    @Column(name = "completed_stages", nullable = false)
    private Integer completedStages = 0;

    /**
     * Number of rejected stages.
     */
    @NotNull
    @Column(name = "rejected_stages", nullable = false)
    private Integer rejectedStages = 0;

    /**
     * Number of skipped stages.
     */
    @NotNull
    @Column(name = "skipped_stages", nullable = false)
    private Integer skippedStages = 0;

    /**
     * Number of pending stages.
     */
    @NotNull
    @Column(name = "pending_stages", nullable = false)
    private Integer pendingStages = 0;

    /**
     * Whether execution has started.
     */
    @Column(name = "started", nullable = false)
    private boolean started;

    /**
     * Whether execution has completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed;

    /**
     * Whether execution has been cancelled.
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;

    /**
     * Whether execution has been suspended.
     */
    @Column(name = "suspended", nullable = false)
    private boolean suspended;

    /**
     * Completion remarks.
     */
    @Size(max = 4000)
    @Column(name = "completion_remarks", length = 4000)
    private String completionRemarks;

    /**
     * Default constructor.
     */
    public ApprovalExecution() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecution other)) {
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
     * Starts this approval execution.
     */
    public void start() {

        if (started) {
            return;
        }

        this.started = true;
        this.executionStatus = ApprovalExecutionStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }

    /**
     * Completes this approval execution.
     *
     * @param remarks completion remarks
     */
    public void complete(final String remarks) {

        this.completed = true;
        this.suspended = false;
        this.cancelled = false;
        this.executionStatus = ApprovalExecutionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.completionRemarks = remarks;
    }

    /**
     * Cancels this approval execution.
     *
     * @param remarks cancellation reason
     */
    public void cancel(final String remarks) {

        this.cancelled = true;
        this.suspended = false;
        this.executionStatus = ApprovalExecutionStatus.CANCELLED;
        this.completedAt = LocalDateTime.now();
        this.completionRemarks = remarks;
    }

    /**
     * Suspends this approval execution.
     */
    public void suspend() {

        if (completed || cancelled) {
            return;
        }

        this.suspended = true;
        this.executionStatus = ApprovalExecutionStatus.SUSPENDED;
    }

    /**
     * Resumes this approval execution.
     */
    public void resume() {

        if (!suspended) {
            return;
        }

        this.suspended = false;
        this.executionStatus = ApprovalExecutionStatus.IN_PROGRESS;
    }

    /**
     * Moves execution to the specified stage.
     *
     * @param stageOrder stage order
     * @param stageCode stage code
     * @param stageName stage name
     */
    public void moveToStage(final Integer stageOrder,
                            final String stageCode,
                            final String stageName) {

        this.currentStageOrder = stageOrder;
        this.currentStageCode = stageCode;
        this.currentStageName = stageName;
    }

    /**
     * Increments the completed stage count.
     */
    public void incrementCompletedStages() {
        this.completedStages++;
    }

    /**
     * Increments the rejected stage count.
     */
    public void incrementRejectedStages() {
        this.rejectedStages++;
    }

    /**
     * Increments the skipped stage count.
     */
    public void incrementSkippedStages() {
        this.skippedStages++;
    }

    /**
     * Increments the pending stage count.
     */
    public void incrementPendingStages() {
        this.pendingStages++;
    }

    /**
     * Decrements the pending stage count.
     */
    public void decrementPendingStages() {

        if (pendingStages > 0) {
            this.pendingStages--;
        }
    }

    /**
     * Returns whether the execution has started.
     *
     * @return true if started
     */
    public boolean hasStarted() {
        return started;
    }

    /**
     * Returns whether the execution has completed.
     *
     * @return true if completed
     */
    public boolean hasCompleted() {
        return completed;
    }

    /**
     * Returns whether the execution has been cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns whether the execution is suspended.
     *
     * @return true if suspended
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Returns the execution progress percentage.
     *
     * @return progress percentage
     */
    public double getProgressPercentage() {

        if (totalStages == null || totalStages == 0) {
            return 0D;
        }

        return (completedStages.doubleValue() * 100D)
                / totalStages.doubleValue();
    }

    /**
     * Returns whether all stages have completed.
     *
     * @return true if all stages are completed
     */
    public boolean isFinished() {

        return totalStages != null
                && completedStages != null
                && completedStages.equals(totalStages);
    }

        // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalRequest getApprovalRequest() {
        return approvalRequest;
    }

    public void setApprovalRequest(ApprovalRequest approvalRequest) {
        this.approvalRequest = approvalRequest;
    }

    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    public void setApprovalPolicy(ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    public UUID getExecutionUuid() {
        return executionUuid;
    }

    public void setExecutionUuid(UUID executionUuid) {
        this.executionUuid = executionUuid;
    }

    public String getExecutionReference() {
        return executionReference;
    }

    public void setExecutionReference(String executionReference) {
        this.executionReference = executionReference;
    }

    public String getExecutionName() {
        return executionName;
    }

    public void setExecutionName(String executionName) {
        this.executionName = executionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ApprovalExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
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

    public Integer getCurrentStageOrder() {
        return currentStageOrder;
    }

    public void setCurrentStageOrder(Integer currentStageOrder) {
        this.currentStageOrder = currentStageOrder;
    }

    public String getCurrentStageCode() {
        return currentStageCode;
    }

    public void setCurrentStageCode(String currentStageCode) {
        this.currentStageCode = currentStageCode;
    }

    public String getCurrentStageName() {
        return currentStageName;
    }

    public void setCurrentStageName(String currentStageName) {
        this.currentStageName = currentStageName;
    }

    public Integer getTotalStages() {
        return totalStages;
    }

    public void setTotalStages(Integer totalStages) {
        this.totalStages = totalStages;
    }

    public Integer getCompletedStages() {
        return completedStages;
    }

    public void setCompletedStages(Integer completedStages) {
        this.completedStages = completedStages;
    }

    public Integer getRejectedStages() {
        return rejectedStages;
    }

    public void setRejectedStages(Integer rejectedStages) {
        this.rejectedStages = rejectedStages;
    }

    public Integer getSkippedStages() {
        return skippedStages;
    }

    public void setSkippedStages(Integer skippedStages) {
        this.skippedStages = skippedStages;
    }

    public Integer getPendingStages() {
        return pendingStages;
    }

    public void setPendingStages(Integer pendingStages) {
        this.pendingStages = pendingStages;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

  

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
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
     * Validates the execution configuration.
     *
     * @throws IllegalStateException if the execution is invalid
     */
    public void validateConfiguration() {

        if (approvalRequest == null) {
            throw new IllegalStateException(
                    "Approval request is required.");
        }

        if (approvalPolicy == null) {
            throw new IllegalStateException(
                    "Approval policy is required.");
        }

        if (executionReference == null || executionReference.isBlank()) {
            throw new IllegalStateException(
                    "Execution reference is required.");
        }

        if (executionName == null || executionName.isBlank()) {
            throw new IllegalStateException(
                    "Execution name is required.");
        }

        if (executionStatus == null) {
            throw new IllegalStateException(
                    "Execution status is required.");
        }

        if (totalStages == null || totalStages < 0) {
            throw new IllegalStateException(
                    "Total stages cannot be negative.");
        }

        if (completedStages == null || completedStages < 0) {
            throw new IllegalStateException(
                    "Completed stages cannot be negative.");
        }

        if (pendingStages == null || pendingStages < 0) {
            throw new IllegalStateException(
                    "Pending stages cannot be negative.");
        }

        if (completedStages > totalStages) {
            throw new IllegalStateException(
                    "Completed stages cannot exceed total stages.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this execution has a due date.
     *
     * @return true if a due date is configured
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Returns whether this execution is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !completed
                && !cancelled;
    }

    /**
     * Returns whether all stages have been processed.
     *
     * @return true if no pending stages remain
     */
    public boolean hasNoPendingStages() {
        return pendingStages != null && pendingStages == 0;
    }

    /**
     * Returns the number of remaining stages.
     *
     * @return remaining stages
     */
    public int getRemainingStages() {

        if (totalStages == null || completedStages == null) {
            return 0;
        }

        return Math.max(0, totalStages - completedStages);
    }

    /**
     * Returns whether this execution is currently active.
     *
     * @return true if active
     */
    public boolean isInProgress() {
        return executionStatus == ApprovalExecutionStatus.IN_PROGRESS;
    }

    @Override
    public String toString() {
        return "ApprovalExecution{" +
                "id=" + getId() +
                ", executionReference='" + executionReference + '\'' +
                ", executionName='" + executionName + '\'' +
                ", executionStatus=" + executionStatus +
                ", currentStageOrder=" + currentStageOrder +
                ", totalStages=" + totalStages +
                ", completedStages=" + completedStages +
                ", pendingStages=" + pendingStages +
                ", started=" + started +
                ", completed=" + completed +
                ", cancelled=" + cancelled +
                ", active=" + isActive() +
                '}';
    }
}