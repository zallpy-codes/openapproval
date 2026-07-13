package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalInitiatorType;
import com.zallpy.openapproval.approval.enums.ApprovalPriority;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Defines the approval policy used by the OpenApproval engine.
 *
 * <p>
 * This entity represents the complete approval configuration for a
 * particular approval type or business process. A policy determines:
 *
 * <ul>
 * <li>Who can initiate approval requests</li>
 * <li>Approval workflow configuration</li>
 * <li>Priority</li>
 * <li>SLA configuration</li>
 * <li>Reminder settings</li>
 * <li>Escalation behaviour</li>
 * <li>Notification behaviour</li>
 * <li>Cancellation rules</li>
 * <li>Delegation rules</li>
 * <li>Policy activation</li>
 * </ul>
 *
 * <p>
 * A policy is versioned through optimistic locking and supports
 * soft deletion through {@link ActiveEntity}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policies", indexes = {
        @Index(name = "idx_policy_code", columnList = "policy_code", unique = true),
        @Index(name = "idx_policy_name", columnList = "policy_name"),
        @Index(name = "idx_policy_category", columnList = "category"),
        @Index(name = "idx_policy_active", columnList = "active"),
        @Index(name = "idx_policy_status", columnList = "default_status")
})
public class ApprovalPolicy extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique business code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "policy_code", nullable = false, unique = true, length = 80)
    private String policyCode;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "policy_name", nullable = false, length = 150)
    private String policyName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Functional category.
     *
     * Examples:
     * Procurement
     * Finance
     * HR
     * IT
     * Banking
     */
    @Size(max = 100)
    @Column(name = "category", length = 100)
    private String category;

    /**
     * Approval module.
     */
    @Size(max = 100)
    @Column(name = "module_name", length = 100)
    private String moduleName;

    /**
     * Entity name supported by this policy.
     */
    @NotBlank
    @Size(max = 120)
    @Column(name = "entity_name", nullable = false, length = 120)
    private String entityName;

    /**
     * Default approval priority.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private ApprovalPriority priority = ApprovalPriority.NORMAL;

    /**
     * Initial request status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "default_status", nullable = false, length = 40)
    private ApprovalStatus defaultStatus = ApprovalStatus.PENDING;

    /**
     * Who can initiate approvals.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "initiator_type", nullable = false, length = 40)
    private ApprovalInitiatorType initiatorType = ApprovalInitiatorType.USER;

    /**
     * Whether approval requests can be initiated
     * through REST API.
     */
    @Column(name = "allow_api_requests", nullable = false)
    private boolean allowApiRequests = true;

    /**
     * Whether UI requests are permitted.
     */
    @Column(name = "allow_ui_requests", nullable = false)
    private boolean allowUiRequests = true;

    /**
     * Allow system generated approvals.
     */
    @Column(name = "allow_system_requests", nullable = false)
    private boolean allowSystemRequests = false;

    /**
     * Whether draft requests are allowed.
     */
    @Column(name = "allow_draft", nullable = false)
    private boolean allowDraft = true;

    /**
     * Automatically submit after creation.
     */
    @Column(name = "auto_submit", nullable = false)
    private boolean autoSubmit = false;

    /**
     * Allow requester cancellation.
     */
    @Column(name = "allow_requester_cancel", nullable = false)
    private boolean allowRequesterCancel = true;

    /**
     * Allow requester modification
     * before approval starts.
     */
    @Column(name = "allow_request_edit", nullable = false)
    private boolean allowRequestEdit = true;

    /**
     * Lock request once submitted.
     */
    @Column(name = "lock_after_submission", nullable = false)
    private boolean lockAfterSubmission = true;

    /**
     * Whether approval comments
     * are mandatory.
     */
    @Column(name = "require_comments", nullable = false)
    private boolean requireComments = false;

    /**
     * Require reason for rejection.
     */
    @Column(name = "require_rejection_reason", nullable = false)
    private boolean requireRejectionReason = true;

    /**
     * Allow file attachments.
     */
    @Column(name = "allow_attachments", nullable = false)
    private boolean allowAttachments = true;

    /**
     * Require attachment.
     */
    @Column(name = "require_attachment", nullable = false)
    private boolean requireAttachment = false;

    /**
     * Enable approval delegation.
     */
    @Column(name = "allow_delegation", nullable = false)
    private boolean allowDelegation = true;

    /**
     * Enable escalation.
     */
    @Column(name = "enable_escalation", nullable = false)
    private boolean enableEscalation = true;

    /**
     * Enable reminders.
     */
    @Column(name = "enable_reminders", nullable = false)
    private boolean enableReminders = true;

    /**
     * SLA timeout in minutes.
     */
    @Column(name = "sla_minutes")
    private Long slaMinutes;

    /**
     * Reminder interval in minutes.
     */
    @Column(name = "reminder_interval_minutes")
    private Long reminderIntervalMinutes;

    /**
     * Maximum reminders.
     */
    @Column(name = "maximum_reminders")
    private Integer maximumReminders;

    /**
     * Escalation delay in minutes.
     */
    @Column(name = "escalation_delay_minutes")
    private Long escalationDelayMinutes;

    /**
     * Maximum approval duration.
     */
    @Column(name = "maximum_duration_minutes")
    private Long maximumDurationMinutes;

    /**
     * System policy.
     */
    @Column(name = "system_policy", nullable = false)
    private boolean systemPolicy = false;

    /**
     * Tags.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "approval_policy_tags", joinColumns = @JoinColumn(name = "policy_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new LinkedHashSet<>();

    /**
     * Ordered workflow stages.
     *
     * Part 2 continues from here.
     */
    @OneToMany(mappedBy = "approvalPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("executionOrder ASC")
    private Set<ApprovalPolicyStage> stages = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalPolicy that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Default constructor.
     */
    public ApprovalPolicy() {
    }

    /**
     * Adds a workflow stage.
     *
     * @param stage stage to add
     */
    public void addStage(final ApprovalPolicyStage stage) {
        if (stage == null) {
            return;
        }

        stages.add(stage);
        stage.setApprovalPolicy(this);
    }

    /**
     * Removes a workflow stage.
     *
     * @param stage stage to remove
     */
    public void removeStage(final ApprovalPolicyStage stage) {
        if (stage == null) {
            return;
        }

        stages.remove(stage);
        stage.setApprovalPolicy(null);
    }

    /**
     * Adds a policy tag.
     *
     * @param tag tag value
     */
    public void addTag(final String tag) {
        if (tag == null || tag.isBlank()) {
            return;
        }

        tags.add(tag.trim());
    }

    /**
     * Removes a tag.
     *
     * @param tag tag value
     */
    public void removeTag(final String tag) {
        if (tag == null) {
            return;
        }

        tags.remove(tag);
    }

    /**
     * Returns SLA duration.
     *
     * @return SLA duration or null
     */
    public Duration getSlaDuration() {
        return slaMinutes == null ? null : Duration.ofMinutes(slaMinutes);
    }

    /**
     * Returns escalation delay.
     *
     * @return escalation duration
     */
    public Duration getEscalationDelay() {
        return escalationDelayMinutes == null
                ? null
                : Duration.ofMinutes(escalationDelayMinutes);
    }

    /**
     * Returns reminder interval.
     *
     * @return reminder duration
     */
    public Duration getReminderInterval() {
        return reminderIntervalMinutes == null
                ? null
                : Duration.ofMinutes(reminderIntervalMinutes);
    }

    /**
     * Returns maximum workflow duration.
     *
     * @return duration
     */
    public Duration getMaximumDuration() {
        return maximumDurationMinutes == null
                ? null
                : Duration.ofMinutes(maximumDurationMinutes);
    }

    // ------------------------------------------------------------------------
    // Getters and Setters
    // ------------------------------------------------------------------------

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public ApprovalPriority getPriority() {
        return priority;
    }

    public void setPriority(ApprovalPriority priority) {
        this.priority = priority;
    }

    public ApprovalStatus getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(ApprovalStatus defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public ApprovalInitiatorType getInitiatorType() {
        return initiatorType;
    }

    public void setInitiatorType(ApprovalInitiatorType initiatorType) {
        this.initiatorType = initiatorType;
    }

    public boolean isAllowApiRequests() {
        return allowApiRequests;
    }

    public void setAllowApiRequests(boolean allowApiRequests) {
        this.allowApiRequests = allowApiRequests;
    }

    public boolean isAllowUiRequests() {
        return allowUiRequests;
    }

    public void setAllowUiRequests(boolean allowUiRequests) {
        this.allowUiRequests = allowUiRequests;
    }

    public boolean isAllowSystemRequests() {
        return allowSystemRequests;
    }

    public void setAllowSystemRequests(boolean allowSystemRequests) {
        this.allowSystemRequests = allowSystemRequests;
    }

    public boolean isAllowDraft() {
        return allowDraft;
    }

    public void setAllowDraft(boolean allowDraft) {
        this.allowDraft = allowDraft;
    }

    public boolean isAutoSubmit() {
        return autoSubmit;
    }

    public void setAutoSubmit(boolean autoSubmit) {
        this.autoSubmit = autoSubmit;
    }

    public boolean isAllowRequesterCancel() {
        return allowRequesterCancel;
    }

    public void setAllowRequesterCancel(boolean allowRequesterCancel) {
        this.allowRequesterCancel = allowRequesterCancel;
    }

    public boolean isAllowRequestEdit() {
        return allowRequestEdit;
    }

    public void setAllowRequestEdit(boolean allowRequestEdit) {
        this.allowRequestEdit = allowRequestEdit;
    }

    public boolean isLockAfterSubmission() {
        return lockAfterSubmission;
    }

    public void setLockAfterSubmission(boolean lockAfterSubmission) {
        this.lockAfterSubmission = lockAfterSubmission;
    }

    public boolean isRequireComments() {
        return requireComments;
    }

    public void setRequireComments(boolean requireComments) {
        this.requireComments = requireComments;
    }

    public boolean isRequireRejectionReason() {
        return requireRejectionReason;
    }

    public void setRequireRejectionReason(boolean requireRejectionReason) {
        this.requireRejectionReason = requireRejectionReason;
    }

    public boolean isAllowAttachments() {
        return allowAttachments;
    }

    public void setAllowAttachments(boolean allowAttachments) {
        this.allowAttachments = allowAttachments;
    }

    public boolean isRequireAttachment() {
        return requireAttachment;
    }

    public void setRequireAttachment(boolean requireAttachment) {
        this.requireAttachment = requireAttachment;
    }

    public boolean isAllowDelegation() {
        return allowDelegation;
    }

    public void setAllowDelegation(boolean allowDelegation) {
        this.allowDelegation = allowDelegation;
    }

    public boolean isEnableEscalation() {
        return enableEscalation;
    }

    public void setEnableEscalation(boolean enableEscalation) {
        this.enableEscalation = enableEscalation;
    }

    public boolean isEnableReminders() {
        return enableReminders;
    }

    public void setEnableReminders(boolean enableReminders) {
        this.enableReminders = enableReminders;
    }

    public Long getSlaMinutes() {
        return slaMinutes;
    }

    public void setSlaMinutes(Long slaMinutes) {
        this.slaMinutes = slaMinutes;
    }

    public Long getReminderIntervalMinutes() {
        return reminderIntervalMinutes;
    }

    public void setReminderIntervalMinutes(Long reminderIntervalMinutes) {
        this.reminderIntervalMinutes = reminderIntervalMinutes;
    }

    public Integer getMaximumReminders() {
        return maximumReminders;
    }

    public void setMaximumReminders(Integer maximumReminders) {
        this.maximumReminders = maximumReminders;
    }

    public Long getEscalationDelayMinutes() {
        return escalationDelayMinutes;
    }

    public void setEscalationDelayMinutes(Long escalationDelayMinutes) {
        this.escalationDelayMinutes = escalationDelayMinutes;
    }

    public Long getMaximumDurationMinutes() {
        return maximumDurationMinutes;
    }

    public void setMaximumDurationMinutes(Long maximumDurationMinutes) {
        this.maximumDurationMinutes = maximumDurationMinutes;
    }

    public boolean isSystemPolicy() {
        return systemPolicy;
    }

    public void setSystemPolicy(boolean systemPolicy) {
        this.systemPolicy = systemPolicy;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags == null ? new LinkedHashSet<>() : tags;
    }

    public Set<ApprovalPolicyStage> getStages() {
        return stages;
    }

    public void setStages(Set<ApprovalPolicyStage> stages) {
        this.stages.clear();

        if (stages != null) {
            stages.forEach(this::addStage);
        }
    }

    /**
     * Determines whether reminders should be sent.
     *
     * @return true if reminders are enabled and configured
     */
    public boolean shouldSendReminder() {
        return enableReminders
                && reminderIntervalMinutes != null
                && reminderIntervalMinutes > 0
                && maximumReminders != null
                && maximumReminders > 0;
    }

    /**
     * Determines whether automatic escalation is enabled.
     *
     * @return true if escalation is configured
     */
    public boolean shouldEscalate() {
        return enableEscalation
                && escalationDelayMinutes != null
                && escalationDelayMinutes > 0;
    }

    /**
     * Validates whether SLA has been configured.
     *
     * @return true when SLA exists
     */
    public boolean hasSla() {
        return slaMinutes != null && slaMinutes > 0;
    }

    /**
     * Returns whether this policy has workflow stages.
     *
     * @return true if stages exist
     */
    public boolean hasStages() {
        return !stages.isEmpty();
    }

    /**
     * Returns whether tags have been configured.
     *
     * @return true if tags exist
     */
    public boolean hasTags() {
        return !tags.isEmpty();
    }

    /**
     * Returns the total number of workflow stages.
     *
     * @return stage count
     */
    public int getStageCount() {
        return stages.size();
    }

    /**
     * Marks this policy as a system policy.
     */
    public void markAsSystemPolicy() {
        this.systemPolicy = true;
    }

    /**
     * Removes the system policy flag.
     */
    public void unmarkSystemPolicy() {
        this.systemPolicy = false;
    }

    @Override
    public String toString() {
        return "ApprovalPolicy{" +
                "id=" + getId() +
                ", policyCode='" + policyCode + '\'' +
                ", policyName='" + policyName + '\'' +
                ", entityName='" + entityName + '\'' +
                ", priority=" + priority +
                ", active=" + isActive() +
                ", stageCount=" + (stages == null ? 0 : stages.size()) +
                '}';
    }
}
