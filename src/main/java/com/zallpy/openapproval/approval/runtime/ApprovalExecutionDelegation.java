package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalDelegationReason;
import com.zallpy.openapproval.approval.enums.ApprovalDelegationStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
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
 * Records a delegation that occurred during approval execution.
 *
 * <p>
 * This entity represents the runtime execution of a delegation,
 * capturing who delegated the approval, who received it,
 * and the lifecycle of that delegation.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_delegations", indexes = {
        @Index(name = "idx_execution_delegation_execution",
                columnList = "approval_execution_id"),
        @Index(name = "idx_execution_delegation_stage",
                columnList = "approval_execution_stage_id"),
        @Index(name = "idx_execution_delegation_task",
                columnList = "approval_execution_task_id"),
        @Index(name = "idx_execution_delegation_reference",
                columnList = "delegation_reference"),
        @Index(name = "idx_execution_delegation_active",
                columnList = "active")
})
public class ApprovalExecutionDelegation extends ActiveEntity {

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
     * Unique delegation identifier.
     */
    @NotNull
    @Column(name = "delegation_uuid",
            nullable = false,
            unique = true)
    private UUID delegationUuid = UUID.randomUUID();

    /**
     * Business reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "delegation_reference",
            nullable = false,
            unique = true,
            length = 100)
    private String delegationReference;

    /**
     * Current delegation status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            nullable = false,
            length = 40)
    private ApprovalDelegationStatus status =
            ApprovalDelegationStatus.PENDING;

    /**
     * Business reason for the delegation.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delegation_reason",
            nullable = false,
            length = 50)
    private ApprovalDelegationReason delegationReason;

    /**
     * Original approver.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "from_approver_id",
            nullable = false,
            length = 100)
    private String fromApproverId;

    /**
     * Original approver name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "from_approver_name",
            nullable = false,
            length = 200)
    private String fromApproverName;

    /**
     * Delegate approver.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "to_approver_id",
            nullable = false,
            length = 100)
    private String toApproverId;

    /**
     * Delegate approver name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "to_approver_name",
            nullable = false,
            length = 200)
    private String toApproverName;

    /**
     * Delegate email.
     */
    @Size(max = 255)
    @Column(name = "to_approver_email",
            length = 255)
    private String toApproverEmail;

        /**
     * Date and time the delegation occurred.
     */
    @NotNull
    @Column(name = "delegated_at", nullable = false)
    private LocalDateTime delegatedAt = LocalDateTime.now();

    /**
     * Date and time the delegation was accepted.
     */
    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    /**
     * Date and time the delegated approval completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Delegation note.
     */
    @Size(max = 2000)
    @Column(name = "delegation_note", length = 2000)
    private String delegationNote;

    /**
     * Additional metadata.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Default constructor.
     */
    public ApprovalExecutionDelegation() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionDelegation other)) {
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
     * Accepts this delegation.
     */
    public void accept() {
        this.status = ApprovalDelegationStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
    }

    /**
     * Declines this delegation.
     */
    public void decline() {
        this.status = ApprovalDelegationStatus.DECLINED;
    }

    /**
     * Completes this delegation.
     */
    public void complete() {
        this.status = ApprovalDelegationStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    /**
     * Revokes this delegation.
     */
    public void revoke() {
        this.status = ApprovalDelegationStatus.REVOKED;
    }

    /**
     * Expires this delegation.
     */
    public void expire() {
        this.status = ApprovalDelegationStatus.EXPIRED;
    }

    /**
     * Cancels this delegation.
     */
    public void cancel() {
        this.status = ApprovalDelegationStatus.CANCELLED;
    }

    /**
     * Updates the delegation note.
     *
     * @param delegationNote delegation note
     */
    public void updateDelegationNote(
            final String delegationNote) {
        this.delegationNote = delegationNote;
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
     * Returns whether this delegation has been accepted.
     *
     * @return true if accepted
     */
    public boolean isAccepted() {
        return status == ApprovalDelegationStatus.ACCEPTED;
    }

    /**
     * Returns whether this delegation has completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return status == ApprovalDelegationStatus.COMPLETED;
    }

    /**
     * Returns whether a delegation note exists.
     *
     * @return true if a note exists
     */
    public boolean hasDelegationNote() {
        return delegationNote != null
                && !delegationNote.isBlank();
    }

    /**
     * Returns whether metadata exists.
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

    public UUID getDelegationUuid() {
        return delegationUuid;
    }

    public void setDelegationUuid(UUID delegationUuid) {
        this.delegationUuid = delegationUuid;
    }

    public String getDelegationReference() {
        return delegationReference;
    }

    public void setDelegationReference(String delegationReference) {
        this.delegationReference = delegationReference;
    }

    public ApprovalDelegationStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalDelegationStatus status) {
        this.status = status;
    }

    public ApprovalDelegationReason getDelegationReason() {
        return delegationReason;
    }

    public void setDelegationReason(
            ApprovalDelegationReason delegationReason) {
        this.delegationReason = delegationReason;
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

    public LocalDateTime getDelegatedAt() {
        return delegatedAt;
    }

    public void setDelegatedAt(LocalDateTime delegatedAt) {
        this.delegatedAt = delegatedAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getDelegationNote() {
        return delegationNote;
    }

    public void setDelegationNote(String delegationNote) {
        this.delegationNote = delegationNote;
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
     * Validates the delegation configuration.
     *
     * @throws IllegalStateException if the delegation is invalid
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

        if (delegationReference == null
                || delegationReference.isBlank()) {

            throw new IllegalStateException(
                    "Delegation reference is required.");
        }

        if (status == null) {
            throw new IllegalStateException(
                    "Delegation status is required.");
        }

        if (delegationReason == null) {
            throw new IllegalStateException(
                    "Delegation reason is required.");
        }

        if (fromApproverId == null
                || fromApproverId.isBlank()) {

            throw new IllegalStateException(
                    "Source approver is required.");
        }

        if (toApproverId == null
                || toApproverId.isBlank()) {

            throw new IllegalStateException(
                    "Delegate approver is required.");
        }

        if (delegatedAt == null) {
            throw new IllegalStateException(
                    "Delegation time is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this delegation belongs to an execution.
     *
     * @return true if associated with an execution
     */
    public boolean hasExecution() {
        return approvalExecution != null;
    }

    /**
     * Returns whether this delegation belongs to a stage.
     *
     * @return true if associated with a stage
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether this delegation belongs to a task.
     *
     * @return true if associated with a task
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether this delegation has been accepted.
     *
     * @return true if accepted
     */
    public boolean hasBeenAccepted() {
        return acceptedAt != null;
    }

    /**
     * Returns whether this delegation has been completed.
     *
     * @return true if completed
     */
    public boolean hasBeenCompleted() {
        return completedAt != null;
    }

    /**
     * Returns whether a delegate email exists.
     *
     * @return true if delegate email exists
     */
    public boolean hasDelegateEmail() {
        return toApproverEmail != null
                && !toApproverEmail.isBlank();
    }

    /**
     * Returns whether the delegation is self-delegation.
     *
     * @return true if source and destination are the same
     */
    public boolean isSelfDelegation() {

        return fromApproverId != null
                && fromApproverId.equals(toApproverId);
    }

    @Override
    public String toString() {

        return "ApprovalExecutionDelegation{" +
                "id=" + getId() +
                ", delegationReference='" + delegationReference + '\'' +
                ", status=" + status +
                ", delegationReason=" + delegationReason +
                ", fromApproverId='" + fromApproverId + '\'' +
                ", toApproverId='" + toApproverId + '\'' +
                ", active=" + isActive() +
                '}';
    }
}