package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalInitiatorType;
import com.zallpy.openapproval.approval.enums.ApprovalPriority;
import com.zallpy.openapproval.approval.enums.ApprovalRequestSource;
import com.zallpy.openapproval.approval.enums.ApprovalRequestStatus;
import com.zallpy.openapproval.approval.enums.ApprovalRequestType;
import com.zallpy.openapproval.approval.policy.ApprovalPolicy;
import com.zallpy.openapproval.approval.policy.ApprovalPolicyVersion;
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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a runtime approval request.
 *
 * <p>
 * This is the aggregate root of the OpenApproval Runtime Engine.
 * Every approval workflow execution begins with an ApprovalRequest.
 *
 * <p>
 * An ApprovalRequest represents a single business transaction that
 * is being processed through an approval workflow.
 *
 * Examples:
 * <ul>
 * <li>Purchase Order Approval</li>
 * <li>Loan Application Approval</li>
 * <li>Expense Reimbursement</li>
 * <li>Leave Request</li>
 * <li>Vendor Registration</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_requests", indexes = {
        @Index(name = "idx_request_number", columnList = "request_number"),
        @Index(name = "idx_request_uuid", columnList = "request_uuid"),
        @Index(name = "idx_request_business_key", columnList = "business_key"),
        @Index(name = "idx_request_status", columnList = "status"),
        @Index(name = "idx_request_policy", columnList = "policy_id"),
        @Index(name = "idx_request_tenant", columnList = "tenant_id"),
        @Index(name = "idx_request_priority", columnList = "priority"),
        @Index(name = "idx_request_initiator", columnList = "initiator_id"),
        @Index(name = "idx_request_correlation", columnList = "correlation_id"),
        @Index(name = "idx_request_active", columnList = "active")
})
public class ApprovalRequest extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Approval policy used for this request.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Policy version used during execution.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_version_id")
    private ApprovalPolicyVersion approvalPolicyVersion;

    /**
     * Tenant identifier.
     */
    @NotNull
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;

    /**
     * Correlation identifier used for
     * distributed tracing.
     */
    @NotNull
    @Column(name = "correlation_id", nullable = false)
    private UUID correlationId = UUID.randomUUID();

    /**
     * Human-readable request number.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "request_number", nullable = false, unique = true, length = 80)
    private String requestNumber;

    /**
     * External business key.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "business_key", nullable = false, length = 150)
    private String businessKey;

    /**
     * External reference number.
     */
    @Size(max = 100)
    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    /**
     * Request title.
     */
    @NotBlank
    @Size(max = 250)
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    /**
     * Detailed request description.
     */
    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    /**
     * Runtime request type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false, length = 40)
    private ApprovalRequestType requestType;

    /**
     * Originating request source.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "request_source", nullable = false, length = 40)
    private ApprovalRequestSource requestSource;

    /**
     * Current runtime status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ApprovalRequestStatus status = ApprovalRequestStatus.DRAFT;

    /**
     * Processing priority.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 30)
    private ApprovalPriority priority = ApprovalPriority.NORMAL;

    /**
     * Initiator type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "initiator_type", nullable = false, length = 30)
    private ApprovalInitiatorType initiatorType;

    /**
     * Initiator identifier.
     */
    @NotNull
    @Column(name = "initiator_id", nullable = false)
    private UUID initiatorId;

    /**
     * Initiator display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "initiator_name", nullable = false, length = 150)
    private String initiatorName;

    /**
     * Initiator email address.
     */
    @Size(max = 255)
    @Column(name = "initiator_email", length = 255)
    private String initiatorEmail;

    /**
     * Initiator department.
     */
    @Size(max = 120)
    @Column(name = "initiator_department", length = 120)
    private String initiatorDepartment;

    /**
     * Initiator role.
     */
    @Size(max = 120)
    @Column(name = "initiator_role", length = 120)
    private String initiatorRole;

    /**
     * Originating application module.
     *
     * <p>
     * Examples:
     * <ul>
     * <li>Core Banking</li>
     * <li>ERP360 Procurement</li>
     * <li>Human Resources</li>
     * <li>Finance</li>
     * </ul>
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "module_name", nullable = false, length = 100)
    private String moduleName;

    /**
     * Business entity type.
     *
     * <p>
     * Examples:
     * PURCHASE_ORDER,
     * LOAN_APPLICATION,
     * LEAVE_REQUEST.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    /**
     * Business entity name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "entity_name", nullable = false, length = 150)
    private String entityName;

    /**
     * Business entity identifier.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;

    /**
     * Business entity display name.
     */
    @Size(max = 250)
    @Column(name = "entity_display_name", length = 250)
    private String entityDisplayName;

    /**
     * Monetary amount associated with the approval request.
     */
    @DecimalMin(value = "0.00")
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * ISO-4217 currency code.
     */
    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency;

    /**
     * Submission timestamp.
     */
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    /**
     * Due timestamp.
     */
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    /**
     * Completion timestamp.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    /**
     * Last runtime action timestamp.
     */
    @Column(name = "last_action_at")
    private LocalDateTime lastActionAt;

    /**
     * User that performed the most recent action.
     */
    @Size(max = 150)
    @Column(name = "last_action_by", length = 150)
    private String lastActionBy;

    /**
     * Current execution stage order.
     */
    @Min(0)
    @Column(name = "current_stage_order")
    private Integer currentStageOrder = 0;

    /**
     * Total number of execution stages.
     */
    @Min(1)
    @Column(name = "total_stages")
    private Integer totalStages = 0;

    /**
     * Indicates whether the request has completed.
     */
    @Column(name = "completed", nullable = false)
    private boolean completed;

    /**
     * Indicates whether the request has been cancelled.
     */
    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;

    /**
     * Indicates whether the request has been escalated.
     */
    @Column(name = "escalated", nullable = false)
    private boolean escalated;

    /**
     * Indicates whether the request is overdue.
     */
    @Column(name = "overdue", nullable = false)
    private boolean overdue;

    /**
     * Indicates whether the request has been delegated.
     */
    @Column(name = "delegated", nullable = false)
    private boolean delegated;

    /**
     * Indicates whether runtime processing
     * is currently suspended.
     */
    @Column(name = "suspended", nullable = false)
    private boolean suspended;

    /**
     * Indicates whether the configured SLA
     * has been breached.
     */
    @Column(name = "sla_breached", nullable = false)
    private boolean slaBreached;

    /**
     * Number of approval stages completed.
     */
    @Min(0)
    @Column(name = "completed_stages", nullable = false)
    private Integer completedStages = 0;

    /**
     * Default constructor.
     */
    public ApprovalRequest() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalRequest other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Submits this approval request.
     */
    public void submit() {

        this.status = ApprovalRequestStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
        this.lastActionAt = LocalDateTime.now();
        this.lastActionBy = initiatorName;
    }

    /**
     * Starts workflow execution.
     */
    public void start() {

        this.status = ApprovalRequestStatus.IN_PROGRESS;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Approves this request.
     */
    public void approve() {

        this.status = ApprovalRequestStatus.APPROVED;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Rejects this request.
     */
    public void reject() {

        this.status = ApprovalRequestStatus.REJECTED;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Places this request on hold.
     */
    public void hold() {

        this.status = ApprovalRequestStatus.ON_HOLD;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Resumes processing.
     */
    public void resume() {

        this.suspended = false;

        if (status == ApprovalRequestStatus.ON_HOLD) {
            this.status = ApprovalRequestStatus.IN_PROGRESS;
        }

        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Suspends workflow processing.
     */
    public void suspend() {

        this.suspended = true;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Completes this request.
     */
    public void complete() {

        this.completed = true;
        this.status = ApprovalRequestStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.lastActionAt = completedAt;
    }

    /**
     * Cancels this request.
     */
    public void cancel() {

        this.cancelled = true;
        this.status = ApprovalRequestStatus.CANCELLED;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Withdraws this request.
     */
    public void withdraw() {

        this.status = ApprovalRequestStatus.WITHDRAWN;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Expires this request.
     */
    public void expire() {

        this.status = ApprovalRequestStatus.EXPIRED;
        this.lastActionAt = LocalDateTime.now();
    }

    /**
     * Marks this request as overdue.
     */
    public void markOverdue() {
        this.overdue = true;
    }

    /**
     * Clears overdue status.
     */
    public void clearOverdue() {
        this.overdue = false;
    }

    /**
     * Marks this request as escalated.
     */
    public void escalate() {
        this.escalated = true;
    }

    /**
     * Clears escalation status.
     */
    public void clearEscalation() {
        this.escalated = false;
    }

    /**
     * Marks this request as delegated.
     */
    public void delegate() {
        this.delegated = true;
    }

    /**
     * Clears delegation status.
     */
    public void clearDelegation() {
        this.delegated = false;
    }

    /**
     * Marks SLA as breached.
     */
    public void markSlaBreached() {
        this.slaBreached = true;
    }

    /**
     * Clears SLA breach.
     */
    public void clearSlaBreach() {
        this.slaBreached = false;
    }

    /**
     * Advances to the next execution stage.
     */
    public void advanceStage() {

        if (currentStageOrder == null) {
            currentStageOrder = 0;
        }

        currentStageOrder++;
    }

    /**
     * Records completion of a workflow stage.
     */
    public void completeStage() {

        if (completedStages == null) {
            completedStages = 0;
        }

        completedStages++;

        advanceStage();
    }

    /**
     * Returns execution progress percentage.
     *
     * @return progress percentage
     */
    public double getProgressPercentage() {

        if (totalStages == null || totalStages == 0) {
            return 0.0;
        }

        return (completedStages.doubleValue() * 100.0)
                / totalStages.doubleValue();
    }

    /**
     * Updates the last action metadata.
     *
     * @param performedBy user performing the action
     */
    public void updateLastAction(String performedBy) {

        this.lastActionAt = LocalDateTime.now();
        this.lastActionBy = performedBy;
    }

    /**
     * Returns whether this request has been submitted.
     *
     * @return true if submitted
     */
    public boolean isSubmitted() {
        return status == ApprovalRequestStatus.SUBMITTED;
    }

    /**
     * Returns whether this request is currently in progress.
     *
     * @return true if in progress
     */
    public boolean isInProgress() {
        return status == ApprovalRequestStatus.IN_PROGRESS;
    }

    /**
     * Returns whether this request has been approved.
     *
     * @return true if approved
     */
    public boolean isApproved() {
        return status == ApprovalRequestStatus.APPROVED;
    }

    /**
     * Returns whether this request has been rejected.
     *
     * @return true if rejected
     */
    public boolean isRejected() {
        return status == ApprovalRequestStatus.REJECTED;
    }

    /**
     * Returns whether this request is pending.
     *
     * @return true if awaiting completion
     */
    public boolean isPending() {

        return status == ApprovalRequestStatus.SUBMITTED
                || status == ApprovalRequestStatus.IN_PROGRESS
                || status == ApprovalRequestStatus.ON_HOLD;
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

    public ApprovalPolicyVersion getApprovalPolicyVersion() {
        return approvalPolicyVersion;
    }

    public void setApprovalPolicyVersion(
            ApprovalPolicyVersion approvalPolicyVersion) {
        this.approvalPolicyVersion = approvalPolicyVersion;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(
            ApprovalRequestType requestType) {
        this.requestType = requestType;
    }

    public ApprovalRequestSource getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(
            ApprovalRequestSource requestSource) {
        this.requestSource = requestSource;
    }

    public ApprovalRequestStatus getStatus() {
        return status;
    }

    public void setStatus(
            ApprovalRequestStatus status) {
        this.status = status;
    }

    public ApprovalPriority getPriority() {
        return priority;
    }

    public void setPriority(
            ApprovalPriority priority) {
        this.priority = priority;
    }

    public ApprovalInitiatorType getInitiatorType() {
        return initiatorType;
    }

    public void setInitiatorType(
            ApprovalInitiatorType initiatorType) {
        this.initiatorType = initiatorType;
    }

    public UUID getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(UUID initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getInitiatorEmail() {
        return initiatorEmail;
    }

    public void setInitiatorEmail(String initiatorEmail) {
        this.initiatorEmail = initiatorEmail;
    }

    public String getInitiatorDepartment() {
        return initiatorDepartment;
    }

    public void setInitiatorDepartment(String initiatorDepartment) {
        this.initiatorDepartment = initiatorDepartment;
    }

    public String getInitiatorRole() {
        return initiatorRole;
    }

    public void setInitiatorRole(String initiatorRole) {
        this.initiatorRole = initiatorRole;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityDisplayName() {
        return entityDisplayName;
    }

    public void setEntityDisplayName(String entityDisplayName) {
        this.entityDisplayName = entityDisplayName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getLastActionAt() {
        return lastActionAt;
    }

    public void setLastActionAt(LocalDateTime lastActionAt) {
        this.lastActionAt = lastActionAt;
    }

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public Integer getCurrentStageOrder() {
        return currentStageOrder;
    }

    public void setCurrentStageOrder(Integer currentStageOrder) {
        this.currentStageOrder = currentStageOrder;
    }

    public Integer getTotalStages() {
        return totalStages;
    }

    public void setTotalStages(Integer totalStages) {
        this.totalStages = totalStages;
    }

    public Integer getCompletedStages() {
        return completedStages;
    }

    public void setCompletedStages(Integer completedStages) {
        this.completedStages = completedStages;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isEscalated() {
        return escalated;
    }

    public void setEscalated(boolean escalated) {
        this.escalated = escalated;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public boolean isDelegated() {
        return delegated;
    }

    public void setDelegated(boolean delegated) {
        this.delegated = delegated;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isSlaBreached() {
        return slaBreached;
    }

    public void setSlaBreached(boolean slaBreached) {
        this.slaBreached = slaBreached;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates the runtime approval request.
     *
     * @throws IllegalStateException if the request configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null) {
            throw new IllegalStateException("Approval policy is required.");
        }

        if (tenantId == null) {
            throw new IllegalStateException("Tenant identifier is required.");
        }

        if (requestNumber == null || requestNumber.isBlank()) {
            throw new IllegalStateException("Request number is required.");
        }

        if (businessKey == null || businessKey.isBlank()) {
            throw new IllegalStateException("Business key is required.");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalStateException("Title is required.");
        }

        if (requestType == null) {
            throw new IllegalStateException("Request type is required.");
        }

        if (requestSource == null) {
            throw new IllegalStateException("Request source is required.");
        }

        if (status == null) {
            throw new IllegalStateException("Status is required.");
        }

        if (priority == null) {
            throw new IllegalStateException("Priority is required.");
        }

        if (initiatorType == null) {
            throw new IllegalStateException("Initiator type is required.");
        }

        if (initiatorId == null) {
            throw new IllegalStateException("Initiator identifier is required.");
        }

        if (initiatorName == null || initiatorName.isBlank()) {
            throw new IllegalStateException("Initiator name is required.");
        }

        if (moduleName == null || moduleName.isBlank()) {
            throw new IllegalStateException("Module name is required.");
        }

        if (entityType == null || entityType.isBlank()) {
            throw new IllegalStateException("Entity type is required.");
        }

        if (entityName == null || entityName.isBlank()) {
            throw new IllegalStateException("Entity name is required.");
        }

        if (entityId == null || entityId.isBlank()) {
            throw new IllegalStateException("Entity identifier is required.");
        }

        if (amount != null && amount.signum() < 0) {
            throw new IllegalStateException("Amount cannot be negative.");
        }

        if (currentStageOrder != null && currentStageOrder < 0) {
            throw new IllegalStateException("Current stage order cannot be negative.");
        }

        if (completedStages != null && completedStages < 0) {
            throw new IllegalStateException("Completed stages cannot be negative.");
        }

        if (totalStages != null && totalStages < 0) {
            throw new IllegalStateException("Total stages cannot be negative.");
        }

        if (completedStages != null
                && totalStages != null
                && completedStages > totalStages) {

            throw new IllegalStateException(
                    "Completed stages cannot exceed total stages.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this request has an approval amount.
     *
     * @return true if amount exists
     */
    public boolean hasAmount() {
        return amount != null;
    }

    /**
     * Returns whether a due date has been configured.
     *
     * @return true if due date exists
     */
    public boolean hasDueDate() {
        return dueAt != null;
    }

    /**
     * Returns whether this request has finished successfully.
     *
     * @return true if completed successfully
     */
    public boolean isCompletedSuccessfully() {
        return completed
                && status == ApprovalRequestStatus.COMPLETED;
    }

    /**
     * Returns whether the workflow is in a terminal state.
     *
     * @return true if terminal
     */
    public boolean isTerminalState() {

        return status == ApprovalRequestStatus.APPROVED
                || status == ApprovalRequestStatus.REJECTED
                || status == ApprovalRequestStatus.CANCELLED
                || status == ApprovalRequestStatus.COMPLETED
                || status == ApprovalRequestStatus.EXPIRED
                || status == ApprovalRequestStatus.WITHDRAWN;
    }

    /**
     * Returns whether this request can be cancelled.
     *
     * @return true if cancellation is allowed
     */
    public boolean canBeCancelled() {
        return !cancelled && !isTerminalState();
    }

    /**
     * Returns whether this request can be resumed.
     *
     * @return true if suspended
     */
    public boolean canBeResumed() {
        return suspended;
    }

    /**
     * Returns whether this request has reached
     * its final execution stage.
     *
     * @return true if final stage reached
     */
    public boolean isFinalStage() {

        return totalStages != null
                && currentStageOrder != null
                && currentStageOrder >= totalStages;
    }

    /**
     * Returns the number of remaining stages.
     *
     * @return remaining stage count
     */
    public int getRemainingStages() {

        if (totalStages == null || completedStages == null) {
            return 0;
        }

        return Math.max(0, totalStages - completedStages);
    }

    /**
     * Returns whether workflow execution
     * has started.
     *
     * @return true if started
     */
    public boolean hasStarted() {
        return submittedAt != null;
    }

    /**
     * Returns whether workflow execution
     * has finished.
     *
     * @return true if finished
     */
    public boolean hasFinished() {
        return completedAt != null;
    }

    @Override
    public String toString() {

        return "ApprovalRequest{" +
                "id=" + getId() +
                ", requestNumber='" + requestNumber + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", moduleName='" + moduleName + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId='" + entityId + '\'' +
                ", currentStage=" + currentStageOrder +
                ", completedStages=" + completedStages +
                ", totalStages=" + totalStages +
                ", progress=" + getProgressPercentage() +
                "%, suspended=" + suspended +
                ", overdue=" + overdue +
                ", escalated=" + escalated +
                ", delegated=" + delegated +
                ", completed=" + completed +
                ", cancelled=" + cancelled +
                ", active=" + isActive() +
                '}';
    }

}