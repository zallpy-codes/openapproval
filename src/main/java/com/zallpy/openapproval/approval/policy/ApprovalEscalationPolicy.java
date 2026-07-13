package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalBusinessCalendarType;
import com.zallpy.openapproval.approval.enums.ApprovalEscalationType;
import com.zallpy.openapproval.approval.enums.ApprovalTimeoutAction;
import com.zallpy.openapproval.approval.enums.ApprovalTimeoutUnit;
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
import java.util.Objects;

/**
 * Defines escalation rules for an approval policy or approval stage.
 *
 * <p>
 * Escalation policies ensure approval requests do not remain
 * unattended beyond configured Service Level Agreements (SLAs).
 *
 * <p>
 * Examples:
 *
 * <pre>
 * After 24 Hours -> Escalate to Manager
 * After 48 Hours -> Escalate to Department Head
 * After 72 Hours -> Auto Reject
 * After 7 Days   -> Cancel Request
 * </pre>
 *
 * <p>
 * An escalation policy may be attached either to an
 * {@link ApprovalPolicy} or a specific {@link ApprovalPolicyStage}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_escalation_policies", indexes = {
        @Index(name = "idx_escalation_policy", columnList = "policy_id"),
        @Index(name = "idx_escalation_stage", columnList = "stage_id"),
        @Index(name = "idx_escalation_type", columnList = "escalation_type"),
        @Index(name = "idx_escalation_action", columnList = "timeout_action"),
        @Index(name = "idx_escalation_active", columnList = "active")
})
public class ApprovalEscalationPolicy extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private ApprovalPolicy approvalPolicy;

    /**
     * Parent approval stage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private ApprovalPolicyStage approvalPolicyStage;

    /**
     * Unique business code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "escalation_code", nullable = false, unique = true, length = 80)
    private String escalationCode;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "escalation_name", nullable = false, length = 150)
    private String escalationName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Escalation target.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "escalation_type", nullable = false, length = 40)
    private ApprovalEscalationType escalationType;

    /**
     * Action to perform after timeout.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "timeout_action", nullable = false, length = 40)
    private ApprovalTimeoutAction timeoutAction;

    /**
     * Timeout value.
     */
    @NotNull
    @Min(1)
    @Column(name = "timeout_value", nullable = false)
    private Long timeoutValue;

    /**
     * Timeout unit.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "timeout_unit", nullable = false, length = 20)
    private ApprovalTimeoutUnit timeoutUnit;

    /**
     * Business calendar used for timeout calculation.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "business_calendar_type", nullable = false, length = 30)
    private ApprovalBusinessCalendarType businessCalendarType = ApprovalBusinessCalendarType.CALENDAR_DAYS;

    /**
     * Maximum escalation attempts.
     */
    @NotNull
    @Min(1)
    @Max(100)
    @Column(name = "maximum_escalations", nullable = false)
    private Integer maximumEscalations = 1;

    /**
     * Execution order.
     */
    @NotNull
    @Min(1)
    @Column(name = "execution_order", nullable = false)
    private Integer executionOrder = 1;

    /**
     * Reference identifier for escalation targets
     * such as USER, ROLE, GROUP or DEPARTMENT.
     */
    @Size(max = 120)
    @Column(name = "target_reference", length = 120)
    private String targetReference;

    /**
     * Indicates whether escalation should be repeated
     * after the initial escalation.
     */
    @Column(name = "repeat_escalation", nullable = false)
    private boolean repeatEscalation = false;

    /**
     * Repeat interval value.
     */
    @Min(1)
    @Column(name = "repeat_interval")
    private Long repeatInterval;

    /**
     * Repeat interval unit.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_interval_unit", length = 20)
    private ApprovalTimeoutUnit repeatIntervalUnit;

    /**
     * Notify the requester after escalation.
     */
    @Column(name = "notify_requester", nullable = false)
    private boolean notifyRequester = true;

    /**
     * Notify the escalated approver.
     */
    @Column(name = "notify_escalated_approver", nullable = false)
    private boolean notifyEscalatedApprover = true;

    /**
     * Stop workflow execution if escalation fails.
     */
    @Column(name = "fail_workflow", nullable = false)
    private boolean failWorkflow = false;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalEscalationPolicy other)) {
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
    public ApprovalEscalationPolicy() {
    }

    /**
     * Returns whether this escalation policy belongs
     * directly to an approval policy.
     *
     * @return true if attached to a policy
     */
    public boolean isPolicyEscalation() {
        return approvalPolicy != null;
    }

    /**
     * Returns whether this escalation policy belongs
     * to an approval stage.
     *
     * @return true if attached to a stage
     */
    public boolean isStageEscalation() {
        return approvalPolicyStage != null;
    }

    /**
     * Returns whether this escalation requires
     * a target reference.
     *
     * @return true if target reference is required
     */
    public boolean requiresTargetReference() {

        return escalationType == ApprovalEscalationType.USER
                || escalationType == ApprovalEscalationType.ROLE
                || escalationType == ApprovalEscalationType.GROUP
                || escalationType == ApprovalEscalationType.DEPARTMENT
                || escalationType == ApprovalEscalationType.BUSINESS_UNIT
                || escalationType == ApprovalEscalationType.BRANCH
                || escalationType == ApprovalEscalationType.CUSTOM;
    }

    /**
     * Returns whether repeat escalation is enabled.
     *
     * @return true if enabled
     */
    public boolean isRepeatEnabled() {
        return repeatEscalation;
    }

    /**
     * Returns whether business calendar calculations
     * should be used.
     *
     * @return true if business calendar is enabled
     */
    public boolean usesBusinessCalendar() {
        return businessCalendarType != ApprovalBusinessCalendarType.CALENDAR_DAYS;
    }

    /**
     * Returns whether the requester should
     * be notified.
     *
     * @return true if requester notification is enabled
     */
    public boolean shouldNotifyRequester() {
        return notifyRequester;
    }

    /**
     * Returns whether the escalated approver
     * should be notified.
     *
     * @return true if enabled
     */
    public boolean shouldNotifyEscalatedApprover() {
        return notifyEscalatedApprover;
    }

    /**
     * Returns whether workflow execution
     * should fail if escalation cannot be completed.
     *
     * @return true if workflow should fail
     */
    public boolean shouldFailWorkflow() {
        return failWorkflow;
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

    public ApprovalPolicyStage getApprovalPolicyStage() {
        return approvalPolicyStage;
    }

    public void setApprovalPolicyStage(ApprovalPolicyStage approvalPolicyStage) {
        this.approvalPolicyStage = approvalPolicyStage;
    }

    public String getEscalationCode() {
        return escalationCode;
    }

    public void setEscalationCode(String escalationCode) {
        this.escalationCode = escalationCode;
    }

    public String getEscalationName() {
        return escalationName;
    }

    public void setEscalationName(String escalationName) {
        this.escalationName = escalationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalEscalationType getEscalationType() {
        return escalationType;
    }

    public void setEscalationType(ApprovalEscalationType escalationType) {
        this.escalationType = escalationType;
    }

    public ApprovalTimeoutAction getTimeoutAction() {
        return timeoutAction;
    }

    public void setTimeoutAction(ApprovalTimeoutAction timeoutAction) {
        this.timeoutAction = timeoutAction;
    }

    public Long getTimeoutValue() {
        return timeoutValue;
    }

    public void setTimeoutValue(Long timeoutValue) {
        this.timeoutValue = timeoutValue;
    }

    public ApprovalTimeoutUnit getTimeoutUnit() {
        return timeoutUnit;
    }

    public void setTimeoutUnit(ApprovalTimeoutUnit timeoutUnit) {
        this.timeoutUnit = timeoutUnit;
    }

    public ApprovalBusinessCalendarType getBusinessCalendarType() {
        return businessCalendarType;
    }

    public void setBusinessCalendarType(ApprovalBusinessCalendarType businessCalendarType) {
        this.businessCalendarType = businessCalendarType;
    }

    public Integer getMaximumEscalations() {
        return maximumEscalations;
    }

    public void setMaximumEscalations(Integer maximumEscalations) {
        this.maximumEscalations = maximumEscalations;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public String getTargetReference() {
        return targetReference;
    }

    public void setTargetReference(String targetReference) {
        this.targetReference = targetReference;
    }

    public boolean isRepeatEscalation() {
        return repeatEscalation;
    }

    public void setRepeatEscalation(boolean repeatEscalation) {
        this.repeatEscalation = repeatEscalation;
    }

    public Long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public ApprovalTimeoutUnit getRepeatIntervalUnit() {
        return repeatIntervalUnit;
    }

    public void setRepeatIntervalUnit(ApprovalTimeoutUnit repeatIntervalUnit) {
        this.repeatIntervalUnit = repeatIntervalUnit;
    }

    public boolean isNotifyRequester() {
        return notifyRequester;
    }

    public void setNotifyRequester(boolean notifyRequester) {
        this.notifyRequester = notifyRequester;
    }

    public boolean isNotifyEscalatedApprover() {
        return notifyEscalatedApprover;
    }

    public void setNotifyEscalatedApprover(boolean notifyEscalatedApprover) {
        this.notifyEscalatedApprover = notifyEscalatedApprover;
    }

    public boolean isFailWorkflow() {
        return failWorkflow;
    }

    public void setFailWorkflow(boolean failWorkflow) {
        this.failWorkflow = failWorkflow;
    }

    /**
     * Validates the escalation policy configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null && approvalPolicyStage == null) {
            throw new IllegalStateException(
                    "An escalation policy must belong to either an approval policy or an approval policy stage.");
        }

        if (approvalPolicy != null && approvalPolicyStage != null) {
            throw new IllegalStateException(
                    "An escalation policy cannot belong to both an approval policy and an approval policy stage.");
        }

        if (escalationCode == null || escalationCode.isBlank()) {
            throw new IllegalStateException(
                    "Escalation code is required.");
        }

        if (escalationName == null || escalationName.isBlank()) {
            throw new IllegalStateException(
                    "Escalation name is required.");
        }

        if (escalationType == null) {
            throw new IllegalStateException(
                    "Escalation type is required.");
        }

        if (timeoutAction == null) {
            throw new IllegalStateException(
                    "Timeout action is required.");
        }

        if (timeoutValue == null || timeoutValue <= 0) {
            throw new IllegalStateException(
                    "Timeout value must be greater than zero.");
        }

        if (timeoutUnit == null) {
            throw new IllegalStateException(
                    "Timeout unit is required.");
        }

        if (businessCalendarType == null) {
            throw new IllegalStateException(
                    "Business calendar type is required.");
        }

        if (maximumEscalations == null || maximumEscalations < 1) {
            throw new IllegalStateException(
                    "Maximum escalations must be greater than zero.");
        }

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (requiresTargetReference()
                && (targetReference == null || targetReference.isBlank())) {

            throw new IllegalStateException(
                    "Target reference is required for escalation type " + escalationType + ".");
        }

        if (repeatEscalation) {

            if (repeatInterval == null || repeatInterval <= 0) {
                throw new IllegalStateException(
                        "Repeat interval must be greater than zero when repeat escalation is enabled.");
            }

            if (repeatIntervalUnit == null) {
                throw new IllegalStateException(
                        "Repeat interval unit is required when repeat escalation is enabled.");
            }
        }
    }

    /**
     * Returns whether this escalation policy
     * is executable.
     *
     * @return true if active
     */
    public boolean isExecutable() {
        return isEnabled();
    }

    /**
     * Returns whether this escalation policy
     * uses automatic timeout processing.
     *
     * @return true if a timeout action is configured
     */
    public boolean hasTimeoutAction() {
        return timeoutAction != null;
    }

    /**
     * Returns whether this escalation policy
     * has a configured escalation target.
     *
     * @return true if a target reference exists
     */
    public boolean hasTargetReference() {
        return targetReference != null
                && !targetReference.isBlank();
    }

    @Override
    public String toString() {
        return "ApprovalEscalationPolicy{" +
                "id=" + getId() +
                ", escalationCode='" + escalationCode + '\'' +
                ", escalationName='" + escalationName + '\'' +
                ", escalationType=" + escalationType +
                ", timeoutAction=" + timeoutAction +
                ", timeoutValue=" + timeoutValue +
                ", timeoutUnit=" + timeoutUnit +
                ", maximumEscalations=" + maximumEscalations +
                ", executionOrder=" + executionOrder +
                ", repeatEscalation=" + repeatEscalation +
                ", active=" + isActive() +
                '}';
    }
}