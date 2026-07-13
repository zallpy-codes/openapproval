package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalNotificationChannel;
import com.zallpy.openapproval.approval.enums.ApprovalNotificationEvent;
import com.zallpy.openapproval.approval.enums.ApprovalNotificationRecipientType;
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
 * Defines a notification configuration for an approval policy or
 * approval policy stage.
 *
 * <p>
 * Notifications are fully configurable and determine:
 * <ul>
 * <li>Which event triggers the notification.</li>
 * <li>Who receives the notification.</li>
 * <li>Which communication channel is used.</li>
 * <li>Which template should be rendered.</li>
 * <li>Whether delivery is immediate or delayed.</li>
 * </ul>
 *
 * <p>
 * A notification may be attached either to an entire
 * {@link ApprovalPolicy} or to a specific
 * {@link ApprovalPolicyStage}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_notifications", indexes = {
        @Index(name = "idx_notification_policy", columnList = "policy_id"),
        @Index(name = "idx_notification_stage", columnList = "stage_id"),
        @Index(name = "idx_notification_event", columnList = "notification_event"),
        @Index(name = "idx_notification_channel", columnList = "notification_channel"),
        @Index(name = "idx_notification_active", columnList = "active")
})
public class ApprovalPolicyNotification extends ActiveEntity {

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
    @Column(name = "notification_code", nullable = false, unique = true, length = 80)
    private String notificationCode;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "notification_name", nullable = false, length = 150)
    private String notificationName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Notification trigger event.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_event", nullable = false, length = 50)
    private ApprovalNotificationEvent notificationEvent;

    /**
     * Notification delivery channel.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_channel", nullable = false, length = 50)
    private ApprovalNotificationChannel notificationChannel;

    /**
     * Notification recipient.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false, length = 50)
    private ApprovalNotificationRecipientType recipientType;

    /**
     * Optional recipient identifier.
     *
     * Used when recipient type is ROLE, GROUP,
     * USER, etc.
     */
    @Size(max = 120)
    @Column(name = "recipient_reference", length = 120)
    private String recipientReference;

    /**
     * Notification template code.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "template_code", nullable = false, length = 100)
    private String templateCode;

    /**
     * Subject template.
     */
    @Size(max = 500)
    @Column(name = "subject_template", length = 500)
    private String subjectTemplate;

    /**
     * Message template.
     */
    @Size(max = 5000)
    @Column(name = "message_template", length = 5000)
    private String messageTemplate;

    /**
     * Send notification immediately.
     */
    @Column(name = "send_immediately", nullable = false)
    private boolean sendImmediately = true;

    /**
     * Delay before sending.
     */
    @Min(0)
    @Column(name = "delay_minutes")
    private Long delayMinutes;

    /**
     * Execution order.
     */
    @Min(1)
    @Column(name = "execution_order")
    private Integer executionOrder = 1;

    /**
     * Indicates whether notification failures
     * should stop workflow execution.
     */
    @Column(name = "fail_on_error", nullable = false)
    private boolean failOnError = false;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyNotification other)) {
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
    public ApprovalPolicyNotification() {
    }

    /**
     * Returns whether this notification belongs to
     * an approval policy.
     *
     * @return true if attached to a policy
     */
    public boolean isPolicyNotification() {
        return approvalPolicy != null;
    }

    /**
     * Returns whether this notification belongs to
     * an approval stage.
     *
     * @return true if attached to a stage
     */
    public boolean isStageNotification() {
        return approvalPolicyStage != null;
    }

    /**
     * Returns whether a template code has been configured.
     *
     * @return true if a template code exists
     */
    public boolean hasTemplate() {
        return templateCode != null && !templateCode.isBlank();
    }

    /**
     * Returns whether an inline subject template exists.
     *
     * @return true if a subject template has been configured
     */
    public boolean hasSubjectTemplate() {
        return subjectTemplate != null && !subjectTemplate.isBlank();
    }

    /**
     * Returns whether an inline message template exists.
     *
     * @return true if a message template has been configured
     */
    public boolean hasMessageTemplate() {
        return messageTemplate != null && !messageTemplate.isBlank();
    }

    /**
     * Returns whether this notification should be sent
     * immediately.
     *
     * @return true if immediate delivery is enabled
     */
    public boolean shouldSendImmediately() {
        return sendImmediately;
    }

    /**
     * Returns whether delayed delivery is configured.
     *
     * @return true if delay is greater than zero
     */
    public boolean isDelayedNotification() {
        return delayMinutes != null && delayMinutes > 0;
    }

    /**
     * Returns whether workflow execution should stop
     * if notification delivery fails.
     *
     * @return true if failures are fatal
     */
    public boolean shouldFailOnError() {
        return failOnError;
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

    public String getNotificationCode() {
        return notificationCode;
    }

    public void setNotificationCode(String notificationCode) {
        this.notificationCode = notificationCode;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalNotificationEvent getNotificationEvent() {
        return notificationEvent;
    }

    public void setNotificationEvent(ApprovalNotificationEvent notificationEvent) {
        this.notificationEvent = notificationEvent;
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public boolean isSendImmediately() {
        return sendImmediately;
    }

    public void setSendImmediately(boolean sendImmediately) {
        this.sendImmediately = sendImmediately;
    }

    public Long getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(Long delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public boolean isFailOnError() {
        return failOnError;
    }

    public void setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
    }

    /**
     * Validates this notification configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null && approvalPolicyStage == null) {
            throw new IllegalStateException(
                    "A notification must belong to either an approval policy or an approval policy stage.");
        }

        if (approvalPolicy != null && approvalPolicyStage != null) {
            throw new IllegalStateException(
                    "A notification cannot belong to both an approval policy and an approval policy stage.");
        }

        if (notificationCode == null || notificationCode.isBlank()) {
            throw new IllegalStateException(
                    "Notification code is required.");
        }

        if (notificationName == null || notificationName.isBlank()) {
            throw new IllegalStateException(
                    "Notification name is required.");
        }

        if (notificationEvent == null) {
            throw new IllegalStateException(
                    "Notification event is required.");
        }

        if (notificationChannel == null) {
            throw new IllegalStateException(
                    "Notification channel is required.");
        }

        if (recipientType == null) {
            throw new IllegalStateException(
                    "Notification recipient type is required.");
        }

        if (templateCode == null || templateCode.isBlank()) {
            throw new IllegalStateException(
                    "Template code is required.");
        }

        if (!sendImmediately && (delayMinutes == null || delayMinutes < 0)) {
            throw new IllegalStateException(
                    "Delay minutes must be zero or greater.");
        }

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        /*
         * Recipient reference is mandatory for recipient
         * types that identify a specific target.
         *
         * Examples:
         * USER
         * ROLE
         * GROUP
         * DEPARTMENT
         * BUSINESS_UNIT
         */
        switch (recipientType) {

            case USER:
            case ROLE:
            case GROUP:
            case DEPARTMENT:
            case BUSINESS_UNIT:

                if (recipientReference == null || recipientReference.isBlank()) {
                    throw new IllegalStateException(
                            "Recipient reference is required for recipient type " + recipientType + ".");
                }

                break;

            default:
                break;
        }
    }

    /**
     * Returns whether this notification has
     * a recipient reference.
     *
     * @return true if configured
     */
    public boolean hasRecipientReference() {
        return recipientReference != null
                && !recipientReference.isBlank();
    }

    /**
     * Returns whether this notification
     * is executable.
     *
     * @return true if enabled
     */
    public boolean isExecutable() {
        return isEnabled();
    }

    @Override
    public String toString() {
        return "ApprovalPolicyNotification{" +
                "id=" + getId() +
                ", notificationCode='" + notificationCode + '\'' +
                ", notificationName='" + notificationName + '\'' +
                ", notificationEvent=" + notificationEvent +
                ", notificationChannel=" + notificationChannel +
                ", recipientType=" + recipientType +
                ", sendImmediately=" + sendImmediately +
                ", executionOrder=" + executionOrder +
                ", active=" + isActive() +
                '}';
    }

}