package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;
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
 * Immutable audit record for approval execution.
 *
 * <p>
 * Every significant runtime event should generate
 * exactly one audit record.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_execution_audits", indexes = {
        @Index(name = "idx_execution_audit_execution",
                columnList = "approval_execution_id"),
        @Index(name = "idx_execution_audit_stage",
                columnList = "approval_execution_stage_id"),
        @Index(name = "idx_execution_audit_task",
                columnList = "approval_execution_task_id"),
        @Index(name = "idx_execution_audit_reference",
                columnList = "audit_reference"),
        @Index(name = "idx_execution_audit_action",
                columnList = "audit_action"),
        @Index(name = "idx_execution_audit_active",
                columnList = "active")
})
public class ApprovalExecutionAudit extends ActiveEntity {

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
     * Related execution stage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_execution_stage_id")
    private ApprovalExecutionStage approvalExecutionStage;

    /**
     * Related execution task.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_execution_task_id")
    private ApprovalExecutionTask approvalExecutionTask;

    /**
     * Unique audit identifier.
     */
    @NotNull
    @Column(name = "audit_uuid", nullable = false, unique = true)
    private UUID auditUuid = UUID.randomUUID();

    /**
     * Business audit reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "audit_reference",
            nullable = false,
            unique = true,
            length = 100)
    private String auditReference;

    /**
     * Audit action performed.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "audit_action",
            nullable = false,
            length = 50)
    private ApprovalAuditAction auditAction;

    /**
     * Actor type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type",
            nullable = false,
            length = 40)
    private ApprovalAuditActorType actorType;

    /**
     * Actor identifier.
     */
    @Size(max = 100)
    @Column(name = "actor_id", length = 100)
    private String actorId;

    /**
     * Actor name.
     */
    @Size(max = 200)
    @Column(name = "actor_name", length = 200)
    private String actorName;

    /**
     * Actor email.
     */
    @Size(max = 255)
    @Column(name = "actor_email", length = 255)
    private String actorEmail;

    /**
     * Audit summary.
     */
    @NotBlank
    @Size(max = 500)
    @Column(name = "summary",
            nullable = false,
            length = 500)
    private String summary;

    /**
     * Detailed audit description.
     */
    @Size(max = 4000)
    @Column(name = "details", length = 4000)
    private String details;


        /**
     * Date and time the audit record was created.
     */
    @NotNull
    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt = LocalDateTime.now();

    /**
     * Client IP address.
     */
    @Size(max = 100)
    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    /**
     * User agent.
     */
    @Size(max = 1000)
    @Column(name = "user_agent", length = 1000)
    private String userAgent;

    /**
     * Additional metadata.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Default constructor.
     */
    public ApprovalExecutionAudit() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionAudit other)) {
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
     * Updates the audit summary.
     *
     * @param summary audit summary
     */
    public void updateSummary(final String summary) {
        this.summary = summary;
    }

    /**
     * Updates the audit details.
     *
     * @param details audit details
     */
    public void updateDetails(final String details) {
        this.details = details;
    }

    /**
     * Updates the client IP address.
     *
     * @param ipAddress client IP address
     */
    public void updateIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Updates the user agent.
     *
     * @param userAgent user agent
     */
    public void updateUserAgent(final String userAgent) {
        this.userAgent = userAgent;
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
     * Returns whether this audit was generated by a user.
     *
     * @return true if generated by a user
     */
    public boolean isUserAction() {
        return actorType == ApprovalAuditActorType.USER;
    }

    /**
     * Returns whether this audit was generated by the system.
     *
     * @return true if generated by the system
     */
    public boolean isSystemAction() {
        return actorType == ApprovalAuditActorType.SYSTEM;
    }

    /**
     * Returns whether this audit contains details.
     *
     * @return true if details exist
     */
    public boolean hasDetails() {
        return details != null
                && !details.isBlank();
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

    public UUID getAuditUuid() {
        return auditUuid;
    }

    public void setAuditUuid(UUID auditUuid) {
        this.auditUuid = auditUuid;
    }

    public String getAuditReference() {
        return auditReference;
    }

    public void setAuditReference(String auditReference) {
        this.auditReference = auditReference;
    }

    public ApprovalAuditAction getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(
            ApprovalAuditAction auditAction) {
        this.auditAction = auditAction;
    }

    public ApprovalAuditActorType getActorType() {
        return actorType;
    }

    public void setActorType(
            ApprovalAuditActorType actorType) {
        this.actorType = actorType;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
     * Validates this audit record.
     *
     * @throws IllegalStateException if the audit record is invalid
     */
    public void validateConfiguration() {

        if (approvalExecution == null) {
            throw new IllegalStateException(
                    "Approval execution is required.");
        }

        if (auditReference == null
                || auditReference.isBlank()) {

            throw new IllegalStateException(
                    "Audit reference is required.");
        }

        if (auditAction == null) {
            throw new IllegalStateException(
                    "Audit action is required.");
        }

        if (actorType == null) {
            throw new IllegalStateException(
                    "Actor type is required.");
        }

        if (summary == null
                || summary.isBlank()) {

            throw new IllegalStateException(
                    "Audit summary is required.");
        }

        if (recordedAt == null) {
            throw new IllegalStateException(
                    "Recorded date/time is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this audit is associated with a stage.
     *
     * @return true if a stage exists
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether this audit is associated with a task.
     *
     * @return true if a task exists
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether an actor has been recorded.
     *
     * @return true if an actor identifier exists
     */
    public boolean hasActor() {
        return actorId != null
                && !actorId.isBlank();
    }

    /**
     * Returns whether an actor email exists.
     *
     * @return true if an actor email exists
     */
    public boolean hasActorEmail() {
        return actorEmail != null
                && !actorEmail.isBlank();
    }

    /**
     * Returns whether an IP address has been recorded.
     *
     * @return true if an IP address exists
     */
    public boolean hasIpAddress() {
        return ipAddress != null
                && !ipAddress.isBlank();
    }

    /**
     * Returns whether a user agent has been recorded.
     *
     * @return true if a user agent exists
     */
    public boolean hasUserAgent() {
        return userAgent != null
                && !userAgent.isBlank();
    }

    @Override
    public String toString() {

        return "ApprovalExecutionAudit{" +
                "id=" + getId() +
                ", auditReference='" + auditReference + '\'' +
                ", auditAction=" + auditAction +
                ", actorType=" + actorType +
                ", actorId='" + actorId + '\'' +
                ", recordedAt=" + recordedAt +
                ", active=" + isActive() +
                '}';
    }
}