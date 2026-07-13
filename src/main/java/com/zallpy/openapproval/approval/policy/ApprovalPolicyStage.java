package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalAssignmentType;
import com.zallpy.openapproval.approval.enums.ApprovalStageType;
import com.zallpy.openapproval.approval.enums.ApprovalStepDecisionMode;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a single stage within an {@link ApprovalPolicy}.
 *
 * <p>
 * An approval policy is composed of one or more ordered stages.
 * Each stage defines:
 *
 * <ul>
 * <li>The execution order.</li>
 * <li>The stage type.</li>
 * <li>Assignment strategy.</li>
 * <li>Decision mode.</li>
 * <li>Quorum requirements.</li>
 * <li>SLA override.</li>
 * <li>Escalation behavior.</li>
 * <li>Notification behavior.</li>
 * </ul>
 *
 * <p>
 * Every stage contains one or more approver definitions
 * (ApprovalPolicyApprover) which determine who is allowed
 * to approve requests reaching this stage.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_stages", indexes = {
        @Index(name = "idx_policy_stage_policy", columnList = "policy_id"),
        @Index(name = "idx_policy_stage_order", columnList = "execution_order"),
        @Index(name = "idx_policy_stage_code", columnList = "stage_code"),
        @Index(name = "idx_policy_stage_active", columnList = "active")
})
public class ApprovalPolicyStage extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Unique stage code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "stage_code", nullable = false, length = 80)
    private String stageCode;

    /**
     * Stage display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "stage_name", nullable = false, length = 150)
    private String stageName;

    /**
     * Stage description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Execution order within the policy.
     */
    @NotNull
    @Min(1)
    @Column(name = "execution_order", nullable = false)
    private Integer executionOrder;

    /**
     * Stage type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stage_type", nullable = false, length = 40)
    private ApprovalStageType stageType = ApprovalStageType.STANDARD;

    /**
     * Determines how approvers are assigned.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false, length = 40)
    private ApprovalAssignmentType assignmentType = ApprovalAssignmentType.NAMED_USERS;

    /**
     * Determines how approval decisions are evaluated.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "decision_mode", nullable = false, length = 40)
    private ApprovalStepDecisionMode decisionMode = ApprovalStepDecisionMode.ALL;

    /**
     * Minimum number of approvals required.
     */
    @Min(1)
    @Column(name = "minimum_approvals")
    private Integer minimumApprovals = 1;

    /**
     * Maximum approvals accepted.
     */
    @Min(1)
    @Column(name = "maximum_approvals")
    private Integer maximumApprovals;

    /**
     * Percentage quorum required.
     */
    @Min(1)
    @Max(100)
    @Column(name = "approval_quorum_percent")
    private Integer approvalQuorumPercent;

    /**
     * Stage-specific SLA override in minutes.
     */
    @Column(name = "sla_minutes")
    private Long slaMinutes;

    /**
     * Reminder interval override in minutes.
     */
    @Column(name = "reminder_interval_minutes")
    private Long reminderIntervalMinutes;

    /**
     * Escalation timeout override in minutes.
     */
    @Column(name = "escalation_minutes")
    private Long escalationMinutes;

    /**
     * Maximum reminder count.
     */
    @Column(name = "maximum_reminders")
    private Integer maximumReminders = 3;

    /**
     * Whether reminders are enabled.
     */
    @Column(name = "enable_reminders", nullable = false)
    private boolean enableReminders = true;

    /**
     * Whether escalation is enabled.
     */
    @Column(name = "enable_escalation", nullable = false)
    private boolean enableEscalation = true;

    /**
     * Whether delegation is allowed.
     */
    @Column(name = "allow_delegation", nullable = false)
    private boolean allowDelegation = true;

    /**
     * Whether comments are mandatory.
     */
    @Column(name = "require_comments", nullable = false)
    private boolean requireComments = false;

    /**
     * Whether attachment is mandatory.
     */
    @Column(name = "require_attachment", nullable = false)
    private boolean requireAttachment = false;

    /**
     * Whether this stage is optional.
     */
    @Column(name = "optional_stage", nullable = false)
    private boolean optionalStage = false;

    /**
     * Stage tags.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "approval_policy_stage_tags", joinColumns = @JoinColumn(name = "stage_id"))
    @Column(name = "tag", length = 100)
    private Set<String> tags = new LinkedHashSet<>();

    /**
     * Approvers configured for this stage.
     *
     * Part 2 continues from here.
     */
    @OneToMany(mappedBy = "approvalPolicyStage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("executionOrder ASC")
    private Set<ApprovalPolicyApprover> approvers = new LinkedHashSet<>();

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyStage other)) {
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
    public ApprovalPolicyStage() {
    }

    /**
     * Adds an approver to this stage.
     *
     * @param approver approver to add
     */
    public void addApprover(final ApprovalPolicyApprover approver) {
        if (approver == null) {
            return;
        }

        approvers.add(approver);
        approver.setApprovalPolicyStage(this);
    }

    /**
     * Removes an approver from this stage.
     *
     * @param approver approver to remove
     */
    public void removeApprover(final ApprovalPolicyApprover approver) {
        if (approver == null) {
            return;
        }

        approvers.remove(approver);
        approver.setApprovalPolicyStage(null);
    }

    /**
     * Adds a tag.
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

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    public void setApprovalPolicy(ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public ApprovalStageType getStageType() {
        return stageType;
    }

    public void setStageType(ApprovalStageType stageType) {
        this.stageType = stageType;
    }

    public ApprovalAssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(ApprovalAssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public ApprovalStepDecisionMode getDecisionMode() {
        return decisionMode;
    }

    public void setDecisionMode(ApprovalStepDecisionMode decisionMode) {
        this.decisionMode = decisionMode;
    }

    public Integer getMinimumApprovals() {
        return minimumApprovals;
    }

    public void setMinimumApprovals(Integer minimumApprovals) {
        this.minimumApprovals = minimumApprovals;
    }

    public Integer getMaximumApprovals() {
        return maximumApprovals;
    }

    public void setMaximumApprovals(Integer maximumApprovals) {
        this.maximumApprovals = maximumApprovals;
    }

    public Integer getApprovalQuorumPercent() {
        return approvalQuorumPercent;
    }

    public void setApprovalQuorumPercent(Integer approvalQuorumPercent) {
        this.approvalQuorumPercent = approvalQuorumPercent;
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

    public Long getEscalationMinutes() {
        return escalationMinutes;
    }

    public void setEscalationMinutes(Long escalationMinutes) {
        this.escalationMinutes = escalationMinutes;
    }

    public Integer getMaximumReminders() {
        return maximumReminders;
    }

    public void setMaximumReminders(Integer maximumReminders) {
        this.maximumReminders = maximumReminders;
    }

    public boolean isEnableReminders() {
        return enableReminders;
    }

    public void setEnableReminders(boolean enableReminders) {
        this.enableReminders = enableReminders;
    }

    public boolean isEnableEscalation() {
        return enableEscalation;
    }

    public void setEnableEscalation(boolean enableEscalation) {
        this.enableEscalation = enableEscalation;
    }

    public boolean isAllowDelegation() {
        return allowDelegation;
    }

    public void setAllowDelegation(boolean allowDelegation) {
        this.allowDelegation = allowDelegation;
    }

    public boolean isRequireComments() {
        return requireComments;
    }

    public void setRequireComments(boolean requireComments) {
        this.requireComments = requireComments;
    }

    public boolean isRequireAttachment() {
        return requireAttachment;
    }

    public void setRequireAttachment(boolean requireAttachment) {
        this.requireAttachment = requireAttachment;
    }

    public boolean isOptionalStage() {
        return optionalStage;
    }

    public void setOptionalStage(boolean optionalStage) {
        this.optionalStage = optionalStage;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = (tags == null)
                ? new LinkedHashSet<>()
                : new LinkedHashSet<>(tags);
    }

    public Set<ApprovalPolicyApprover> getApprovers() {
        return approvers;
    }

    public void setApprovers(Set<ApprovalPolicyApprover> approvers) {
        this.approvers.clear();

        if (approvers != null) {
            approvers.forEach(this::addApprover);
        }
    }

    /**
     * Determines whether reminders are configured for this stage.
     *
     * @return {@code true} if reminders are enabled and properly configured
     */
    public boolean shouldSendReminder() {
        return enableReminders
                && reminderIntervalMinutes != null
                && reminderIntervalMinutes > 0
                && maximumReminders != null
                && maximumReminders > 0;
    }

    /**
     * Determines whether escalation is configured for this stage.
     *
     * @return {@code true} if escalation is enabled
     */
    public boolean shouldEscalate() {
        return enableEscalation
                && escalationMinutes != null
                && escalationMinutes > 0;
    }

    /**
     * Indicates whether this stage overrides the parent policy SLA.
     *
     * @return {@code true} if an SLA override exists
     */
    public boolean hasSlaOverride() {
        return slaMinutes != null && slaMinutes > 0;
    }

    /**
     * Returns whether this stage contains any approvers.
     *
     * @return {@code true} if at least one approver is configured
     */
    public boolean hasApprovers() {
        return !approvers.isEmpty();
    }

    /**
     * Returns the total number of configured approvers.
     *
     * @return approver count
     */
    public int getApproverCount() {
        return approvers.size();
    }

    /**
     * Returns whether this stage has any tags.
     *
     * @return {@code true} if tags exist
     */
    public boolean hasTags() {
        return !tags.isEmpty();
    }

    /**
     * Marks this stage as optional.
     */
    public void makeOptional() {
        this.optionalStage = true;
    }

    /**
     * Marks this stage as mandatory.
     */
    public void makeMandatory() {
        this.optionalStage = false;
    }

    /**
     * Validates the stage configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (minimumApprovals != null
                && maximumApprovals != null
                && minimumApprovals > maximumApprovals) {
            throw new IllegalStateException(
                    "Minimum approvals cannot exceed maximum approvals.");
        }

        if (!optionalStage && approvers.isEmpty()) {
            throw new IllegalStateException(
                    "A mandatory approval stage must contain at least one approver.");
        }

        if (approvalQuorumPercent != null
                && (approvalQuorumPercent < 1 || approvalQuorumPercent > 100)) {
            throw new IllegalStateException(
                    "Approval quorum percentage must be between 1 and 100.");
        }
    }

    @Override
    public String toString() {
        return "ApprovalPolicyStage{" +
                "id=" + getId() +
                ", stageCode='" + stageCode + '\'' +
                ", stageName='" + stageName + '\'' +
                ", executionOrder=" + executionOrder +
                ", stageType=" + stageType +
                ", decisionMode=" + decisionMode +
                ", active=" + isActive() +
                ", approverCount=" + (approvers == null ? 0 : approvers.size()) +
                '}';
    }
}