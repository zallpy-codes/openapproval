package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Defines the configuration used to execute approval workflows.
 *
 * <p>
 * An {@code ApprovalPolicy} is the configuration aggregate that controls how
 * approval requests are processed by the OpenApproval Engine.
 * </p>
 *
 * <p>
 * A policy defines:
 * </p>
 * <ul>
 * <li>Approval strategy</li>
 * <li>Approval stages</li>
 * <li>Minimum required approvals</li>
 * <li>Delegation behaviour</li>
 * <li>Escalation behaviour</li>
 * <li>Timeout behaviour</li>
 * <li>Notification behaviour</li>
 * </ul>
 *
 * <p>
 * Policies are reusable and may be referenced by multiple approval requests.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_policy", indexes = {
        @Index(name = "idx_policy_code", columnList = "policy_code"),
        @Index(name = "idx_policy_name", columnList = "policy_name"),
        @Index(name = "idx_policy_active", columnList = "active")
})
public class ApprovalPolicy extends BaseEntity {

    /**
     * Unique business policy code.
     */
    @Column(name = "policy_code", nullable = false, unique = true, length = 100)
    private String policyCode;

    /**
     * Human-readable policy name.
     */
    @Column(name = "policy_name", nullable = false, length = 200)
    private String policyName;

    /**
     * Policy description.
     */
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Indicates whether this policy is active.
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Indicates whether this is the system default policy.
     */
    @Column(name = "default_policy", nullable = false)
    private boolean defaultPolicy = false;

    /**
     * Indicates whether this policy is available for new approval requests.
     */
    @Column(name = "available_for_use", nullable = false)
    private boolean availableForUse = true;

    /**
     * Approval execution strategy.
     */
    @Column(name = "approval_strategy", nullable = false, length = 50)
    private ApprovalStrategy approvalStrategy = ApprovalStrategy.SEQUENTIAL;

    /**
     * Number of approvals required before the workflow is approved.
     */
    @Column(name = "required_approvals", nullable = false)
    private Integer requiredApprovals = 1;

    /**
     * Approval stages belonging to this policy.
     */
    @OneToMany(mappedBy = "approvalPolicy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<ApprovalStage> stages = new LinkedHashSet<>();

    /**
     * Indicates whether delegation is permitted.
     */
    @Column(name = "delegation_allowed", nullable = false)
    private boolean delegationAllowed = true;

    /**
     * Indicates whether the request initiator may approve
     * his or her own request.
     */
    @Column(name = "initiator_can_approve", nullable = false)
    private boolean initiatorCanApprove = false;

    /**
     * Indicates whether duplicate approvals from the same approver
     * are allowed.
     */
    @Column(name = "allow_duplicate_approval", nullable = false)
    private boolean allowDuplicateApproval = false;

    /**
     * Indicates whether reminder notifications are enabled.
     */
    @Column(name = "reminder_enabled", nullable = false)
    private boolean reminderEnabled = true;

    /**
     * Reminder interval in minutes.
     */
    @Column(name = "reminder_interval_minutes")
    private Integer reminderIntervalMinutes;

    /**
     * Indicates whether workflow timeout is enabled.
     */
    @Column(name = "timeout_enabled", nullable = false)
    private boolean timeoutEnabled = false;

    /**
     * Workflow timeout in minutes.
     */
    @Column(name = "timeout_minutes")
    private Integer timeoutMinutes;

    /**
     * Indicates whether escalation is enabled.
     */
    @Column(name = "escalation_enabled", nullable = false)
    private boolean escalationEnabled = false;

    /**
     * Escalation timeout in minutes.
     */
    @Column(name = "escalation_timeout_minutes")
    private Integer escalationTimeoutMinutes;

    /**
     * Automatically approve the workflow after timeout.
     */
    @Column(name = "auto_approve_on_timeout", nullable = false)
    private boolean autoApproveOnTimeout = false;

    /**
     * Automatically reject the workflow after timeout.
     */
    @Column(name = "auto_reject_on_timeout", nullable = false)
    private boolean autoRejectOnTimeout = false;

    /**
     * Indicates whether email notifications are enabled.
     */
    @Column(name = "email_notification_enabled", nullable = false)
    private boolean emailNotificationEnabled = true;

    /**
     * Indicates whether in-application notifications are enabled.
     */
    @Column(name = "in_app_notification_enabled", nullable = false)
    private boolean inAppNotificationEnabled = true;

    /**
     * Indicates whether SMS notifications are enabled.
     */
    @Column(name = "sms_notification_enabled", nullable = false)
    private boolean smsNotificationEnabled = false;

    /**
     * Indicates whether audit logging is enabled.
     */
    @Column(name = "audit_enabled", nullable = false)
    private boolean auditEnabled = true;

    /**
     * Indicates whether an approval comment is mandatory.
     */
    @Column(name = "approval_comment_required", nullable = false)
    private boolean approvalCommentRequired = false;

    /**
     * Indicates whether a rejection comment is mandatory.
     */
    @Column(name = "rejection_comment_required", nullable = false)
    private boolean rejectionCommentRequired = true;

    /*
     * ==========================================================
     * Basic Getters
     * ==========================================================
     */

    /**
     * Returns the policy code.
     *
     * @return policy code
     */
    public String getPolicyCode() {
        return policyCode;
    }

    /**
     * Returns the policy name.
     *
     * @return policy name
     */
    public String getPolicyName() {
        return policyName;
    }

    /**
     * Returns the description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the approval strategy.
     *
     * @return approval strategy
     */
    public ApprovalStrategy getApprovalStrategy() {
        return approvalStrategy;
    }

    /**
     * Returns the required approvals.
     *
     * @return required approvals
     */
    public Integer getRequiredApprovals() {
        return requiredApprovals;
    }

    /**
     * Returns the configured approval stages.
     *
     * @return approval stages
     */
    public Set<ApprovalStage> getStages() {
        return stages;
    }

    /**
     * Returns whether this policy is active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns whether this policy is the default policy.
     *
     * @return true if default
     */
    public boolean isDefaultPolicy() {
        return defaultPolicy;
    }

    /**
     * Returns whether this policy is available for use.
     *
     * @return true if available
     */
    public boolean isAvailableForUse() {
        return availableForUse;
    }

    /**
     * Returns whether delegation is allowed.
     *
     * @return true if delegation is allowed
     */
    public boolean isDelegationAllowed() {
        return delegationAllowed;
    }

    /**
     * Returns whether initiators may approve their own requests.
     *
     * @return true if allowed
     */
    public boolean isInitiatorCanApprove() {
        return initiatorCanApprove;
    }

    /**
     * Returns whether duplicate approvals are allowed.
     *
     * @return true if duplicate approvals are allowed
     */
    public boolean isAllowDuplicateApproval() {
        return allowDuplicateApproval;
    }

    /**
     * Returns whether reminders are enabled.
     *
     * @return true if enabled
     */
    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    /**
     * Returns the reminder interval.
     *
     * @return reminder interval in minutes
     */
    public Integer getReminderIntervalMinutes() {
        return reminderIntervalMinutes;
    }

    /**
     * Returns whether workflow timeout is enabled.
     *
     * @return true if enabled
     */
    public boolean isTimeoutEnabled() {
        return timeoutEnabled;
    }

    /**
     * Returns the workflow timeout in minutes.
     *
     * @return timeout in minutes
     */
    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    /**
     * Returns whether escalation is enabled.
     *
     * @return true if enabled
     */
    public boolean isEscalationEnabled() {
        return escalationEnabled;
    }

    /**
     * Returns the escalation timeout.
     *
     * @return escalation timeout in minutes
     */
    public Integer getEscalationTimeoutMinutes() {
        return escalationTimeoutMinutes;
    }

    /**
     * Returns whether workflows are automatically approved on timeout.
     *
     * @return true if enabled
     */
    public boolean isAutoApproveOnTimeout() {
        return autoApproveOnTimeout;
    }

    /**
     * Returns whether workflows are automatically rejected on timeout.
     *
     * @return true if enabled
     */
    public boolean isAutoRejectOnTimeout() {
        return autoRejectOnTimeout;
    }

    /**
     * Returns whether email notifications are enabled.
     *
     * @return true if enabled
     */
    public boolean isEmailNotificationEnabled() {
        return emailNotificationEnabled;
    }

    /**
     * Returns whether in-app notifications are enabled.
     *
     * @return true if enabled
     */
    public boolean isInAppNotificationEnabled() {
        return inAppNotificationEnabled;
    }

    /**
     * Returns whether SMS notifications are enabled.
     *
     * @return true if enabled
     */
    public boolean isSmsNotificationEnabled() {
        return smsNotificationEnabled;
    }

    /**
     * Returns whether audit logging is enabled.
     *
     * @return true if enabled
     */
    public boolean isAuditEnabled() {
        return auditEnabled;
    }

    /**
     * Returns whether approval comments are required.
     *
     * @return true if required
     */
    public boolean isApprovalCommentRequired() {
        return approvalCommentRequired;
    }

    /**
     * Returns whether rejection comments are required.
     *
     * @return true if required
     */
    public boolean isRejectionCommentRequired() {
        return rejectionCommentRequired;
    }

    /*
     * ==========================================================
     * Controlled Setters
     * ==========================================================
     */

    public void setPolicyName(final String policyName) {
        this.policyName = policyName;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setApprovalStrategy(final ApprovalStrategy approvalStrategy) {
        this.approvalStrategy = approvalStrategy;
    }

    public void setRequiredApprovals(final Integer requiredApprovals) {
        this.requiredApprovals = requiredApprovals;
    }

    public void setReminderIntervalMinutes(final Integer reminderIntervalMinutes) {
        this.reminderIntervalMinutes = reminderIntervalMinutes;
    }

    public void setTimeoutMinutes(final Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }

    public void setEscalationTimeoutMinutes(final Integer escalationTimeoutMinutes) {
        this.escalationTimeoutMinutes = escalationTimeoutMinutes;
    }

    /*
     * ==========================================================
     * Aggregate Relationship Management
     * ==========================================================
     */

    /**
     * Adds an approval stage to this policy.
     *
     * @param approvalStage approval stage
     */
    public void addStage(final ApprovalStage approvalStage) {

        if (approvalStage == null) {
            return;
        }

        if (this.stages.contains(approvalStage)) {
            return;
        }

        approvalStage.setApprovalPolicy(this);
        this.stages.add(approvalStage);
    }

    /**
     * Removes an approval stage from this policy.
     *
     * @param approvalStage approval stage
     */
    public void removeStage(final ApprovalStage approvalStage) {

        if (approvalStage == null) {
            return;
        }

        if (!this.stages.contains(approvalStage)) {
            return;
        }

        approvalStage.setApprovalPolicy(null);
        this.stages.remove(approvalStage);
    }

    /**
     * Removes all approval stages.
     */
    public void clearStages() {

        this.stages.forEach(stage -> stage.setApprovalPolicy(null));
        this.stages.clear();
    }

    /*
     * ==========================================================
     * Lifecycle
     * ==========================================================
     */

    /**
     * Activates this policy.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates this policy.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Marks this policy as the default policy.
     */
    public void markAsDefault() {
        this.defaultPolicy = true;
    }

    /**
     * Removes the default designation.
     */
    public void unmarkAsDefault() {
        this.defaultPolicy = false;
    }

    /**
     * Makes this policy available for execution.
     */
    public void makeAvailable() {
        this.availableForUse = true;
    }

    /**
     * Makes this policy unavailable for execution.
     */
    public void makeUnavailable() {
        this.availableForUse = false;
    }

    /*
     * ==========================================================
     * Feature Toggles
     * ==========================================================
     */

    public void enableDelegation() {
        this.delegationAllowed = true;
    }

    public void disableDelegation() {
        this.delegationAllowed = false;
    }

    public void enableReminders() {
        this.reminderEnabled = true;
    }

    public void disableReminders() {
        this.reminderEnabled = false;
    }

    public void enableTimeout() {
        this.timeoutEnabled = true;
    }

    public void disableTimeout() {
        this.timeoutEnabled = false;
    }

    public void enableEscalation() {
        this.escalationEnabled = true;
    }

    public void disableEscalation() {
        this.escalationEnabled = false;
    }

    public void enableAudit() {
        this.auditEnabled = true;
    }

    public void disableAudit() {
        this.auditEnabled = false;
    }

    public void enableEmailNotification() {
        this.emailNotificationEnabled = true;
    }

    public void disableEmailNotification() {
        this.emailNotificationEnabled = false;
    }

    public void enableInAppNotification() {
        this.inAppNotificationEnabled = true;
    }

    public void disableInAppNotification() {
        this.inAppNotificationEnabled = false;
    }

    public void enableSmsNotification() {
        this.smsNotificationEnabled = true;
    }

    public void disableSmsNotification() {
        this.smsNotificationEnabled = false;
    }

    /*
     * ==========================================================
     * Business Rules
     * ==========================================================
     */

    /**
     * Determines whether this policy is executable by the approval engine.
     *
     * @return {@code true} if executable
     */
    public boolean isExecutable() {

        return active
                && availableForUse
                && approvalStrategy != null
                && requiredApprovals != null
                && requiredApprovals > 0
                && !stages.isEmpty();
    }

    /**
     * Determines whether this policy contains approval stages.
     *
     * @return {@code true} if stages exist
     */
    public boolean hasStages() {
        return !this.stages.isEmpty();
    }

    /**
     * Returns the number of configured approval stages.
     *
     * @return stage count
     */
    public int getStageCount() {
        return this.stages.size();
    }

    /**
     * Determines whether reminder processing is configured.
     *
     * @return {@code true} if reminders are enabled
     */
    public boolean supportsReminders() {
        return reminderEnabled && reminderIntervalMinutes != null;
    }

    /**
     * Determines whether timeout processing is configured.
     *
     * @return {@code true} if timeout is enabled
     */
    public boolean supportsTimeout() {
        return timeoutEnabled && timeoutMinutes != null;
    }

    /**
     * Determines whether escalation processing is configured.
     *
     * @return {@code true} if escalation is enabled
     */
    public boolean supportsEscalation() {
        return escalationEnabled && escalationTimeoutMinutes != null;
    }

    /**
     * Determines whether this policy can automatically complete
     * a workflow after timeout.
     *
     * @return {@code true} if automatic completion is configured
     */
    public boolean supportsAutomaticTimeoutDecision() {
        return autoApproveOnTimeout || autoRejectOnTimeout;
    }

}