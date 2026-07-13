package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalPolicyTemplateStatus;
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
import java.util.Objects;

/**
 * Represents a reusable approval policy template.
 *
 * <p>
 * Templates provide standardized approval workflow definitions that
 * organizations can clone into tenant-specific approval policies.
 *
 * <p>
 * Examples:
 * <ul>
 * <li>Purchase Requisition Approval</li>
 * <li>Loan Approval Workflow</li>
 * <li>Leave Request Approval</li>
 * <li>Vendor Onboarding</li>
 * <li>Expense Reimbursement</li>
 * </ul>
 *
 * <p>
 * A template acts as a blueprint from which one or more
 * {@link ApprovalPolicy} instances can be created.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_templates", indexes = {
        @Index(name = "idx_template_code", columnList = "template_code"),
        @Index(name = "idx_template_status", columnList = "status"),
        @Index(name = "idx_template_industry", columnList = "industry"),
        @Index(name = "idx_template_public", columnList = "public_template"),
        @Index(name = "idx_template_active", columnList = "active")
})
public class ApprovalPolicyTemplate extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Source approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private ApprovalPolicy approvalPolicy;

    /**
     * Unique template code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "template_code", nullable = false, unique = true, length = 80)
    private String templateCode;

    /**
     * Template display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "template_name", nullable = false, length = 150)
    private String templateName;

    /**
     * Description.
     */
    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    /**
     * Template version.
     *
     * Example:
     * 1.0.0
     */
    @NotBlank
    @Size(max = 30)
    @Column(name = "template_version", nullable = false, length = 30)
    private String templateVersion = "1.0.0";

    /**
     * Lifecycle status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ApprovalPolicyTemplateStatus status = ApprovalPolicyTemplateStatus.DRAFT;

    /**
     * Industry classification.
     */
    @Size(max = 100)
    @Column(name = "industry", length = 100)
    private String industry;

    /**
     * Business domain.
     */
    @Size(max = 100)
    @Column(name = "business_domain", length = 100)
    private String businessDomain;

    /**
     * Template author.
     */
    @Size(max = 120)
    @Column(name = "author", length = 120)
    private String author;

    /**
     * Owning organization.
     */
    @Size(max = 150)
    @Column(name = "organization", length = 150)
    private String organization;

    /**
     * Search tags.
     */
    @Size(max = 1000)
    @Column(name = "tags", length = 1000)
    private String tags;

    /**
     * UI icon.
     */
    @Size(max = 100)
    @Column(name = "icon", length = 100)
    private String icon;

    /**
     * Display color.
     */
    @Size(max = 30)
    @Column(name = "color", length = 30)
    private String color;

    /**
     * Built-in template.
     */
    @Column(name = "system_template", nullable = false)
    private boolean systemTemplate = false;

    /**
     * Public template.
     */
    @Column(name = "public_template", nullable = false)
    private boolean publicTemplate = false;

    /**
     * Tenant-specific template.
     */
    @Column(name = "tenant_template", nullable = false)
    private boolean tenantTemplate = true;

    /**
     * Number of downloads.
     */
    @Min(0)
    @Column(name = "download_count", nullable = false)
    private Long downloadCount = 0L;

    /**
     * Number of times this template
     * has been used.
     */
    @Min(0)
    @Column(name = "usage_count", nullable = false)
    private Long usageCount = 0L;

    @Column(name = "clone_count", nullable = false)
    private Long cloneCount = 0L;

    /**
     * Number of times this template
     * has been marked as a favorite.
     */
    @Min(0)
    @Column(name = "favorite_count", nullable = false)
    private Long favoriteCount = 0L;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyTemplate other)) {
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
    public ApprovalPolicyTemplate() {
    }

    /**
     * Returns whether this template is in draft status.
     *
     * @return true if draft
     */
    public boolean isDraft() {
        return status == ApprovalPolicyTemplateStatus.DRAFT;
    }

    /**
     * Returns whether this template has been published.
     *
     * @return true if published
     */
    public boolean isPublished() {
        return status == ApprovalPolicyTemplateStatus.PUBLISHED;
    }

    /**
     * Returns whether this template is active.
     *
     * @return true if active
     */
    public boolean isActiveTemplate() {
        return status == ApprovalPolicyTemplateStatus.ACTIVE;
    }

    /**
     * Returns whether this template has been deprecated.
     *
     * @return true if deprecated
     */
    public boolean isDeprecated() {
        return status == ApprovalPolicyTemplateStatus.DEPRECATED;
    }

    /**
     * Returns whether this template has been archived.
     *
     * @return true if archived
     */
    public boolean isArchived() {
        return status == ApprovalPolicyTemplateStatus.ARCHIVED;
    }

    /**
     * Returns whether this is a system template.
     *
     * @return true if system template
     */
    public boolean isSystemTemplate() {
        return systemTemplate;
    }

    /**
     * Returns whether this is a public template.
     *
     * @return true if public template
     */
    public boolean isPublicTemplate() {
        return publicTemplate;
    }

    /**
     * Returns whether this is tenant-specific.
     *
     * @return true if tenant template
     */
    public boolean isTenantTemplate() {
        return tenantTemplate;
    }

    /**
     * Returns whether this template has tags.
     *
     * @return true if tags exist
     */
    public boolean hasTags() {
        return tags != null && !tags.isBlank();
    }

    /**
     * Returns whether this template has an icon.
     *
     * @return true if icon exists
     */
    public boolean hasIcon() {
        return icon != null && !icon.isBlank();
    }

    /**
     * Returns whether this template has a display color.
     *
     * @return true if color exists
     */
    public boolean hasColor() {
        return color != null && !color.isBlank();
    }

    /**
     * Returns the number of favorites.
     *
     * @return favorite count
     */
    public Long getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * Sets the favorite count.
     *
     * @param favoriteCount favorite count
     */
    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * Increments the favorite count.
     */
    public void incrementFavoriteCount() {

        if (favoriteCount == null) {
            favoriteCount = 0L;
        }

        favoriteCount++;
    }

    /**
     * Decrements the favorite count.
     *
     * <p>
     * The favorite count will never become negative.
     */
    public void decrementFavoriteCount() {

        if (favoriteCount == null || favoriteCount == 0L) {
            favoriteCount = 0L;
            return;
        }

        favoriteCount--;
    }

    /**
     * Returns whether this template
     * has been marked as a favorite.
     *
     * @return true if favorited
     */
    public boolean hasFavorites() {
        return favoriteCount != null && favoriteCount > 0;
    }

    /**
     * Returns whether this template
     * is highly favorited.
     *
     * <p>
     * A template is considered highly favorited
     * after receiving at least 50 favorites.
     *
     * @return true if highly favorited
     */
    public boolean isHighlyFavorited() {
        return favoriteCount != null && favoriteCount >= 50;
    }

    /**
     * Returns the number of times this template
     * has been cloned into approval policies.
     *
     * @return clone count
     */
    public Long getCloneCount() {
        return cloneCount;
    }

    /**
     * Sets the clone count.
     *
     * @param cloneCount clone count
     */
    public void setCloneCount(Long cloneCount) {
        this.cloneCount = cloneCount;
    }

    /**
     * Increments the clone count.
     */
    public void incrementCloneCount() {

        if (cloneCount == null) {
            cloneCount = 0L;
        }

        cloneCount++;
    }

    /**
     * Returns whether this template
     * has been cloned at least once.
     *
     * @return true if cloned
     */
    public boolean hasBeenCloned() {
        return cloneCount != null && cloneCount > 0;
    }

    /**
     * Returns whether this template
     * is popular based on clone activity.
     *
     * <p>
     * A template is considered popular after
     * being cloned at least 100 times.
     *
     * @return true if popular
     */
    public boolean isPopular() {
        return cloneCount != null && cloneCount >= 100;
    }

    /**
     * Increments the usage count.
     */
    public void incrementUsage() {
        if (usageCount == null) {
            usageCount = 0L;
        }
        usageCount++;
    }

    /**
     * Increments the download count.
     */
    public void incrementDownloads() {
        if (downloadCount == null) {
            downloadCount = 0L;
        }
        downloadCount++;
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public ApprovalPolicyTemplateStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalPolicyTemplateStatus status) {
        this.status = status;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(String businessDomain) {
        this.businessDomain = businessDomain;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSystemTemplate(boolean systemTemplate) {
        this.systemTemplate = systemTemplate;
    }

    public void setPublicTemplate(boolean publicTemplate) {
        this.publicTemplate = publicTemplate;
    }

    public void setTenantTemplate(boolean tenantTemplate) {
        this.tenantTemplate = tenantTemplate;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }

    /**
     * Returns the overall popularity score.
     *
     * <p>
     * Formula:
     * favorites × 5 +
     * clones × 10 +
     * downloads × 2 +
     * usage × 20
     *
     * @return popularity score
     */
    public long getPopularityScore() {

        long favorites = favoriteCount == null ? 0L : favoriteCount;
        long clones = cloneCount == null ? 0L : cloneCount;
        long downloads = downloadCount == null ? 0L : downloadCount;
        long usage = usageCount == null ? 0L : usageCount;

        return (favorites * 5)
                + (clones * 10)
                + (downloads * 2)
                + (usage * 20);
    }

    /**
     * Returns whether this template
     * is trending.
     *
     * @return true if popularity score is at least 500
     */
    public boolean isTrending() {
        return getPopularityScore() >= 500;
    }

    /**
     * Validates the template configuration.
     *
     * @throws IllegalStateException if the template configuration is invalid
     */
    public void validateConfiguration() {

        if (templateCode == null || templateCode.isBlank()) {
            throw new IllegalStateException(
                    "Template code is required.");
        }

        if (templateName == null || templateName.isBlank()) {
            throw new IllegalStateException(
                    "Template name is required.");
        }

        if (templateVersion == null || templateVersion.isBlank()) {
            throw new IllegalStateException(
                    "Template version is required.");
        }

        if (status == null) {
            throw new IllegalStateException(
                    "Template status is required.");
        }

        if (downloadCount != null && downloadCount < 0) {
            throw new IllegalStateException(
                    "Download count cannot be negative.");
        }

        if (usageCount != null && usageCount < 0) {
            throw new IllegalStateException(
                    "Usage count cannot be negative.");
        }

        if (cloneCount != null && cloneCount < 0) {
            throw new IllegalStateException(
                    "Clone count cannot be negative.");
        }

        if (favoriteCount != null && favoriteCount < 0) {
            throw new IllegalStateException(
                    "Favorite count cannot be negative.");
        }

        if (systemTemplate && tenantTemplate) {
            throw new IllegalStateException(
                    "A template cannot be both a system template and a tenant template.");
        }

        if (publicTemplate && !isPublished() && !isActiveTemplate()) {
            throw new IllegalStateException(
                    "Only published or active templates can be public.");
        }
    }

    /**
     * Returns whether the template has an industry.
     *
     * @return true if industry exists
     */
    public boolean hasIndustry() {
        return industry != null && !industry.isBlank();
    }

    /**
     * Returns whether the template has a business domain.
     *
     * @return true if business domain exists
     */
    public boolean hasBusinessDomain() {
        return businessDomain != null && !businessDomain.isBlank();
    }

    /**
     * Returns whether the template has an author.
     *
     * @return true if author exists
     */
    public boolean hasAuthor() {
        return author != null && !author.isBlank();
    }

    /**
     * Returns whether the template has an owning organization.
     *
     * @return true if organization exists
     */
    public boolean hasOrganization() {
        return organization != null && !organization.isBlank();
    }

    /**
     * Returns whether this template is ready
     * for cloning into an approval policy.
     *
     * @return true if available for use
     */
    public boolean isAvailableForUse() {
        return isEnabled() && (isPublished() || isActiveTemplate());
    }

    /**
     * Returns whether this template
     * is eligible for publication.
     *
     * @return true if it can be published
     */
    public boolean canBePublished() {
        return isDraft();
    }

    /**
     * Returns whether this template
     * is eligible for archival.
     *
     * @return true if it can be archived
     */
    public boolean canBeArchived() {
        return !isArchived();
    }

    /**
     * Marks the template as published.
     */
    public void publish() {
        this.status = ApprovalPolicyTemplateStatus.PUBLISHED;
    }

    /**
     * Marks the template as active.
     */
    public void activate() {
        this.status = ApprovalPolicyTemplateStatus.ACTIVE;
    }

    /**
     * Marks the template as deprecated.
     */
    public void deprecate() {
        this.status = ApprovalPolicyTemplateStatus.DEPRECATED;
    }

    /**
     * Marks the template as archived.
     */
    public void archive() {
        this.status = ApprovalPolicyTemplateStatus.ARCHIVED;
    }

    @Override
    public String toString() {
        return "ApprovalPolicyTemplate{" +
                "id=" + getId() +
                ", templateCode='" + templateCode + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateVersion='" + templateVersion + '\'' +
                ", status=" + status +
                ", industry='" + industry + '\'' +
                ", businessDomain='" + businessDomain + '\'' +
                ", publicTemplate=" + publicTemplate +
                ", systemTemplate=" + systemTemplate +
                ", tenantTemplate=" + tenantTemplate +
                ", usageCount=" + usageCount +
                ", cloneCount=" + cloneCount +
                ", favoriteCount=" + favoriteCount +
                ", downloadCount=" + downloadCount +
                ", active=" + isActive() +
                '}';
    }
}