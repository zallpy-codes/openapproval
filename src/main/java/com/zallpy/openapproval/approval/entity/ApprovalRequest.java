package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a business request submitted to the OpenApproval engine.
 *
 * <p>
 * Every approval initiated by a client application creates exactly one
 * {@code ApprovalRequest}. The request references the approval policy
 * that governs its execution and owns the runtime workflow responsible
 * for processing approvals.
 * </p>
 *
 * <p>
 * Examples:
 * </p>
 * <ul>
 * <li>Purchase Order Approval</li>
 * <li>Leave Request Approval</li>
 * <li>Vendor Registration Approval</li>
 * <li>Expense Approval</li>
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
     * Unique request reference.
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
     * Submission timestamp.
     */
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    /**
     * Approval policy used for this request.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Runtime workflow for this request.
     */
    @OneToOne(mappedBy = "approvalRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ApprovalWorkflow workflow;

    /**
     * Optional business key supplied by the client application.
     */
    @Column(name = "business_key", length = 150)
    private String businessKey;

    /**
     * Correlation identifier used for distributed tracing.
     */
    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    /**
     * Priority assigned to this approval request.
     */
    @Column(name = "priority", length = 30)
    private String priority;

    /**
     * Due date for completing the approval request.
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /**
     * Indicates whether the request has been completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    /**
     * Date and time the request was completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * User that completed the approval request.
     */
    @Column(name = "completed_by")
    private UUID completedBy;

    /**
     * Indicates whether the request has been cancelled.
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;

    /**
     * Date and time the request was cancelled.
     */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    /**
     * User that cancelled the request.
     */
    @Column(name = "cancelled_by")
    private UUID cancelledBy;

    /**
     * Reason for cancelling the request.
     */
    @Column(name = "cancellation_reason", length = 2000)
    private String cancellationReason;

    /**
     * Indicates whether the request has been recalled.
     */
    @Column(name = "recalled", nullable = false)
    private boolean recalled = false;

    /**
     * Date and time the request was recalled.
     */
    @Column(name = "recalled_at")
    private LocalDateTime recalledAt;

    /**
     * User that recalled the request.
     */
    @Column(name = "recalled_by")
    private UUID recalledBy;

    /**
     * Reason for recalling the request.
     */
    @Column(name = "recall_reason", length = 2000)
    private String recallReason;

    /**
     * Additional business attributes supplied by the client
     * application during submission.
     */
    @Lob
    @Column(name = "attributes")
    private String attributes;

    /**
     * Assigns the runtime workflow to this request.
     *
     * @param workflow approval workflow
     */
    public void setWorkflow(final ApprovalWorkflow workflow) {

        this.workflow = workflow;

        if (workflow != null && workflow.getApprovalRequest() != this) {
            workflow.setApprovalRequest(this);
        }
    }

    /**
     * Marks this request as completed.
     *
     * @param completedBy user that completed the request
     */
    public void complete(final UUID completedBy) {

        this.completed = true;
        this.completedAt = LocalDateTime.now();
        this.completedBy = completedBy;
    }

    /**
     * Cancels this request.
     *
     * @param cancelledBy user cancelling the request
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
     * Recalls this request.
     *
     * @param recalledBy user recalling the request
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
     * Determines whether this request is currently active.
     *
     * @return true if active
     */
    public boolean isActive() {

        return !completed
                && !cancelled
                && !recalled;
    }

    /**
     * Determines whether this request has reached a terminal state.
     *
     * @return true if terminal
     */
    public boolean isTerminal() {

        return completed
                || cancelled
                || recalled;
    }

    /**
     * Determines whether this request can be processed.
     *
     * @return true if executable
     */
    public boolean isExecutable() {

        return approvalPolicy != null
                && workflow != null
                && isActive();
    }

    /**
     * Returns whether the request has a due date.
     *
     * @return true if due date exists
     */
    public boolean hasDueDate() {
        return dueDate != null;
    }

    /**
     * Returns whether this request is overdue.
     *
     * @return true if overdue
     */
    public boolean isOverdue() {

        return dueDate != null
                && LocalDateTime.now().isAfter(dueDate)
                && !isTerminal();
    }

    /**
     * Returns whether the request has been cancelled.
     *
     * @return true if cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns whether the request has been recalled.
     *
     * @return true if recalled
     */
    public boolean isRecalled() {
        return recalled;
    }

    /**
     * Returns whether the request has been completed.
     *
     * @return true if completed
     */
    public boolean isCompleted() {
        return completed;
    }

    public ApprovalWorkflow getWorkflow() {
        return workflow;
    }

}