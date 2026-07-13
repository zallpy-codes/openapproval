package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalPolicyAuditAction;
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

/**
 * Audit record for approval policy configuration.
 *
 * <p>
 * This entity records administrative changes made to approval policies,
 * policy versions and related configuration entities.
 *
 * <p>
 * Unlike runtime approval history, this audit trail focuses on
 * configuration management and governance.
 *
 * Examples:
 * <ul>
 * <li>Policy updated</li>
 * <li>Stage added</li>
 * <li>Approver removed</li>
 * <li>Reminder modified</li>
 * <li>Version published</li>
 * <li>Schedule changed</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_audits", indexes = {
        @Index(name = "idx_policy_audit_policy", columnList = "policy_id"),
        @Index(name = "idx_policy_audit_version", columnList = "policy_version_id"),
        @Index(name = "idx_policy_audit_action", columnList = "audit_action"),
        @Index(name = "idx_policy_audit_performed_at", columnList = "performed_at"),
        @Index(name = "idx_policy_audit_active", columnList = "active")
})
public class ApprovalPolicyAudit extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Associated approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Related policy version.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_version_id")
    private ApprovalPolicyVersion approvalPolicyVersion;

    /**
     * Audit action performed.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "audit_action", nullable = false, length = 50)
    private ApprovalPolicyAuditAction auditAction;

    /**
     * Entity name.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "entity_name", nullable = false, length = 100)
    private String entityName;

    /**
     * Entity identifier.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;

    /**
     * Previous serialized value.
     */
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    /**
     * New serialized value.
     */
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    /**
     * User performing the action.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "performed_by", nullable = false, length = 100)
    private String performedBy;

    /**
     * Execution timestamp.
     */
    @NotNull
    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    /**
     * Source IP address.
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
     * Additional remarks.
     */
    @Size(max = 2000)
    @Column(name = "remarks", length = 2000)
    private String remarks;

    /**
     * Indicates whether the audited operation
     * completed successfully.
     */
    @Column(name = "successful", nullable = false)
    private boolean successful = true;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyAudit other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Default constructor.
     */
    public ApprovalPolicyAudit() {
    }

    /**
     * Returns whether this audit record was successful.
     *
     * @return true if successful
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Returns whether an old value exists.
     *
     * @return true if old value exists
     */
    public boolean hasOldValue() {
        return oldValue != null
                && !oldValue.isBlank();
    }

    /**
     * Returns whether a new value exists.
     *
     * @return true if new value exists
     */
    public boolean hasNewValue() {
        return newValue != null
                && !newValue.isBlank();
    }

    /**
     * Returns whether remarks exist.
     *
     * @return true if remarks exist
     */
    public boolean hasRemarks() {
        return remarks != null
                && !remarks.isBlank();
    }

    /**
     * Returns whether this audit belongs
     * to a policy version change.
     *
     * @return true if version audit
     */
    public boolean isVersionAudit() {
        return approvalPolicyVersion != null;
    }

    /**
     * Returns whether this audit represents
     * a creation action.
     *
     * @return true if creation audit
     */
    public boolean isCreationAction() {

        return auditAction == ApprovalPolicyAuditAction.CREATE_POLICY
                || auditAction == ApprovalPolicyAuditAction.CREATE_STAGE
                || auditAction == ApprovalPolicyAuditAction.CREATE_CONDITION
                || auditAction == ApprovalPolicyAuditAction.CREATE_NOTIFICATION
                || auditAction == ApprovalPolicyAuditAction.CREATE_REMINDER
                || auditAction == ApprovalPolicyAuditAction.CREATE_ESCALATION
                || auditAction == ApprovalPolicyAuditAction.CREATE_VERSION
                || auditAction == ApprovalPolicyAuditAction.CREATE_SCHEDULE
                || auditAction == ApprovalPolicyAuditAction.CREATE_VARIABLE;
    }

    /**
     * Returns whether this audit represents
     * an update action.
     *
     * @return true if update audit
     */
    public boolean isUpdateAction() {

        return auditAction == ApprovalPolicyAuditAction.UPDATE_POLICY
                || auditAction == ApprovalPolicyAuditAction.UPDATE_STAGE
                || auditAction == ApprovalPolicyAuditAction.UPDATE_CONDITION
                || auditAction == ApprovalPolicyAuditAction.UPDATE_NOTIFICATION
                || auditAction == ApprovalPolicyAuditAction.UPDATE_REMINDER
                || auditAction == ApprovalPolicyAuditAction.UPDATE_ESCALATION
                || auditAction == ApprovalPolicyAuditAction.UPDATE_SCHEDULE
                || auditAction == ApprovalPolicyAuditAction.UPDATE_VARIABLE;
    }

    /**
     * Returns whether this audit represents
     * a deletion action.
     *
     * @return true if deletion audit
     */
    public boolean isDeletionAction() {

        return auditAction == ApprovalPolicyAuditAction.DELETE_POLICY
                || auditAction == ApprovalPolicyAuditAction.DELETE_STAGE
                || auditAction == ApprovalPolicyAuditAction.DELETE_CONDITION
                || auditAction == ApprovalPolicyAuditAction.DELETE_NOTIFICATION
                || auditAction == ApprovalPolicyAuditAction.DELETE_REMINDER
                || auditAction == ApprovalPolicyAuditAction.DELETE_ESCALATION
                || auditAction == ApprovalPolicyAuditAction.DELETE_SCHEDULE
                || auditAction == ApprovalPolicyAuditAction.DELETE_VARIABLE;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    public void setApprovalPolicy(ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    public ApprovalPolicyVersion getApprovalPolicyVersion() {
        return approvalPolicyVersion;
    }

    public void setApprovalPolicyVersion(
            ApprovalPolicyVersion approvalPolicyVersion) {

        this.approvalPolicyVersion = approvalPolicyVersion;
    }

    public ApprovalPolicyAuditAction getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(
            ApprovalPolicyAuditAction auditAction) {

        this.auditAction = auditAction;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Validates this audit record configuration.
     *
     * @throws IllegalStateException if the audit record is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null) {
            throw new IllegalStateException(
                    "Approval policy is required.");
        }

        if (auditAction == null) {
            throw new IllegalStateException(
                    "Audit action is required.");
        }

        if (entityName == null || entityName.isBlank()) {
            throw new IllegalStateException(
                    "Entity name is required.");
        }

        if (entityId == null || entityId.isBlank()) {
            throw new IllegalStateException(
                    "Entity identifier is required.");
        }

        if (performedBy == null || performedBy.isBlank()) {
            throw new IllegalStateException(
                    "Performed by user is required.");
        }

        if (performedAt == null) {
            throw new IllegalStateException(
                    "Performed timestamp is required.");
        }

        if (isUpdateAction()
                && !hasOldValue()
                && !hasNewValue()) {

            throw new IllegalStateException(
                    "Update audit must contain old value or new value.");
        }

        if (isVersionAudit()
                && approvalPolicyVersion == null) {

            throw new IllegalStateException(
                    "Version audit must reference a policy version.");
        }
    }

    /**
     * Returns whether this audit record contains
     * before and after values.
     *
     * @return true if change snapshot exists
     */
    public boolean containsChangeSnapshot() {

        return hasOldValue()
                && hasNewValue();
    }

    /**
     * Returns whether this audit represents
     * a lifecycle operation.
     *
     * @return true if lifecycle action
     */
    public boolean isLifecycleAction() {

        return auditAction == ApprovalPolicyAuditAction.PUBLISH_VERSION
                || auditAction == ApprovalPolicyAuditAction.ACTIVATE_VERSION
                || auditAction == ApprovalPolicyAuditAction.RETIRE_VERSION
                || auditAction == ApprovalPolicyAuditAction.ARCHIVE_VERSION;
    }

    /**
     * Returns whether this audit record
     * was generated from an import operation.
     *
     * @return true if imported
     */
    public boolean isImportOperation() {
        return auditAction == ApprovalPolicyAuditAction.IMPORT_POLICY;
    }

    /**
     * Returns whether this audit record
     * was generated from an export operation.
     *
     * @return true if exported
     */
    public boolean isExportOperation() {
        return auditAction == ApprovalPolicyAuditAction.EXPORT_POLICY;
    }

    /**
     * Marks the audit operation as successful.
     */
    public void markSuccessful() {
        this.successful = true;
    }

    /**
     * Marks the audit operation as failed.
     */
    public void markFailed() {
        this.successful = false;
    }

    @Override
    public String toString() {
        return "ApprovalPolicyAudit{" +
                "id=" + getId() +
                ", auditAction=" + auditAction +
                ", entityName='" + entityName + '\'' +
                ", entityId='" + entityId + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", performedAt=" + performedAt +
                ", successful=" + successful +
                ", active=" + isActive() +
                '}';
    }

}