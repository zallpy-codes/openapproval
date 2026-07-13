package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalReminderChannel;
import com.zallpy.openapproval.approval.enums.ApprovalReminderStatus;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a reminder generated for an approval execution.
 *
 * <p>
 * Reminders are created automatically by the approval engine
 * whenever an approval task exceeds its reminder interval.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(
        name = "approval_execution_reminders",
        indexes = {
                @Index(
                        name = "idx_execution_reminder_execution",
                        columnList = "approval_execution_id"
                ),
                @Index(
                        name = "idx_execution_reminder_stage",
                        columnList = "approval_execution_stage_id"
                ),
                @Index(
                        name = "idx_execution_reminder_task",
                        columnList = "approval_execution_task_id"
                ),
                @Index(
                        name = "idx_execution_reminder_reference",
                        columnList = "reminder_reference"
                ),
                @Index(
                        name = "idx_execution_reminder_active",
                        columnList = "active"
                )
        }
)
public class ApprovalExecutionReminder extends ActiveEntity {

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
     * Unique reminder identifier.
     */
    @NotNull
    @Column(name = "reminder_uuid", nullable = false, unique = true)
    private UUID reminderUuid = UUID.randomUUID();

    /**
     * Human-readable reminder reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(
            name = "reminder_reference",
            nullable = false,
            unique = true,
            length = 100
    )
    private String reminderReference;

    /**
     * Reminder delivery channel.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 40)
    private ApprovalReminderChannel channel =
            ApprovalReminderChannel.EMAIL;

    /**
     * Current reminder status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ApprovalReminderStatus status =
            ApprovalReminderStatus.SCHEDULED;

    /**
     * Reminder sequence number.
     */
    @NotNull
    @Min(1)
    @Column(name = "reminder_number", nullable = false)
    private Integer reminderNumber = 1;

    /**
     * Recipient identifier.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "recipient_id", nullable = false, length = 100)
    private String recipientId;

    /**
     * Recipient display name.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "recipient_name", nullable = false, length = 200)
    private String recipientName;

    /**
     * Recipient email address.
     */
    @Size(max = 255)
    @Column(name = "recipient_email", length = 255)
    private String recipientEmail;

        /**
     * Date and time the reminder is scheduled.
     */
    @NotNull
    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt = LocalDateTime.now();

    /**
     * Date and time the reminder was sent.
     */
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    /**
     * Delivery failure reason.
     */
    @Size(max = 2000)
    @Column(name = "failure_reason", length = 2000)
    private String failureReason;

    /**
     * Additional reminder metadata.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Default constructor.
     */
    public ApprovalExecutionReminder() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionReminder other)) {
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
     * Marks the reminder as queued.
     */
    public void queue() {
        this.status = ApprovalReminderStatus.QUEUED;
    }

    /**
     * Marks the reminder as being sent.
     */
    public void markAsSending() {
        this.status = ApprovalReminderStatus.SENDING;
    }

    /**
     * Marks the reminder as successfully sent.
     */
    public void markAsSent() {
        this.status = ApprovalReminderStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.failureReason = null;
    }

    /**
     * Marks the reminder as failed.
     *
     * @param reason failure reason
     */
    public void markAsFailed(final String reason) {

        this.status = ApprovalReminderStatus.FAILED;
        this.failureReason = reason;
    }

    /**
     * Cancels this reminder.
     */
    public void cancel() {
        this.status = ApprovalReminderStatus.CANCELLED;
    }

    /**
     * Marks this reminder as expired.
     */
    public void expire() {
        this.status = ApprovalReminderStatus.EXPIRED;
    }

    /**
     * Updates the reminder metadata.
     *
     * @param metadata metadata
     */
    public void updateMetadata(final String metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns whether the reminder has been sent.
     *
     * @return true if sent
     */
    public boolean isSent() {
        return status == ApprovalReminderStatus.SENT;
    }

    /**
     * Returns whether the reminder has failed.
     *
     * @return true if failed
     */
    public boolean hasFailed() {
        return status == ApprovalReminderStatus.FAILED;
    }

    /**
     * Returns whether the reminder is waiting to be sent.
     *
     * @return true if queued
     */
    public boolean isQueued() {
        return status == ApprovalReminderStatus.QUEUED;
    }

    /**
     * Returns whether a failure reason exists.
     *
     * @return true if present
     */
    public boolean hasFailureReason() {
        return failureReason != null
                && !failureReason.isBlank();
    }

    /**
     * Returns whether metadata exists.
     *
     * @return true if present
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

    public UUID getReminderUuid() {
        return reminderUuid;
    }

    public void setReminderUuid(UUID reminderUuid) {
        this.reminderUuid = reminderUuid;
    }

    public String getReminderReference() {
        return reminderReference;
    }

    public void setReminderReference(String reminderReference) {
        this.reminderReference = reminderReference;
    }

    public ApprovalReminderChannel getChannel() {
        return channel;
    }

    public void setChannel(ApprovalReminderChannel channel) {
        this.channel = channel;
    }

    public ApprovalReminderStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalReminderStatus status) {
        this.status = status;
    }

    public Integer getReminderNumber() {
        return reminderNumber;
    }

    public void setReminderNumber(Integer reminderNumber) {
        this.reminderNumber = reminderNumber;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
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
     * Validates the reminder configuration.
     *
     * @throws IllegalStateException if the reminder is invalid
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

        if (reminderReference == null
                || reminderReference.isBlank()) {

            throw new IllegalStateException(
                    "Reminder reference is required.");
        }

        if (channel == null) {
            throw new IllegalStateException(
                    "Reminder channel is required.");
        }

        if (status == null) {
            throw new IllegalStateException(
                    "Reminder status is required.");
        }

        if (reminderNumber == null
                || reminderNumber < 1) {

            throw new IllegalStateException(
                    "Reminder number must be greater than zero.");
        }

        if (recipientId == null
                || recipientId.isBlank()) {

            throw new IllegalStateException(
                    "Recipient ID is required.");
        }

        if (recipientName == null
                || recipientName.isBlank()) {

            throw new IllegalStateException(
                    "Recipient name is required.");
        }

        if (scheduledAt == null) {
            throw new IllegalStateException(
                    "Scheduled time is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this reminder belongs to an execution.
     *
     * @return true if associated with an execution
     */
    public boolean hasExecution() {
        return approvalExecution != null;
    }

    /**
     * Returns whether this reminder belongs to a stage.
     *
     * @return true if associated with a stage
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether this reminder belongs to a task.
     *
     * @return true if associated with a task
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether an email recipient has been configured.
     *
     * @return true if recipient email exists
     */
    public boolean hasRecipientEmail() {
        return recipientEmail != null
                && !recipientEmail.isBlank();
    }

    /**
     * Returns whether the reminder has been delivered.
     *
     * @return true if sent successfully
     */
    public boolean hasBeenSent() {
        return sentAt != null;
    }

    @Override
    public String toString() {

        return "ApprovalExecutionReminder{" +
                "id=" + getId() +
                ", reminderReference='" + reminderReference + '\'' +
                ", channel=" + channel +
                ", status=" + status +
                ", reminderNumber=" + reminderNumber +
                ", recipientId='" + recipientId + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", active=" + isActive() +
                '}';
    }
}