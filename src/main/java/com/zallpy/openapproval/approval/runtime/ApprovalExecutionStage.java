package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalExecutionStageStatus;
import com.zallpy.openapproval.approval.enums.ApprovalMode;
import com.zallpy.openapproval.approval.enums.ApprovalPriority;
import com.zallpy.openapproval.approval.enums.ApprovalStageType;
import com.zallpy.openapproval.approval.policy.ApprovalPolicyStage;
import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Runtime execution stage for an approval request.
 *
 * <p>
 * Each ApprovalExecutionStage represents a runtime snapshot of a single
 * ApprovalPolicyStage. Once created, it preserves the stage configuration
 * used during execution even if the underlying policy changes later.
 *
 * This entity is responsible for tracking:
 * <ul>
 * <li>Stage execution</li>
 * <li>Approval progress</li>
 * <li>Delegation</li>
 * <li>Escalation</li>
 * <li>Reminder activity</li>
 * <li>SLA monitoring</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_stages", indexes = {
        @Index(name = "idx_exec_stage_request", columnList = "approval_request_id"),
        @Index(name = "idx_exec_stage_policy", columnList = "approval_policy_stage_id"),
        @Index(name = "idx_exec_stage_uuid", columnList = "stage_uuid"),
        @Index(name = "idx_exec_stage_status", columnList = "status"),
        @Index(name = "idx_exec_stage_order", columnList = "stage_order"),
        @Index(name = "idx_exec_stage_due", columnList = "due_at"),
        @Index(name = "idx_exec_stage_active", columnList = "active")
})
public class ApprovalExecutionStage extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval request.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_request_id", nullable = false)
    private ApprovalRequest approvalRequest;

    /**
     * Source policy stage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_policy_stage_id")
    private ApprovalPolicyStage approvalPolicyStage;

    /**
     * Immutable runtime stage identifier.
     */
    @NotNull
    @Column(name = "stage_uuid", nullable = false, unique = true, updatable = false)
    private UUID stageUuid = UUID.randomUUID();

    /**
     * Stage execution order.
     */
    @NotNull
    @Min(1)
    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder;

    /**
     * Snapshot of the policy stage code.
     */
    @NotBlank
    @Size(max = 50)
    @Column(name = "stage_code", nullable = false, length = 50)
    private String stageCode;

    /**
     * Snapshot of the policy stage name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "stage_name", nullable = false, length = 150)
    private String stageName;

    /**
     * Snapshot of the policy stage description.
     */
    @Size(max = 2000)
    @Column(name = "stage_description", length = 2000)
    private String stageDescription;

    /**
     * Snapshot of the configured stage type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stage_type", nullable = false, length = 40)
    private ApprovalStageType stageType;

    /**
     * Runtime execution status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ApprovalExecutionStageStatus status = ApprovalExecutionStageStatus.PENDING;

    /**
     * Runtime execution priority.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private ApprovalPriority priority = ApprovalPriority.NORMAL;

    /**
     * Snapshot of the approval mode.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_mode", nullable = false, length = 30)
    private ApprovalMode approvalMode;

    /**
     * Number of required approvals.
     */
    @NotNull
    @Min(1)
    @Column(name = "required_approvals", nullable = false)
    private Integer requiredApprovals;

    /**
     * Number of completed approvals.
     */
    @Min(0)
    @Column(name = "completed_approvals", nullable = false)
    private Integer completedApprovals = 0;

    /**
     * Number of rejected approvals.
     */
    @Min(0)
    @Column(name = "rejected_approvals", nullable = false)
    private Integer rejectedApprovals = 0;

    /**
     * Number of pending approvals.
     */
    @Min(0)
    @Column(name = "pending_approvals", nullable = false)
    private Integer pendingApprovals = 0;

    /**
     * Number of delegated approvals.
     */
    @Min(0)
    @Column(name = "delegated_approvals", nullable = false)
    private Integer delegatedApprovals = 0;

    /**
     * Number of escalated approvals.
     */
    @Min(0)
    @Column(name = "escalated_approvals", nullable = false)
    private Integer escalatedApprovals = 0;

    /**
     * Number of reminders sent.
     */
    @Min(0)
    @Column(name = "reminder_count", nullable = false)
    private Integer reminderCount = 0;

    /**
     * Number of recorded actions.
     */
    @Min(0)
    @Column(name = "action_count", nullable = false)
    private Integer actionCount = 0;

    /**
     * Number of approval attempts.
     */
    @Min(0)
    @Column(name = "approval_attempts", nullable = false)
    private Integer approvalAttempts = 0;

    /**
     * Number of reassignment operations.
     */
    @Min(0)
    @Column(name = "reassignment_count", nullable = false)
    private Integer reassignmentCount = 0;

    /**
     * Number of SLA breaches.
     */
    @Min(0)
    @Column(name = "sla_breach_count", nullable = false)
    private Integer slaBreachCount = 0;

    /**
     * Indicates whether execution has started.
     */
    @Column(name = "started", nullable = false)
    private boolean started;

    /**
     * Indicates whether execution completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed;

    /**
     * Indicates whether this stage was skipped.
     */
    @Column(name = "skipped", nullable = false)
    private boolean skipped;

    /**
     * Indicates whether this stage ended in rejection.
     */
    @Column(name = "rejected", nullable = false)
    private boolean rejected;

    /**
     * Indicates whether this stage was delegated.
     */
    @Column(name = "delegated", nullable = false)
    private boolean delegated;

    /**
     * Indicates whether this stage was escalated.
     */
    @Column(name = "escalated", nullable = false)
    private boolean escalated;

    /**
     * Indicates whether this stage is overdue.
     */
    @Column(name = "overdue", nullable = false)
    private boolean overdue;

    /**
     * Indicates whether execution is suspended.
     */
    @Column(name = "suspended", nullable = false)
    private boolean suspended;

    /**
     * Stage execution start timestamp.
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * Stage due timestamp.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Stage completion timestamp.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Timestamp of the last recorded action.
     */
    @Column(name = "last_action_at")
    private LocalDateTime lastActionAt;

    /**
     * User that completed this stage.
     */
    @Size(max = 150)
    @Column(name = "completed_by", length = 150)
    private String completedBy;

    /**
     * Reason for rejection.
     */
    @Size(max = 4000)
    @Column(name = "rejection_reason", length = 4000)
    private String rejectionReason;

    /**
     * Snapshot of the stage timeout in minutes.
     */
    @Min(0)
    @Column(name = "timeout_minutes")
    private Integer timeoutMinutes;

    /**
     * Default constructor.
     */
    public ApprovalExecutionStage() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionStage other)) {
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
     * Starts stage execution.
     */
    public void start() {

        this.started = true;
        this.status = ApprovalExecutionStageStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
        this.lastActionAt = startedAt;
    }

    /**
     * Completes this execution stage.
     *
     * @param completedBy user completing the stage
     */
    public void complete(String completedBy) {

        this.completed = true;
        this.status = ApprovalExecutionStageStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.lastActionAt = completedAt;
        this.completedBy = completedBy;
    }

    /**
     * Approves this stage.
     *
     * @param approvedBy approving user
     */
    public void approve(String approvedBy) {

        incrementCompletedApprovals();
        incrementActionCount();

        this.completed = true;
        this.status = ApprovalExecutionStageStatus.APPROVED;
        this.completedAt = LocalDateTime.now();
        this.lastActionAt = completedAt;
        this.completedBy = approvedBy;
    }

    /**
     * Rejects this stage.
     *
     * @param rejectedBy approving user
     * @param reason     rejection reason
     */
    public void reject(String rejectedBy,
            String reason) {

        incrementRejectedApprovals();
        incrementActionCount();

        this.rejected = true;
        this.status = ApprovalExecutionStageStatus.REJECTED;
        this.completedAt = LocalDateTime.now();
        this.lastActionAt = completedAt;
        this.completedBy = rejectedBy;
        this.rejectionReason = reason;
    }

    /**
     * Skips this stage.
     */
    public void skip() {

        this.skipped = true;
        this.completed = true;
        this.status = ApprovalExecutionStageStatus.SKIPPED;
        this.completedAt = LocalDateTime.now();
        this.lastActionAt = completedAt;
    }

    /**
     * Delegates this stage.
     */
    public void delegate() {

        this.delegated = true;

        incrementDelegatedApprovals();
        incrementActionCount();

        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Escalates this stage.
     */
    public void escalate() {

        this.escalated = true;

        incrementEscalatedApprovals();
        incrementActionCount();

        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Suspends stage execution.
     */
    public void suspend() {

        this.suspended = true;
        this.status = ApprovalExecutionStageStatus.ON_HOLD;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Resumes stage execution.
     */
    public void resume() {

        this.suspended = false;
        this.status = ApprovalExecutionStageStatus.IN_PROGRESS;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Marks this stage as overdue.
     */
    public void markOverdue() {

        this.overdue = true;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Clears overdue status.
     */
    public void clearOverdue() {
        this.overdue = false;
    }

    /**
     * Increments completed approvals.
     */
    public void incrementCompletedApprovals() {
        completedApprovals++;
    }

    /**
     * Increments rejected approvals.
     */
    public void incrementRejectedApprovals() {
        rejectedApprovals++;
    }

    /**
     * Increments delegated approvals.
     */
    public void incrementDelegatedApprovals() {
        delegatedApprovals++;
    }

    /**
     * Increments escalated approvals.
     */
    public void incrementEscalatedApprovals() {
        escalatedApprovals++;
    }

    /**
     * Increments reminder count.
     */
    public void incrementReminderCount() {
        reminderCount++;
    }

    /**
     * Increments action count.
     */
    public void incrementActionCount() {
        actionCount++;
    }

    /**
     * Increments approval attempts.
     */
    public void incrementApprovalAttempts() {
        approvalAttempts++;
    }

    /**
     * Increments reassignment count.
     */
    public void incrementReassignmentCount() {
        reassignmentCount++;
    }

    /**
     * Increments SLA breach count.
     */
    public void incrementSlaBreachCount() {
        slaBreachCount++;
    }

    /**
     * Returns execution progress percentage.
     *
     * @return completion percentage
     */
    public double getApprovalPercentage() {

        if (requiredApprovals == null || requiredApprovals == 0) {
            return 0D;
        }

        return (completedApprovals.doubleValue() * 100D)
                / requiredApprovals.doubleValue();
    }

    /**
     * Returns whether all required approvals
     * have been obtained.
     *
     * @return true if approval threshold reached
     */
    public boolean isFinalApprovalReached() {

        return completedApprovals != null
                && requiredApprovals != null
                && completedApprovals >= requiredApprovals;
    }

    /**
     * Returns whether pending approvals exist.
     *
     * @return true if approvals remain
     */
    public boolean hasPendingApprovals() {
        return pendingApprovals != null
                && pendingApprovals > 0;
    }

    /**
     * Updates the last action timestamp.
     */
    public void touch() {
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Updates the last action timestamp.
     *
     * @param actionTime timestamp
     */
    public void touch(LocalDateTime actionTime) {
        this.lastActionAt = actionTime;
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

    public ApprovalPolicyStage getApprovalPolicyStage() {
        return approvalPolicyStage;
    }

    public void setApprovalPolicyStage(
            ApprovalPolicyStage approvalPolicyStage) {
        this.approvalPolicyStage = approvalPolicyStage;
    }

    public UUID getStageUuid() {
        return stageUuid;
    }

    public void setStageUuid(UUID stageUuid) {
        this.stageUuid = stageUuid;
    }

    public Integer getStageOrder() {
        return stageOrder;
    }

    public void setStageOrder(Integer stageOrder) {
        this.stageOrder = stageOrder;
    }

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageDescription() {
        return stageDescription;
    }

    public void setStageDescription(String stageDescription) {
        this.stageDescription = stageDescription;
    }

    public ApprovalStageType getStageType() {
        return stageType;
    }

    public void setStageType(ApprovalStageType stageType) {
        this.stageType = stageType;
    }

    public ApprovalExecutionStageStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalExecutionStageStatus status) {
        this.status = status;
    }

    public ApprovalPriority getPriority() {
        return priority;
    }

    public void setPriority(ApprovalPriority priority) {
        this.priority = priority;
    }

    public ApprovalMode getApprovalMode() {
        return approvalMode;
    }

    public void setApprovalMode(ApprovalMode approvalMode) {
        this.approvalMode = approvalMode;
    }

    public Integer getRequiredApprovals() {
        return requiredApprovals;
    }

    public void setRequiredApprovals(Integer requiredApprovals) {
        this.requiredApprovals = requiredApprovals;
    }

    public Integer getCompletedApprovals() {
        return completedApprovals;
    }

    public void setCompletedApprovals(Integer completedApprovals) {
        this.completedApprovals = completedApprovals;
    }

    public Integer getRejectedApprovals() {
        return rejectedApprovals;
    }

    public void setRejectedApprovals(Integer rejectedApprovals) {
        this.rejectedApprovals = rejectedApprovals;
    }

    public Integer getPendingApprovals() {
        return pendingApprovals;
    }

    public void setPendingApprovals(Integer pendingApprovals) {
        this.pendingApprovals = pendingApprovals;
    }

    public Integer getDelegatedApprovals() {
        return delegatedApprovals;
    }

    public void setDelegatedApprovals(Integer delegatedApprovals) {
        this.delegatedApprovals = delegatedApprovals;
    }

    public Integer getEscalatedApprovals() {
        return escalatedApprovals;
    }

    public void setEscalatedApprovals(Integer escalatedApprovals) {
        this.escalatedApprovals = escalatedApprovals;
    }

    public Integer getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(Integer reminderCount) {
        this.reminderCount = reminderCount;
    }

    public Integer getActionCount() {
        return actionCount;
    }

    public void setActionCount(Integer actionCount) {
        this.actionCount = actionCount;
    }

    public Integer getApprovalAttempts() {
        return approvalAttempts;
    }

    public void setApprovalAttempts(Integer approvalAttempts) {
        this.approvalAttempts = approvalAttempts;
    }

    public Integer getReassignmentCount() {
        return reassignmentCount;
    }

    public void setReassignmentCount(Integer reassignmentCount) {
        this.reassignmentCount = reassignmentCount;
    }

    public Integer getSlaBreachCount() {
        return slaBreachCount;
    }

    public void setSlaBreachCount(Integer slaBreachCount) {
        this.slaBreachCount = slaBreachCount;
    }

    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
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

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
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

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getLastActionAt() {
        return lastActionAt;
    }

    public void setLastActionAt(LocalDateTime lastActionAt) {
        this.lastActionAt = lastActionAt;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates the runtime execution stage configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalRequest == null) {
            throw new IllegalStateException("Approval request is required.");
        }

        if (stageOrder == null || stageOrder < 1) {
            throw new IllegalStateException("Stage order must be greater than zero.");
        }

        if (stageCode == null || stageCode.isBlank()) {
            throw new IllegalStateException("Stage code is required.");
        }

        if (stageName == null || stageName.isBlank()) {
            throw new IllegalStateException("Stage name is required.");
        }

        if (stageType == null) {
            throw new IllegalStateException("Stage type is required.");
        }

        if (approvalMode == null) {
            throw new IllegalStateException("Approval mode is required.");
        }

        if (status == null) {
            throw new IllegalStateException("Execution status is required.");
        }

        if (requiredApprovals == null || requiredApprovals < 1) {
            throw new IllegalStateException(
                    "Required approvals must be greater than zero.");
        }

        if (completedApprovals > requiredApprovals) {
            throw new IllegalStateException(
                    "Completed approvals cannot exceed required approvals.");
        }

        if (pendingApprovals < 0) {
            throw new IllegalStateException(
                    "Pending approvals cannot be negative.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns the remaining approvals required.
     *
     * @return remaining approval count
     */
    public int getRemainingApprovals() {
        return Math.max(0, requiredApprovals - completedApprovals);
    }

    /**
     * Returns the approval deficit.
     *
     * @return approval deficit
     */
    public int getApprovalDeficit() {
        return getRemainingApprovals();
    }

    /**
     * Returns whether the approval threshold has been met.
     *
     * <p>
     * Supports all approval modes.
     *
     * @return true if the configured approval requirement has been satisfied
     */
    public boolean isApprovalThresholdMet() {

        return switch (approvalMode) {

            case ANY ->
                completedApprovals >= 1;

            case ALL ->
                completedApprovals >= requiredApprovals;

            case MAJORITY ->
                completedApprovals > (requiredApprovals / 2);

            case UNANIMOUS ->
                completedApprovals == requiredApprovals;

            case QUORUM ->
                completedApprovals >= requiredApprovals;

            default ->
                false;
        };
    }

    /**
     * Returns whether the stage can now be completed.
     *
     * @return true if completion criteria are satisfied
     */
    public boolean isReadyToComplete() {
        return isApprovalThresholdMet()
                && !completed
                && !rejected;
    }

    /**
     * Returns whether the stage completed successfully.
     *
     * @return true if completed successfully
     */
    public boolean isCompletedSuccessfully() {
        return completed
                && status == ApprovalExecutionStageStatus.COMPLETED;
    }

    /**
     * Returns whether execution has started.
     *
     * @return true if started
     */
    public boolean hasStarted() {
        return startedAt != null;
    }

    /**
     * Returns whether execution has finished.
     *
     * @return true if finished
     */
    public boolean hasFinished() {
        return completedAt != null;
    }

    /**
     * Returns whether the stage has expired.
     *
     * @return true if overdue
     */
    public boolean hasExpired() {

        return dueAt != null
                && LocalDateTime.now().isAfter(dueAt)
                && !completed;
    }

    /**
     * Returns whether the stage is awaiting approvals.
     *
     * @return true if pending
     */
    public boolean isPending() {
        return status == ApprovalExecutionStageStatus.PENDING
                || status == ApprovalExecutionStageStatus.READY;
    }

    /**
     * Returns whether the stage is actively executing.
     *
     * @return true if in progress
     */
    public boolean isInProgress() {
        return status == ApprovalExecutionStageStatus.IN_PROGRESS;
    }

    @Override
    public String toString() {

        return "ApprovalExecutionStage{" +
                "id=" + getId() +
                ", stageCode='" + stageCode + '\'' +
                ", stageName='" + stageName + '\'' +
                ", stageOrder=" + stageOrder +
                ", approvalMode=" + approvalMode +
                ", status=" + status +
                ", completedApprovals=" + completedApprovals +
                ", requiredApprovals=" + requiredApprovals +
                ", pendingApprovals=" + pendingApprovals +
                ", reminderCount=" + reminderCount +
                ", actionCount=" + actionCount +
                ", approvalAttempts=" + approvalAttempts +
                ", reassignmentCount=" + reassignmentCount +
                ", slaBreachCount=" + slaBreachCount +
                ", completed=" + completed +
                ", rejected=" + rejected +
                ", delegated=" + delegated +
                ", escalated=" + escalated +
                ", overdue=" + overdue +
                ", suspended=" + suspended +
                ", active=" + isActive() +
                '}';
    }
}