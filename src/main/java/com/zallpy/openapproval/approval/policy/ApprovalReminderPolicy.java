package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalBusinessCalendarType;
import com.zallpy.openapproval.approval.enums.ApprovalNotificationChannel;
import com.zallpy.openapproval.approval.enums.ApprovalNotificationRecipientType;
import com.zallpy.openapproval.approval.enums.ApprovalReminderFrequency;
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
 * Defines reminder rules for an approval policy or approval stage.
 *
 * <p>
 * Unlike {@link ApprovalPolicyNotification}, which is event-driven,
 * reminder policies are time-driven and periodically notify pending
 * approvers until the approval is completed, cancelled, rejected,
 * escalated or the configured reminder limit is reached.
 *
 * <p>
 * Examples:
 * 
 * <pre>
 * First Reminder : 2 Hours
 * Frequency      : Every 24 Hours
 * Maximum Count  : 5
 * Channel        : EMAIL
 *
 * First Reminder : 30 Minutes
 * Frequency      : Hourly
 * Stop After Escalation : Yes
 * </pre>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_reminder_policies", indexes = {
        @Index(name = "idx_reminder_policy", columnList = "policy_id"),
        @Index(name = "idx_reminder_stage", columnList = "stage_id"),
        @Index(name = "idx_reminder_channel", columnList = "notification_channel"),
        @Index(name = "idx_reminder_recipient", columnList = "recipient_type"),
        @Index(name = "idx_reminder_active", columnList = "active")
})
public class ApprovalReminderPolicy extends ActiveEntity {

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
     * Unique reminder code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "reminder_code", nullable = false, unique = true, length = 80)
    private String reminderCode;

    /**
     * Reminder display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "reminder_name", nullable = false, length = 150)
    private String reminderName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Delivery channel.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_channel", nullable = false, length = 40)
    private ApprovalNotificationChannel notificationChannel;

    /**
     * Reminder recipient.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false, length = 40)
    private ApprovalNotificationRecipientType recipientType;

    /**
     * Optional recipient reference.
     *
     * Used for USER, ROLE, GROUP,
     * DEPARTMENT, etc.
     */
    @Size(max = 120)
    @Column(name = "recipient_reference", length = 120)
    private String recipientReference;

    /**
     * Time before the first reminder.
     */
    @NotNull
    @Min(1)
    @Column(name = "first_reminder_after", nullable = false)
    private Long firstReminderAfter = 1L;

    /**
     * Unit used for the first reminder.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "timeout_unit", nullable = false, length = 20)
    private ApprovalTimeoutUnit timeoutUnit;

    /**
     * Reminder repetition strategy.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reminder_frequency", nullable = false, length = 30)
    private ApprovalReminderFrequency reminderFrequency = ApprovalReminderFrequency.ONCE;

    /**
     * Repeat interval.
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
     * Maximum number of reminders.
     */
    @NotNull
    @Min(1)
    @Max(100)
    @Column(name = "maximum_reminders", nullable = false)
    private Integer maximumReminders = 1;

    /**
     * Calendar used when computing reminder schedules.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "business_calendar_type", nullable = false, length = 30)
    private ApprovalBusinessCalendarType businessCalendarType = ApprovalBusinessCalendarType.CALENDAR_DAYS;

    /**
     * Stop reminders after escalation.
     */
    @Column(name = "stop_after_escalation", nullable = false)
    private boolean stopAfterEscalation = true;

    /**
     * Stop reminders after approval.
     */
    @Column(name = "stop_after_approval", nullable = false)
    private boolean stopAfterApproval = true;

    /**
     * Stop reminders after rejection.
     */
    @Column(name = "stop_after_rejection", nullable = false)
    private boolean stopAfterRejection = true;

    /**
     * Execution order.
     */
    @NotNull
    @Min(1)
    @Column(name = "execution_order", nullable = false)
    private Integer executionOrder = 1;

    /**
     * Notification template code.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "template_code", nullable = false, length = 100)
    private String templateCode;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalReminderPolicy other)) {
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
    public ApprovalReminderPolicy() {
    }

    /**
     * Returns whether this reminder belongs directly
     * to an approval policy.
     *
     * @return true if attached to a policy
     */
    public boolean isPolicyReminder() {
        return approvalPolicy != null;
    }

    /**
     * Returns whether this reminder belongs
     * to an approval stage.
     *
     * @return true if attached to a stage
     */
    public boolean isStageReminder() {
        return approvalPolicyStage != null;
    }

    /**
     * Returns whether this reminder is recurring.
     *
     * @return true if more than one reminder may be sent
     */
    public boolean isRecurring() {
        return reminderFrequency != ApprovalReminderFrequency.ONCE;
    }

    /**
     * Returns whether this reminder requires
     * a recipient reference.
     *
     * @return true if recipient reference is required
     */
    public boolean requiresRecipientReference() {

        return recipientType == ApprovalNotificationRecipientType.USER
                || recipientType == ApprovalNotificationRecipientType.ROLE
                || recipientType == ApprovalNotificationRecipientType.GROUP
                || recipientType == ApprovalNotificationRecipientType.DEPARTMENT
                || recipientType == ApprovalNotificationRecipientType.BUSINESS_UNIT
                || recipientType == ApprovalNotificationRecipientType.BRANCH
                || recipientType == ApprovalNotificationRecipientType.CUSTOM;
    }

    /**
     * Returns whether business calendar calculations
     * are enabled.
     *
     * @return true if using business calendar
     */
    public boolean usesBusinessCalendar() {
        return businessCalendarType != ApprovalBusinessCalendarType.CALENDAR_DAYS;
    }

    /**
     * Returns whether reminders stop after escalation.
     *
     * @return true if configured
     */
    public boolean shouldStopAfterEscalation() {
        return stopAfterEscalation;
    }

    /**
     * Returns whether reminders stop after approval.
     *
     * @return true if configured
     */
    public boolean shouldStopAfterApproval() {
        return stopAfterApproval;
    }

    /**
     * Returns whether reminders stop after rejection.
     *
     * @return true if configured
     */
    public boolean shouldStopAfterRejection() {
        return stopAfterRejection;
    }

    /**
     * Returns whether a recipient reference
     * has been configured.
     *
     * @return true if configured
     */
    public boolean hasRecipientReference() {
        return recipientReference != null
                && !recipientReference.isBlank();
    }

    /**
     * Returns whether a template has been configured.
     *
     * @return true if template exists
     */
    public boolean hasTemplate() {
        return templateCode != null
                && !templateCode.isBlank();
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

    public String getReminderCode() {
        return reminderCode;
    }

    public void setReminderCode(String reminderCode) {
        this.reminderCode = reminderCode;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalNotificationChannel getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationChannel(ApprovalNotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    public ApprovalNotificationRecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(ApprovalNotificationRecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public String getRecipientReference() {
        return recipientReference;
    }

    public void setRecipientReference(String recipientReference) {
        this.recipientReference = recipientReference;
    }

    public Long getFirstReminderAfter() {
        return firstReminderAfter;
    }

    public void setFirstReminderAfter(Long firstReminderAfter) {
        this.firstReminderAfter = firstReminderAfter;
    }

    public ApprovalTimeoutUnit getTimeoutUnit() {
        return timeoutUnit;
    }

    public void setTimeoutUnit(ApprovalTimeoutUnit timeoutUnit) {
        this.timeoutUnit = timeoutUnit;
    }

    public ApprovalReminderFrequency getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(ApprovalReminderFrequency reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
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

    public Integer getMaximumReminders() {
        return maximumReminders;
    }

    public void setMaximumReminders(Integer maximumReminders) {
        this.maximumReminders = maximumReminders;
    }

    public ApprovalBusinessCalendarType getBusinessCalendarType() {
        return businessCalendarType;
    }

    public void setBusinessCalendarType(ApprovalBusinessCalendarType businessCalendarType) {
        this.businessCalendarType = businessCalendarType;
    }

    public boolean isStopAfterEscalation() {
        return stopAfterEscalation;
    }

    public void setStopAfterEscalation(boolean stopAfterEscalation) {
        this.stopAfterEscalation = stopAfterEscalation;
    }

    public boolean isStopAfterApproval() {
        return stopAfterApproval;
    }

    public void setStopAfterApproval(boolean stopAfterApproval) {
        this.stopAfterApproval = stopAfterApproval;
    }

    public boolean isStopAfterRejection() {
        return stopAfterRejection;
    }

    public void setStopAfterRejection(boolean stopAfterRejection) {
        this.stopAfterRejection = stopAfterRejection;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * Validates this reminder policy configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null && approvalPolicyStage == null) {
            throw new IllegalStateException(
                    "A reminder policy must belong to either an approval policy or an approval policy stage.");
        }

        if (approvalPolicy != null && approvalPolicyStage != null) {
            throw new IllegalStateException(
                    "A reminder policy cannot belong to both an approval policy and an approval policy stage.");
        }

        if (reminderCode == null || reminderCode.isBlank()) {
            throw new IllegalStateException(
                    "Reminder code is required.");
        }

        if (reminderName == null || reminderName.isBlank()) {
            throw new IllegalStateException(
                    "Reminder name is required.");
        }

        if (notificationChannel == null) {
            throw new IllegalStateException(
                    "Notification channel is required.");
        }

        if (recipientType == null) {
            throw new IllegalStateException(
                    "Recipient type is required.");
        }

        if (firstReminderAfter == null || firstReminderAfter < 1) {
            throw new IllegalStateException(
                    "First reminder delay must be greater than zero.");
        }

        if (timeoutUnit == null) {
            throw new IllegalStateException(
                    "Timeout unit is required.");
        }

        if (reminderFrequency == null) {
            throw new IllegalStateException(
                    "Reminder frequency is required.");
        }

        if (businessCalendarType == null) {
            throw new IllegalStateException(
                    "Business calendar type is required.");
        }

        if (maximumReminders == null || maximumReminders < 1) {
            throw new IllegalStateException(
                    "Maximum reminders must be greater than zero.");
        }

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (templateCode == null || templateCode.isBlank()) {
            throw new IllegalStateException(
                    "Template code is required.");
        }

        if (requiresRecipientReference() && !hasRecipientReference()) {
            throw new IllegalStateException(
                    "Recipient reference is required for recipient type " + recipientType + ".");
        }

        if (isRecurring()) {

            if (repeatInterval == null || repeatInterval < 1) {
                throw new IllegalStateException(
                        "Repeat interval must be greater than zero for recurring reminders.");
            }

            if (repeatIntervalUnit == null) {
                throw new IllegalStateException(
                        "Repeat interval unit is required for recurring reminders.");
            }
        }
    }

    /**
     * Returns whether this reminder policy
     * is executable.
     *
     * @return true if enabled
     */
    public boolean isExecutable() {
        return isEnabled();
    }

    /**
     * Returns whether repeat interval
     * has been configured.
     *
     * @return true if configured
     */
    public boolean hasRepeatInterval() {
        return repeatInterval != null && repeatInterval > 0;
    }

    @Override
    public String toString() {
        return "ApprovalReminderPolicy{" +
                "id=" + getId() +
                ", reminderCode='" + reminderCode + '\'' +
                ", reminderName='" + reminderName + '\'' +
                ", notificationChannel=" + notificationChannel +
                ", recipientType=" + recipientType +
                ", reminderFrequency=" + reminderFrequency +
                ", firstReminderAfter=" + firstReminderAfter +
                ", timeoutUnit=" + timeoutUnit +
                ", maximumReminders=" + maximumReminders +
                ", executionOrder=" + executionOrder +
                ", active=" + isActive() +
                '}';
    }
}