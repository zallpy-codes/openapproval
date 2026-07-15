package com.zallpy.openapproval.approval.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.common.entity.BaseEntity;
import com.zallpy.openapproval.common.exception.ApprovalValidationException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a business request submitted to the OpenApproval Engine.
 *
 * <p>
 * Every approval initiated by a client application creates exactly one
 * {@code ApprovalRequest}. The request references the approval policy
 * that governs its execution and owns the runtime approval workflow.
 * </p>
 *
 * <p>
 * Examples:
 * </p>
 * <ul>
 * <li>Purchase Order Approval</li>
 * <li>Leave Request Approval</li>
 * <li>Expense Approval</li>
 * <li>Vendor Registration Approval</li>
 * <li>Loan Approval</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_request", indexes = {
        @Index(name = "idx_request_reference", columnList = "request_reference"),
        @Index(name = "idx_request_resource", columnList = "resource_type,resource_id"),
        @Index(name = "idx_request_status", columnList = "status"),
        @Index(name = "idx_request_submitted_by", columnList = "submitted_by")
})
public class ApprovalRequest extends BaseEntity {

    /**
     * Unique business request reference.
     */
    @Column(name = "request_reference", nullable = false, unique = true, length = 100)
    private String requestReference;

    /**
     * Business resource type.
     *
     * Example:
     * PURCHASE_ORDER
     * LEAVE_REQUEST
     */
    @Column(name = "resource_type", nullable = false, length = 100)
    private String resourceType;

    /**
     * Identifier of the business resource.
     */
    @Column(name = "resource_id", nullable = false)
    private UUID resourceId;

    /**
     * Approval title.
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Approval description.
     */
    @Column(name = "description", length = 4000)
    private String description;

    /**
     * Current approval status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    /**
     * User that submitted the request.
     */
    @Column(name = "submitted_by", nullable = false)
    private UUID submittedBy;

    /**
     * Date and time the request was submitted.
     */
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    /**
     * Approval policy governing this request.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Runtime workflow.
     */
    @OneToOne(mappedBy = "approvalRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ApprovalWorkflow workflow;

    /**
     * Optional business key supplied by the client application.
     */
    @Column(name = "business_key", length = 150)
    private String businessKey;

    /**
     * Correlation identifier for distributed tracing.
     */
    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    /**
     * Request priority.
     */
    @Column(name = "priority", length = 30)
    private String priority;

    /**
     * Due date for completing this approval.
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /**
     * Additional business attributes supplied by the client.
     */
    @Lob
    @Column(name = "attributes")
    private String attributes;

    /**
     * Indicates whether this request has been completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    /**
     * Date and time completion occurred.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * User that completed the request.
     */
    @Column(name = "completed_by")
    private UUID completedBy;

    /**
     * Indicates whether this request has been cancelled.
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;

    /**
     * Date and time cancellation occurred.
     */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    /**
     * User that cancelled the request.
     */
    @Column(name = "cancelled_by")
    private UUID cancelledBy;

    /**
     * Cancellation reason.
     */
    @Column(name = "cancellation_reason", length = 2000)
    private String cancellationReason;

    /**
     * Indicates whether this request has been recalled.
     */
    @Column(name = "recalled", nullable = false)
    private boolean recalled = false;

    /**
     * Date and time recall occurred.
     */
    @Column(name = "recalled_at")
    private LocalDateTime recalledAt;

    /**
     * User that recalled the request.
     */
    @Column(name = "recalled_by")
    private UUID recalledBy;

    /**
     * Recall reason.
     */
    @Column(name = "recall_reason", length = 2000)
    private String recallReason;

    /*
     * ==========================================================
     * Getters
     * ==========================================================
     */

    /**
     * Returns the request reference.
     *
     * @return request reference
     */
    public String getRequestReference() {
        return requestReference;
    }

    /**
     * Returns the business resource type.
     *
     * @return resource type
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Returns the business resource identifier.
     *
     * @return resource identifier
     */
    public UUID getResourceId() {
        return resourceId;
    }

    /**
     * Returns the approval title.
     *
     * @return approval title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the approval description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the approval status.
     *
     * @return approval status
     */
    public ApprovalStatus getStatus() {
        return status;
    }

    /**
     * Returns the submitting user.
     *
     * @return submitted by
     */
    public UUID getSubmittedBy() {
        return submittedBy;
    }

    /**
     * Returns the submission timestamp.
     *
     * @return submitted at
     */
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    /**
     * Returns the approval policy.
     *
     * @return approval policy
     */
    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    /**
     * Returns the runtime workflow.
     *
     * @return workflow
     */
    public ApprovalWorkflow getWorkflow() {
        return workflow;
    }

    /**
     * Returns the business key.
     *
     * @return business key
     */
    public String getBusinessKey() {
        return businessKey;
    }

    /**
     * Returns the correlation identifier.
     *
     * @return correlation identifier
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Returns the priority.
     *
     * @return priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Returns the due date.
     *
     * @return due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Returns the additional attributes.
     *
     * @return attributes
     */
    public String getAttributes() {
        return attributes;
    }

    /**
     * Returns whether this request is completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Returns whether this request is cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns whether this request is recalled.
     *
     * @return true if recalled
     */
    public boolean isRecalled() {
        return recalled;
    }

    /**
     * Returns the completion timestamp.
     *
     * @return completion timestamp
     */
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    /**
     * Returns the completion user.
     *
     * @return completed by
     */
    public UUID getCompletedBy() {
        return completedBy;
    }

    /**
     * Returns the cancellation timestamp.
     *
     * @return cancelled at
     */
    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    /**
     * Returns the cancellation user.
     *
     * @return cancelled by
     */
    public UUID getCancelledBy() {
        return cancelledBy;
    }

    /**
     * Returns the cancellation reason.
     *
     * @return cancellation reason
     */
    public String getCancellationReason() {
        return cancellationReason;
    }

    /**
     * Returns the recall timestamp.
     *
     * @return recalled at
     */
    public LocalDateTime getRecalledAt() {
        return recalledAt;
    }

    /**
     * Returns the recall user.
     *
     * @return recalled by
     */
    public UUID getRecalledBy() {
        return recalledBy;
    }

    /**
     * Returns the recall reason.
     *
     * @return recall reason
     */
    public String getRecallReason() {
        return recallReason;
    }

    /*
     * ==========================================================
     * Controlled Setters
     * ==========================================================
     */

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPriority(final String priority) {
        this.priority = priority;
    }

    public void setBusinessKey(final String businessKey) {
        this.businessKey = businessKey;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    public void setDueDate(final LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setAttributes(final String attributes) {
        this.attributes = attributes;
    }

    public void setApprovalPolicy(final ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    /**
     * Assigns the runtime workflow while maintaining the
     * bidirectional relationship.
     *
     * @param workflow approval workflow
     */
    public void setWorkflow(final ApprovalWorkflow workflow) {

        this.workflow = workflow;

        if (workflow != null
                && workflow.getApprovalRequest() != this) {
            workflow.setApprovalRequest(this);
        }
    }

    /*
     * ==========================================================
     * Lifecycle
     * ==========================================================
     */

    /**
     * Marks this request as completed.
     *
     * @param completedBy user completing the request
     */
    public void complete(final UUID completedBy) {

        this.completed = true;
        this.completedAt = LocalDateTime.now();
        this.completedBy = completedBy;
    }

    /**
     * Cancels this approval request.
     *
     * @param cancelledBy cancelling user
     * @param reason      cancellation reason
     */
    public void cancel(final UUID cancelledBy,
            final String reason) {

        this.cancelled = true;
        this.cancelledAt = LocalDateTime.now();
        this.cancelledBy = cancelledBy;
        this.cancellationReason = reason;
    }

    /**
     * Recalls this approval request.
     *
     * @param recalledBy recalling user
     * @param reason     recall reason
     */
    public void recall(final UUID recalledBy,
            final String reason) {

        this.recalled = true;
        this.recalledAt = LocalDateTime.now();
        this.recalledBy = recalledBy;
        this.recallReason = reason;
    }

    /**
     * Updates the approval status.
     *
     * @param status new approval status
     */
    public void updateStatus(final ApprovalStatus status) {

        if (status != null) {
            this.status = status;
        }
    }

    /*
     * ==========================================================
     * Business Rules
     * ==========================================================
     */

    /**
     * Determines whether this request is active.
     *
     * @return true if active
     */
    public boolean isActive() {

        return !completed
                && !cancelled
                && !recalled;
    }

    /**
     * Determines whether this request has reached
     * a terminal state.
     *
     * @return true if terminal
     */
    public boolean isTerminal() {

        return completed
                || cancelled
                || recalled;
    }

    /**
     * Determines whether this request can be executed.
     *
     * @return true if executable
     */
    public boolean isExecutable() {

        return approvalPolicy != null
                && workflow != null
                && approvalPolicy.isExecutable()
                && isActive();
    }

    /**
     * Determines whether this request has a due date.
     *
     * @return true if due date exists
     */
    public boolean hasDueDate() {
        return dueDate != null;
    }

    /**
     * Determines whether this request is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueDate != null
                && LocalDateTime.now().isAfter(dueDate)
                && !isTerminal();
    }

    /**
     * Determines whether this request has
     * an approval policy.
     *
     * @return true if attached to a policy
     */
    public boolean hasApprovalPolicy() {
        return approvalPolicy != null;
    }

    /**
     * Determines whether this request has
     * a runtime workflow.
     *
     * @return true if workflow exists
     */
    public boolean hasWorkflow() {
        return workflow != null;
    }

    /**
     * Determines whether this request
     * belongs to the specified resource.
     *
     * @param resourceType business resource type
     * @param resourceId   business resource identifier
     * @return true if matching
     */
    public boolean belongsTo(final String resourceType,
            final UUID resourceId) {

        if (resourceType == null || resourceId == null) {
            return false;
        }

        return resourceType.equals(this.resourceType)
                && resourceId.equals(this.resourceId);
    }

    /**
     * Determines whether this request
     * was submitted by the supplied user.
     *
     * @param userId user identifier
     * @return true if submitted by user
     */
    public boolean isSubmittedBy(final UUID userId) {

        return userId != null
                && userId.equals(this.submittedBy);
    }

    /**
     * Determines whether this request
     * has additional attributes.
     *
     * @return true if attributes exist
     */
    public boolean hasAttributes() {

        return attributes != null
                && !attributes.isBlank();
    }

    public void setRequestReference(final String requestReference) {
        this.requestReference = requestReference;
    }

    public void setResourceType(final String resourceType) {
        this.resourceType = resourceType;
    }

    public void setResourceId(final UUID resourceId) {
        this.resourceId = resourceId;
    }

    public void setStatus(final ApprovalStatus status) {
        this.status = status;
    }

    public void setSubmittedBy(final UUID submittedBy) {
        this.submittedBy = submittedBy;
    }

    public void setSubmittedAt(final LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    public void setCompletedAt(final LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setCompletedBy(final UUID completedBy) {
        this.completedBy = completedBy;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setCancelledAt(final LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public void setCancelledBy(final UUID cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public void setCancellationReason(final String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public void setRecalled(final boolean recalled) {
        this.recalled = recalled;
    }

    public void setRecalledAt(final LocalDateTime recalledAt) {
        this.recalledAt = recalledAt;
    }

    public void setRecalledBy(final UUID recalledBy) {
        this.recalledBy = recalledBy;
    }

    public void setRecallReason(final String recallReason) {
        this.recallReason = recallReason;
    }

    public void setAttributes(final Map<String, Object> attributes) {

        if (attributes == null || attributes.isEmpty()) {
            this.attributes = null;
            return;
        }

        try {
            this.attributes = new ObjectMapper()
                    .writeValueAsString(attributes);
        } catch (JsonProcessingException ex) {
            throw new ApprovalValidationException(
                    "Unable to serialize approval attributes.",
                    ex);
        }
    }

}