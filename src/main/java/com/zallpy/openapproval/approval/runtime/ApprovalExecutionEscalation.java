package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalEscalationStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import com.zallpy.openapproval.approval.policy.ApprovalEscalationPolicy;
import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Records an escalation that actually occurred during
 * approval execution.
 *
 * <p>
 * Unlike {@link ApprovalEscalationPolicy}, this entity
 * stores the runtime result of an executed escalation.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_escalations", indexes = {
        @Index(name = "idx_execution_escalation_execution", columnList = "approval_execution_id"),
        @Index(name = "idx_execution_escalation_stage", columnList = "approval_execution_stage_id"),
        @Index(name = "idx_execution_escalation_task", columnList = "approval_execution_task_id"),
        @Index(name = "idx_execution_escalation_policy", columnList = "escalation_policy_id"),
        @Index(name = "idx_execution_escalation_reference", columnList = "escalation_reference"),
        @Index(name = "idx_execution_escalation_active", columnList = "active")
})
public class ApprovalExecutionEscalation extends ActiveEntity {

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
     * Parent execution task.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_task_id", nullable = false)
    private ApprovalExecutionTask approvalExecutionTask;

    /**
     * Policy that initiated this escalation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escalation_policy_id")
    private ApprovalEscalationPolicy approvalEscalationPolicy;

    /**
     * Runtime escalation identifier.
     */
    @NotNull
    @Column(name = "escalation_uuid", nullable = false, unique = true)
    private UUID escalationUuid = UUID.randomUUID();

    /**
     * Business reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "escalation_reference", nullable = false, unique = true, length = 100)
    private String escalationReference;

    /**
     * Current execution status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ApprovalEscalationStatus status = ApprovalEscalationStatus.SCHEDULED;

    /**
     * User being escalated from.
     */
    @Size(max = 100)
    @Column(name = "from_approver_id", length = 100)
    private String fromApproverId;

    /**
     * Name of the original approver.
     */
    @Size(max = 200)
    @Column(name = "from_approver_name", length = 200)
    private String fromApproverName;

    /**
     * User receiving the escalation.
     */
    @Size(max = 100)
    @Column(name = "to_approver_id", length = 100)
    private String toApproverId;

    /**
     * Name of the escalated approver.
     */
    @Size(max = 200)
    @Column(name = "to_approver_name", length = 200)
    private String toApproverName;

    /**
     * Email of the escalated approver.
     */
    @Size(max = 255)
    @Column(name = "to_approver_email", length = 255)
    private String toApproverEmail;

    /**
     * Date and time the escalation was scheduled.
     */
    @NotNull
    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt = LocalDateTime.now();

    /**
     * Date and time the escalation was executed.
     */
    @Column(name = "escalated_at")
    private LocalDateTime escalatedAt;

    /**
     * Reason for the escalation.
     */
    @Size(max = 2000)
    @Column(name = "escalation_reason", length = 2000)
    private String escalationReason;

    /**
     * Failure reason if escalation could not be completed.
     */
    @Size(max = 2000)
    @Column(name = "failure_reason", length = 2000)
    private String failureReason;

    /**
     * Additional runtime metadata.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Default constructor.
     */
    public ApprovalExecutionEscalation() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionEscalation other)) {
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
     * Marks this escalation as queued.
     */
    public void queue() {
        this.status = ApprovalEscalationStatus.QUEUED;
    }

    /**
     * Marks this escalation as processing.
     */
    public void markProcessing() {
        this.status = ApprovalEscalationStatus.PROCESSING;
    }

    /**
     * Marks this escalation as completed.
     */
    public void complete() {
        this.status = ApprovalEscalationStatus.COMPLETED;
        this.escalatedAt = LocalDateTime.now();
        this.failureReason = null;
    }

    /**
     * Marks this escalation as failed.
     *
     * @param reason failure reason
     */
    public void fail(final String reason) {

        this.status = ApprovalEscalationStatus.FAILED;
        this.failureReason = reason;
    }

    /**
     * Cancels this escalation.
     */
    public void cancel() {
        this.status = ApprovalEscalationStatus.CANCELLED;
    }

    /**
     * Marks this escalation as expired.
     */
    public void expire() {
        this.status = ApprovalEscalationStatus.EXPIRED;
    }

    /**
     * Updates the escalation reason.
     *
     * @param escalationReason escalation reason
     */
    public void updateEscalationReason(
            final String escalationReason) {
        this.escalationReason = escalationReason;
    }

    /**
     * Updates the metadata.
     *
     * @param metadata metadata
     */
    public void updateMetadata(final String metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns whether the escalation has completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return status == ApprovalEscalationStatus.COMPLETED;
    }

    /**
     * Returns whether the escalation failed.
     *
     * @return true if failed
     */
    public boolean hasFailed() {
        return status == ApprovalEscalationStatus.FAILED;
    }

    /**
     * Returns whether this escalation has a reason.
     *
     * @return true if a reason exists
     */
    public boolean hasEscalationReason() {
        return escalationReason != null
                && !escalationReason.isBlank();
    }

    /**
     * Returns whether this escalation has a failure reason.
     *
     * @return true if a failure reason exists
     */
    public boolean hasFailureReason() {
        return failureReason != null
                && !failureReason.isBlank();
    }

    /**
     * Returns whether metadata has been supplied.
     *
     * @return true if metadata exists
     */
    public boolean hasMetadata() {
        return metadata != null
                && !metadata.isBlank();
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalExecution getApprovalExecution() {
        return approvalExecution;
    }

    public void setApprovalExecution(
            ApprovalExecution approvalExecution) {
        this.approvalExecution = approvalExecution;
    }

    public ApprovalExecutionStage getApprovalExecutionStage() {
        return approvalExecutionStage;
    }

    public void setApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage) {
        this.approvalExecutionStage = approvalExecutionStage;
    }

    public ApprovalExecutionTask getApprovalExecutionTask() {
        return approvalExecutionTask;
    }

    public void setApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask) {
        this.approvalExecutionTask = approvalExecutionTask;
    }

    public ApprovalEscalationPolicy getApprovalEscalationPolicy() {
        return approvalEscalationPolicy;
    }

    public void setApprovalEscalationPolicy(
            ApprovalEscalationPolicy approvalEscalationPolicy) {
        this.approvalEscalationPolicy = approvalEscalationPolicy;
    }

    public UUID getEscalationUuid() {
        return escalationUuid;
    }

    public void setEscalationUuid(UUID escalationUuid) {
        this.escalationUuid = escalationUuid;
    }

    public String getEscalationReference() {
        return escalationReference;
    }

    public void setEscalationReference(String escalationReference) {
        this.escalationReference = escalationReference;
    }

    public ApprovalEscalationStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalEscalationStatus status) {
        this.status = status;
    }

    public String getFromApproverId() {
        return fromApproverId;
    }

    public void setFromApproverId(String fromApproverId) {
        this.fromApproverId = fromApproverId;
    }

    public String getFromApproverName() {
        return fromApproverName;
    }

    public void setFromApproverName(String fromApproverName) {
        this.fromApproverName = fromApproverName;
    }

    public String getToApproverId() {
        return toApproverId;
    }

    public void setToApproverId(String toApproverId) {
        this.toApproverId = toApproverId;
    }

    public String getToApproverName() {
        return toApproverName;
    }

    public void setToApproverName(String toApproverName) {
        this.toApproverName = toApproverName;
    }

    public String getToApproverEmail() {
        return toApproverEmail;
    }

    public void setToApproverEmail(String toApproverEmail) {
        this.toApproverEmail = toApproverEmail;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getEscalatedAt() {
        return escalatedAt;
    }

    public void setEscalatedAt(LocalDateTime escalatedAt) {
        this.escalatedAt = escalatedAt;
    }

    public String getEscalationReason() {
        return escalationReason;
    }

    public void setEscalationReason(String escalationReason) {
        this.escalationReason = escalationReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates the escalation configuration.
     *
     * @throws IllegalStateException if the escalation is invalid
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

        if (approvalExecutionTask == null) {
            throw new IllegalStateException(
                    "Approval execution task is required.");
        }

        if (escalationReference == null
                || escalationReference.isBlank()) {

            throw new IllegalStateException(
                    "Escalation reference is required.");
        }

        if (approvalEscalationPolicy == null) {
            throw new IllegalStateException(
                    "Approval escalation policy is required.");
        }

        if (status == null) {
            throw new IllegalStateException(
                    "Escalation status is required.");
        }

        if (scheduledAt == null) {
            throw new IllegalStateException(
                    "Scheduled time is required.");
        }

        if (toApproverId == null
                || toApproverId.isBlank()) {

            throw new IllegalStateException(
                    "Target approver is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this escalation belongs to an execution.
     *
     * @return true if associated with an execution
     */
    public boolean hasExecution() {
        return approvalExecution != null;
    }

    /**
     * Returns whether this escalation belongs to a stage.
     *
     * @return true if associated with a stage
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether this escalation belongs to a task.
     *
     * @return true if associated with a task
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether this escalation originated from a policy.
     *
     * @return true if a policy exists
     */
    public boolean hasEscalationPolicy() {
        return approvalEscalationPolicy != null;
    }

    /**
     * Returns whether an escalation has been executed.
     *
     * @return true if completed
     */
    public boolean hasBeenEscalated() {
        return escalatedAt != null;
    }

    /**
     * Returns whether a destination approver has been assigned.
     *
     * @return true if destination exists
     */
    public boolean hasTargetApprover() {
        return toApproverId != null
                && !toApproverId.isBlank();
    }

    /**
     * Returns whether an origin approver exists.
     *
     * @return true if origin exists
     */
    public boolean hasSourceApprover() {
        return fromApproverId != null
                && !fromApproverId.isBlank();
    }

    @Override
    public String toString() {

        return "ApprovalExecutionEscalation{" +
                "id=" + getId() +
                ", escalationReference='" + escalationReference + '\'' +
                ", status=" + status +
                ", fromApproverId='" + fromApproverId + '\'' +
                ", toApproverId='" + toApproverId + '\'' +
                ", active=" + isActive() +
                '}';
    }

}