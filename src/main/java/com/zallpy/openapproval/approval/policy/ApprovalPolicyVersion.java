package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalPolicyVersionStatus;
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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a version of an {@link ApprovalPolicy}.
 *
 * <p>
 * Versioning allows approval policies to evolve without affecting
 * approval requests that are already in progress. Every approval
 * request is executed using the policy version that was active
 * when the request was created.
 *
 * <p>
 * Typical lifecycle:
 *
 * <pre>
 * DRAFT
 *      ↓
 * IN_REVIEW
 *      ↓
 * APPROVED
 *      ↓
 * ACTIVE
 *      ↓
 * RETIRED
 *      ↓
 * ARCHIVED
 * </pre>
 *
 * <p>
 * Only one version of a policy should normally be ACTIVE at any
 * point in time.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_versions", indexes = {
        @Index(name = "idx_policy_version_policy", columnList = "policy_id"),
        @Index(name = "idx_policy_version_number", columnList = "version_number"),
        @Index(name = "idx_policy_version_status", columnList = "status"),
        @Index(name = "idx_policy_version_current", columnList = "current_version"),
        @Index(name = "idx_policy_version_active", columnList = "active")
})
public class ApprovalPolicyVersion extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Business version number.
     *
     * Examples:
     * 1.0.0
     * 2.1.3
     * 5.0
     */
    @NotBlank
    @Size(max = 30)
    @Column(name = "version_number", nullable = false, length = 30)
    private String versionNumber;

    /**
     * Major version.
     */
    @NotNull
    @Min(1)
    @Column(name = "major_version", nullable = false)
    private Integer majorVersion = 1;

    /**
     * Minor version.
     */
    @NotNull
    @Min(0)
    @Column(name = "minor_version", nullable = false)
    private Integer minorVersion = 0;

    /**
     * Revision number.
     */
    @NotNull
    @Min(0)
    @Column(name = "revision", nullable = false)
    private Integer revision = 0;

    /**
     * Lifecycle status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ApprovalPolicyVersionStatus status = ApprovalPolicyVersionStatus.DRAFT;

    /**
     * Summary of changes introduced by this version.
     */
    @Size(max = 4000)
    @Column(name = "change_summary", length = 4000)
    private String changeSummary;

    /**
     * Indicates whether this is the current version.
     */
    @Column(name = "current_version", nullable = false)
    private boolean currentVersion = false;

    /**
     * Indicates whether this version
     * has been published.
     */
    @Column(name = "published", nullable = false)
    private boolean published = false;

    /**
     * Date and time this version becomes effective.
     */
    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    /**
     * Date and time this version expires.
     */
    @Column(name = "effective_until")
    private LocalDateTime effectiveUntil;

    /**
     * Date and time this version was published.
     */
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    /**
     * User that published this version.
     */
    @Size(max = 100)
    @Column(name = "published_by", length = 100)
    private String publishedBy;

    /**
     * Date and time this version was retired.
     */
    @Column(name = "retired_at")
    private LocalDateTime retiredAt;

    /**
     * User that retired this version.
     */
    @Size(max = 100)
    @Column(name = "retired_by", length = 100)
    private String retiredBy;

    /**
     * Optional execution order.
     */
    @Min(1)
    @Max(1000)
    @Column(name = "execution_order")
    private Integer executionOrder = 1;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyVersion other)) {
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
    public ApprovalPolicyVersion() {
    }

    /**
     * Returns whether this version is in draft state.
     *
     * @return true if draft
     */
    public boolean isDraft() {
        return status == ApprovalPolicyVersionStatus.DRAFT;
    }

    /**
     * Returns whether this version is under review.
     *
     * @return true if in review
     */
    public boolean isInReview() {
        return status == ApprovalPolicyVersionStatus.IN_REVIEW;
    }

    /**
     * Returns whether this version has been approved.
     *
     * @return true if approved
     */
    public boolean isApproved() {
        return status == ApprovalPolicyVersionStatus.APPROVED;
    }

    /**
     * Returns whether this version is currently active.
     *
     * @return true if active
     */
    public boolean isActiveVersion() {
        return status == ApprovalPolicyVersionStatus.ACTIVE;
    }

    /**
     * Returns whether this version has been retired.
     *
     * @return true if retired
     */
    public boolean isRetired() {
        return status == ApprovalPolicyVersionStatus.RETIRED;
    }

    /**
     * Returns whether this version has been archived.
     *
     * @return true if archived
     */
    public boolean isArchived() {
        return status == ApprovalPolicyVersionStatus.ARCHIVED;
    }

    /**
     * Returns whether this version has been published.
     *
     * @return true if published
     */
    public boolean isPublished() {
        return published;
    }

    /**
     * Returns whether this is the current version.
     *
     * @return true if current
     */
    public boolean isCurrentVersion() {
        return currentVersion;
    }

    /**
     * Determines whether this version is effective
     * at the current time.
     *
     * @return true if effective now
     */
    public boolean isEffectiveNow() {

        LocalDateTime now = LocalDateTime.now();

        if (effectiveFrom != null && now.isBefore(effectiveFrom)) {
            return false;
        }

        if (effectiveUntil != null && now.isAfter(effectiveUntil)) {
            return false;
        }

        return true;
    }

    /**
     * Marks this version as published.
     *
     * @param publishedBy user publishing the version
     */
    public void publish(String publishedBy) {
        this.published = true;
        this.status = ApprovalPolicyVersionStatus.ACTIVE;
        this.currentVersion = true;
        this.publishedAt = LocalDateTime.now();
        this.publishedBy = publishedBy;
    }

    /**
     * Retires this version.
     *
     * @param retiredBy user retiring the version
     */
    public void retire(String retiredBy) {
        this.status = ApprovalPolicyVersionStatus.RETIRED;
        this.currentVersion = false;
        this.retiredAt = LocalDateTime.now();
        this.retiredBy = retiredBy;
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

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ApprovalPolicyVersionStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalPolicyVersionStatus status) {
        this.status = status;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
    }

    public void setCurrentVersion(boolean currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveUntil() {
        return effectiveUntil;
    }

    public void setEffectiveUntil(LocalDateTime effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public LocalDateTime getRetiredAt() {
        return retiredAt;
    }

    public void setRetiredAt(LocalDateTime retiredAt) {
        this.retiredAt = retiredAt;
    }

    public String getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(String retiredBy) {
        this.retiredBy = retiredBy;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    /**
     * Validates this policy version.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null) {
            throw new IllegalStateException(
                    "Approval policy is required.");
        }

        if (versionNumber == null || versionNumber.isBlank()) {
            throw new IllegalStateException(
                    "Version number is required.");
        }

        if (majorVersion == null || majorVersion < 1) {
            throw new IllegalStateException(
                    "Major version must be greater than zero.");
        }

        if (minorVersion == null || minorVersion < 0) {
            throw new IllegalStateException(
                    "Minor version cannot be negative.");
        }

        if (revision == null || revision < 0) {
            throw new IllegalStateException(
                    "Revision cannot be negative.");
        }

        if (status == null) {
            throw new IllegalStateException(
                    "Version status is required.");
        }

        if (effectiveFrom != null
                && effectiveUntil != null
                && effectiveFrom.isAfter(effectiveUntil)) {

            throw new IllegalStateException(
                    "Effective from date cannot be after effective until date.");
        }

        if (executionOrder != null && executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (published && publishedAt == null) {
            throw new IllegalStateException(
                    "Published date is required for published versions.");
        }

        if (status == ApprovalPolicyVersionStatus.RETIRED
                && retiredAt == null) {

            throw new IllegalStateException(
                    "Retired date is required for retired versions.");
        }
    }

    /**
     * Returns the semantic version.
     *
     * @return semantic version
     */
    public String getSemanticVersion() {
        return majorVersion + "." + minorVersion + "." + revision;
    }

    /**
     * Returns whether this version can be modified.
     *
     * @return true if editable
     */
    public boolean isEditable() {
        return status == ApprovalPolicyVersionStatus.DRAFT
                || status == ApprovalPolicyVersionStatus.IN_REVIEW;
    }

    /**
     * Returns whether this version can be published.
     *
     * @return true if publishable
     */
    public boolean canBePublished() {
        return status == ApprovalPolicyVersionStatus.APPROVED
                && !published;
    }

    /**
     * Returns whether this version can be retired.
     *
     * @return true if retirement is allowed
     */
    public boolean canBeRetired() {
        return status == ApprovalPolicyVersionStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return "ApprovalPolicyVersion{" +
                "id=" + getId() +
                ", versionNumber='" + versionNumber + '\'' +
                ", semanticVersion='" + getSemanticVersion() + '\'' +
                ", status=" + status +
                ", currentVersion=" + currentVersion +
                ", published=" + published +
                ", active=" + isActive() +
                '}';
    }
}