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
     * Policy display name.
     */
    @Column(name = "policy_name", nullable = false, length = 200)
    private String policyName;

    /**
     * Policy description.
     */
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Indicates whether the policy is active.
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Indicates whether this is the default policy.
     */
    @Column(name = "default_policy", nullable = false)
    private boolean defaultPolicy = false;

    /**
     * Approval execution strategy.
     */
    @Column(name = "approval_strategy", nullable = false, length = 50)
    private ApprovalStrategy approvalStrategy = ApprovalStrategy.SEQUENTIAL;

    /**
     * Number of approvals required before the workflow is considered approved.
     */
    @Column(name = "required_approvals", nullable = false)
    private Integer requiredApprovals = 1;

    /**
     * Approval stages configured for this policy.
     */
    @OneToMany(mappedBy = "approvalPolicy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ApprovalStage> stages = new LinkedHashSet<>();

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
     * Indicates whether duplicate approvals by the same
     * approver are permitted.
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
     * Sends email notifications.
     */
    @Column(name = "email_notification_enabled", nullable = false)
    private boolean emailNotificationEnabled = true;

    /**
     * Sends in-application notifications.
     */
    @Column(name = "in_app_notification_enabled", nullable = false)
    private boolean inAppNotificationEnabled = true;

    /**
     * Sends SMS notifications.
     */
    @Column(name = "sms_notification_enabled", nullable = false)
    private boolean smsNotificationEnabled = false;

    /**
     * Indicates whether audit logging is enabled.
     */
    @Column(name = "audit_enabled", nullable = false)
    private boolean auditEnabled = true;

    /**
     * Indicates whether comments are mandatory during approval.
     */
    @Column(name = "approval_comment_required", nullable = false)
    private boolean approvalCommentRequired = false;

    /**
     * Indicates whether comments are mandatory during rejection.
     */
    @Column(name = "rejection_comment_required", nullable = false)
    private boolean rejectionCommentRequired = true;

    /**
     * Indicates whether this policy can be used by new
     * approval requests.
     */
    @Column(name = "available_for_use", nullable = false)
    private boolean availableForUse = true;

    /**
     * Adds an approval stage to this policy.
     *
     * @param stage approval stage
     */
    public void addStage(final ApprovalStage stage) {

        if (stage == null) {
            return;
        }

        if (this.stages.contains(stage)) {
            return;
        }

        stage.setApprovalPolicy(this);
        this.stages.add(stage);
    }

    /**
     * Removes an approval stage from this policy.
     *
     * @param stage approval stage
     */
    public void removeStage(final ApprovalStage stage) {

        if (stage == null) {
            return;
        }

        if (!this.stages.contains(stage)) {
            return;
        }

        stage.setApprovalPolicy(null);
        this.stages.remove(stage);
    }

    /**
     * Removes all approval stages.
     */
    public void clearStages() {

        this.stages.forEach(stage -> stage.setApprovalPolicy(null));
        this.stages.clear();
    }

    /**
     * Returns whether this policy is currently available for execution.
     *
     * @return {@code true} if the policy can be used
     */
    public boolean isExecutable() {

        return active
                && availableForUse
                && !stages.isEmpty()
                && requiredApprovals != null
                && requiredApprovals > 0;
    }

    /**
     * Enables this policy.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Disables this policy.
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
     * Removes the default policy designation.
     */
    public void unmarkAsDefault() {
        this.defaultPolicy = false;
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
     * Enables workflow timeout.
     */
    public void enableTimeout() {
        this.timeoutEnabled = true;
    }

    /**
     * Disables workflow timeout.
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
     * Enables audit logging.
     */
    public void enableAudit() {
        this.auditEnabled = true;
    }

    /**
     * Disables audit logging.
     */
    public void disableAudit() {
        this.auditEnabled = false;
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
     * Returns the policy code.
     *
     * @return policy code
     */
    public String getPolicyCode() {
        return policyCode;
    }

    /**
     * Returns the approval stages.
     *
     * @return approval stages
     */
    public Set<ApprovalStage> getStages() {
        return stages;
    }

}