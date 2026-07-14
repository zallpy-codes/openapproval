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
 * the execution behavior for that stage.
 * </p>
 *
 * <p>
 * Examples:
 * </p>
 *
 * <pre>
 * Purchase Approval Policy
 *
 * Stage 1 -> Department Manager
 * Stage 2 -> Finance Manager
 * Stage 3 -> Chief Executive Officer
 * </pre>
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
     * Stage display name.
     */
    @Column(name = "stage_name", nullable = false, length = 200)
    private String stageName;

    /**
     * Stage description.
     */
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Defines the execution order of this stage.
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
     * Indicates whether delegation is allowed for this stage.
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
     * Indicates whether this stage should be automatically
     * approved after timeout.
     */
    @Column(name = "auto_approve_on_timeout", nullable = false)
    private boolean autoApproveOnTimeout = false;

    /**
     * Indicates whether this stage should be automatically
     * rejected after timeout.
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
     * Sends email notifications for this stage.
     */
    @Column(name = "email_notification_enabled", nullable = false)
    private boolean emailNotificationEnabled = true;

    /**
     * Sends in-app notifications for this stage.
     */
    @Column(name = "in_app_notification_enabled", nullable = false)
    private boolean inAppNotificationEnabled = true;

    /**
     * Sends SMS notifications for this stage.
     */
    @Column(name = "sms_notification_enabled", nullable = false)
    private boolean smsNotificationEnabled = false;

    /**
     * Indicates whether this stage can be skipped by the engine
     * when its execution conditions are not satisfied.
     */
    @Column(name = "skippable", nullable = false)
    private boolean skippable = false;

    /**
     * Indicates whether this stage is mandatory.
     */
    @Column(name = "mandatory", nullable = false)
    private boolean mandatory = true;

    /**
     * Determines whether this stage is executable.
     *
     * @return {@code true} if the stage is executable
     */
    public boolean isExecutable() {

        return active
                && mandatory
                && requiredApprovals != null
                && requiredApprovals > 0
                && stageOrder != null
                && stageOrder > 0;
    }

    /**
     * Activates this stage.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates this stage.
     */
    public void deactivate() {
        this.active = false;
    }

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
     * Enables escalation.
     */
    public void enableEscalation() {
        this.escalationEnabled = true;
    }

    /**
     * Disables escalation.
     */
    public void disableEscalation() {
        this.escalationEnabled = false;
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
     * Enables in-application notifications.
     */
    public void enableInAppNotification() {
        this.inAppNotificationEnabled = true;
    }

    /**
     * Disables in-application notifications.
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

    /**
     * Returns whether this stage requires an approval comment.
     *
     * @return {@code true} if an approval comment is required
     */
    public boolean requiresApprovalComment() {
        return approvalCommentRequired;
    }

    /**
     * Returns whether this stage requires a rejection comment.
     *
     * @return {@code true} if a rejection comment is required
     */
    public boolean requiresRejectionComment() {
        return rejectionCommentRequired;
    }

    public void setApprovalPolicy(final ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

}