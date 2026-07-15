package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStrategy;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Defines a single approval stage within an {@link ApprovalPolicy}.
 *
 * <p>
 * An approval policy consists of one or more ordered approval stages.
 * Each stage defines who approves, how approvals are evaluated, and
 * the execution behaviour for that stage.
 * </p>
 *
 * <p>
 * Every {@code ApprovalStage} acts as the blueprint from which
 * runtime {@link ApprovalStep} instances are created when an
 * approval workflow starts.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_stage", indexes = {
        @Index(name = "idx_stage_policy", columnList = "approval_policy_id"),
        @Index(name = "idx_stage_code", columnList = "stage_code"),
        @Index(name = "idx_stage_order", columnList = "stage_order")
})
public class ApprovalStage extends BaseEntity {

    /**
     * Parent approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Unique stage code within the policy.
     */
    @Column(name = "stage_code", nullable = false, length = 100)
    private String stageCode;

    /**
     * Human-readable stage name.
     */
    @Column(name = "stage_name", nullable = false, length = 200)
    private String stageName;

    /**
     * Stage description.
     */
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Execution order of this stage.
     */
    @Column(name = "stage_order", nullable = false)
    private Integer stageOrder;

    /**
     * Approval strategy used by this stage.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_strategy", nullable = false, length = 50)
    private ApprovalStrategy approvalStrategy = ApprovalStrategy.SEQUENTIAL;

    /**
     * Number of approvals required before this stage
     * is considered approved.
     */
    @Column(name = "required_approvals", nullable = false)
    private Integer requiredApprovals = 1;

    /**
     * Indicates whether this stage is active.
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Indicates whether this stage is mandatory.
     */
    @Column(name = "mandatory", nullable = false)
    private boolean mandatory = true;

    /**
     * Indicates whether this stage may be skipped.
     */
    @Column(name = "skippable", nullable = false)
    private boolean skippable = false;

    /**
     * Indicates whether delegation is allowed.
     */
    @Column(name = "delegation_allowed", nullable = false)
    private boolean delegationAllowed = true;

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
     * Indicates whether timeout processing is enabled.
     */
    @Column(name = "timeout_enabled", nullable = false)
    private boolean timeoutEnabled = false;

    /**
     * Timeout duration in minutes.
     */
    @Column(name = "timeout_minutes")
    private Integer timeoutMinutes;

    /**
     * Indicates whether escalation processing is enabled.
     */
    @Column(name = "escalation_enabled", nullable = false)
    private boolean escalationEnabled = false;

    /**
     * Escalation timeout in minutes.
     */
    @Column(name = "escalation_timeout_minutes")
    private Integer escalationTimeoutMinutes;

    /**
     * Automatically approve this stage when timeout occurs.
     */
    @Column(name = "auto_approve_on_timeout", nullable = false)
    private boolean autoApproveOnTimeout = false;

    /**
     * Automatically reject this stage when timeout occurs.
     */
    @Column(name = "auto_reject_on_timeout", nullable = false)
    private boolean autoRejectOnTimeout = false;

    /**
     * Indicates whether approval comments are mandatory.
     */
    @Column(name = "approval_comment_required", nullable = false)
    private boolean approvalCommentRequired = false;

    /**
     * Indicates whether rejection comments are mandatory.
     */
    @Column(name = "rejection_comment_required", nullable = false)
    private boolean rejectionCommentRequired = true;

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

    /*
     * ==========================================================
     * Getters
     * ==========================================================
     */

    /**
     * Returns the parent approval policy.
     *
     * @return approval policy
     */
    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    /**
     * Returns the stage code.
     *
     * @return stage code
     */
    public String getStageCode() {
        return stageCode;
    }

    /**
     * Returns the stage name.
     *
     * @return stage name
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * Returns the stage description.
     *
     * @return stage description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the execution order.
     *
     * @return stage order
     */
    public Integer getStageOrder() {
        return stageOrder;
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
     * Returns whether this stage is active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns whether this stage is mandatory.
     *
     * @return true if mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Returns whether this stage is skippable.
     *
     * @return true if skippable
     */
    public boolean isSkippable() {
        return skippable;
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
     * Returns whether timeout processing is enabled.
     *
     * @return true if enabled
     */
    public boolean isTimeoutEnabled() {
        return timeoutEnabled;
    }

    /**
     * Returns the timeout duration.
     *
     * @return timeout in minutes
     */
    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    /**
     * Returns whether escalation processing is enabled.
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
     * Returns whether automatic approval on timeout is enabled.
     *
     * @return true if enabled
     */
    public boolean isAutoApproveOnTimeout() {
        return autoApproveOnTimeout;
    }

    /**
     * Returns whether automatic rejection on timeout is enabled.
     *
     * @return true if enabled
     */
    public boolean isAutoRejectOnTimeout() {
        return autoRejectOnTimeout;
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

    /*
     * ==========================================================
     * Controlled Setters
     * ==========================================================
     */

    public void setApprovalPolicy(final ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    public void setStageName(final String stageName) {
        this.stageName = stageName;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setStageOrder(final Integer stageOrder) {
        this.stageOrder = stageOrder;
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
     * Lifecycle
     * ==========================================================
     */

    /**
     * Activates this approval stage.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates this approval stage.
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Makes this stage mandatory.
     */
    public void makeMandatory() {
        this.mandatory = true;
    }

    /**
     * Makes this stage optional.
     */
    public void makeOptional() {
        this.mandatory = false;
    }

    /**
     * Allows this stage to be skipped.
     */
    public void allowSkip() {
        this.skippable = true;
    }

    /**
     * Prevents this stage from being skipped.
     */
    public void preventSkip() {
        this.skippable = false;
    }

    /*
     * ==========================================================
     * Feature Toggles
     * ==========================================================
     */

    /**
     * Enables delegation.
     */
    public void enableDelegation() {
        this.delegationAllowed = true;
    }

    /**
     * Disables delegation.
     */
    public void disableDelegation() {
        this.delegationAllowed = false;
    }

    /**
     * Enables reminders.
     */
    public void enableReminders() {
        this.reminderEnabled = true;
    }

    /**
     * Disables reminders.
     */
    public void disableReminders() {
        this.reminderEnabled = false;
    }

    /**
     * Enables timeout processing.
     */
    public void enableTimeout() {
        this.timeoutEnabled = true;
    }

    /**
     * Disables timeout processing.
     */
    public void disableTimeout() {
        this.timeoutEnabled = false;
    }

    /**
     * Enables escalation processing.
     */
    public void enableEscalation() {
        this.escalationEnabled = true;
    }

    /**
     * Disables escalation processing.
     */
    public void disableEscalation() {
        this.escalationEnabled = false;
    }

    /**
     * Enables email notifications.
     */
    public void enableEmailNotification() {
        this.emailNotificationEnabled = true;
    }

    /**
     * Disables email notifications.
     */
    public void disableEmailNotification() {
        this.emailNotificationEnabled = false;
    }

    /**
     * Enables in-app notifications.
     */
    public void enableInAppNotification() {
        this.inAppNotificationEnabled = true;
    }

    /**
     * Disables in-app notifications.
     */
    public void disableInAppNotification() {
        this.inAppNotificationEnabled = false;
    }

    /**
     * Enables SMS notifications.
     */
    public void enableSmsNotification() {
        this.smsNotificationEnabled = true;
    }

    /**
     * Disables SMS notifications.
     */
    public void disableSmsNotification() {
        this.smsNotificationEnabled = false;
    }

    /*
     * ==========================================================
     * Business Rules
     * ==========================================================
     */

    /**
     * Determines whether this stage can be executed by the approval engine.
     *
     * @return {@code true} if executable
     */
    public boolean isExecutable() {

        return active
                && stageOrder != null
                && stageOrder > 0
                && approvalStrategy != null
                && requiredApprovals != null
                && requiredApprovals > 0;
    }

    /**
     * Determines whether reminder processing is configured.
     *
     * @return {@code true} if reminders are supported
     */
    public boolean supportsReminders() {
        return reminderEnabled && reminderIntervalMinutes != null;
    }

    /**
     * Determines whether timeout processing is configured.
     *
     * @return {@code true} if timeout processing is supported
     */
    public boolean supportsTimeout() {
        return timeoutEnabled && timeoutMinutes != null;
    }

    /**
     * Determines whether escalation processing is configured.
     *
     * @return {@code true} if escalation processing is supported
     */
    public boolean supportsEscalation() {
        return escalationEnabled && escalationTimeoutMinutes != null;
    }

    /**
     * Determines whether automatic timeout handling is configured.
     *
     * @return {@code true} if automatic timeout handling is enabled
     */
    public boolean supportsAutomaticTimeoutDecision() {
        return autoApproveOnTimeout || autoRejectOnTimeout;
    }

    /**
     * Determines whether approval comments are required.
     *
     * @return {@code true} if approval comments are mandatory
     */
    public boolean requiresApprovalComment() {
        return approvalCommentRequired;
    }

    /**
     * Determines whether rejection comments are required.
     *
     * @return {@code true} if rejection comments are mandatory
     */
    public boolean requiresRejectionComment() {
        return rejectionCommentRequired;
    }

    /**
     * Determines whether this stage belongs to an approval policy.
     *
     * @return {@code true} if attached to a policy
     */
    public boolean hasApprovalPolicy() {
        return approvalPolicy != null;
    }

    /**
     * Determines whether this stage executes before another stage.
     *
     * @param other another approval stage
     * @return {@code true} if this stage executes first
     */
    public boolean executesBefore(final ApprovalStage other) {

        if (other == null
                || this.stageOrder == null
                || other.stageOrder == null) {
            return false;
        }

        return this.stageOrder < other.stageOrder;
    }

    /**
     * Determines whether this stage executes after another stage.
     *
     * @param other another approval stage
     * @return {@code true} if this stage executes later
     */
    public boolean executesAfter(final ApprovalStage other) {

        if (other == null
                || this.stageOrder == null
                || other.stageOrder == null) {
            return false;
        }

        return this.stageOrder > other.stageOrder;
    }

}