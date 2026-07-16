package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;
import com.zallpy.openapproval.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable audit history for approval operations.
 *
 * <p>
 * Every significant business action performed by the
 * approval engine produces one audit record.
 * </p>
 *
 * <p>
 * Audit records are append-only and should never be
 * modified after creation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "oa_approval_audit", indexes = {

        @Index(name = "idx_audit_workflow", columnList = "approval_workflow_id"),

        @Index(name = "idx_audit_request", columnList = "approval_request_id"),

        @Index(name = "idx_audit_step", columnList = "approval_step_id"),

        @Index(name = "idx_audit_action", columnList = "action"),

        @Index(name = "idx_audit_occurred_at", columnList = "occurred_at"),

        @Index(name = "idx_audit_actor", columnList = "performed_by"),

        @Index(name = "idx_audit_actor_type", columnList = "actor_type")
})
public class ApprovalAudit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_workflow_id")
    private ApprovalWorkflow approvalWorkflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_request_id")
    private ApprovalRequest approvalRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_step_id")
    private ApprovalStep approvalStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_policy_id")
    private ApprovalPolicy approvalPolicy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private ApprovalAuditAction action;

    @Column(name = "event_type", nullable = false, length = 150)
    private String eventType;

    @Column(name = "performed_by")
    private UUID performedBy;

    @Column(name = "performed_by_name", length = 200)
    private String performedByName;

    @Column(name = "affected_user")
    private UUID affectedUser;

    @Column(name = "affected_user_name", length = 200)
    private String affectedUserName;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(length = 4000)
    private String comment;

    @Lob
    @Column(name = "metadata")
    private String metadata;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @Column(name = "trace_id", length = 100)
    private String traceId;

    @Column(length = 100)
    private String source;

    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    @Column(length = 300)
    private String device;

    @Column(name = "system_generated", nullable = false)
    private boolean systemGenerated;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false, length = 50)
    private ApprovalAuditActorType actorType = ApprovalAuditActorType.USER;

    /**
     * Returns the actor type that performed
     * this audit action.
     *
     * @return actor type
     */
    public ApprovalAuditActorType getActorType() {
        return actorType;
    }

    /**
     * Sets the actor type that performed
     * this audit action.
     *
     * @param actorType actor type
     */
    public void setActorType(
            final ApprovalAuditActorType actorType) {
        this.actorType = actorType;
    }

    /**
     * Creates a new immutable audit record.
     */
    public ApprovalAudit() {
        this.occurredAt = LocalDateTime.now();
        this.actorType = ApprovalAuditActorType.SYSTEM;
        this.systemGenerated = true;
    }

    /**
     * Sets the occurrence timestamp.
     *
     * @param occurredAt occurrence timestamp
     */
    public void setOccurredAt(
            final LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    /*
     * ==========================================================
     * Relationship Setters
     * ==========================================================
     */

    public void setApprovalWorkflow(
            final ApprovalWorkflow approvalWorkflow) {
        this.approvalWorkflow = approvalWorkflow;
    }

    public void setApprovalRequest(
            final ApprovalRequest approvalRequest) {
        this.approvalRequest = approvalRequest;
    }

    public void setApprovalStep(
            final ApprovalStep approvalStep) {
        this.approvalStep = approvalStep;
    }

    public void setApprovalPolicy(
            final ApprovalPolicy approvalPolicy) {
        this.approvalPolicy = approvalPolicy;
    }

    /*
     * ==========================================================
     * Business Setters
     * ==========================================================
     */

    public void setAction(final ApprovalAuditAction action) {
        this.action = action;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    public void setPerformedBy(final UUID performedBy) {
        this.performedBy = performedBy;
    }

    public void setPerformedByName(final String performedByName) {
        this.performedByName = performedByName;
    }

    public void setAffectedUser(final UUID affectedUser) {
        this.affectedUser = affectedUser;
    }

    public void setAffectedUserName(final String affectedUserName) {
        this.affectedUserName = affectedUserName;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setDevice(final String device) {
        this.device = device;
    }

    public void setSystemGenerated(final boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    /*
     * ==========================================================
     * Getters
     * ==========================================================
     */

    public ApprovalWorkflow getApprovalWorkflow() {
        return approvalWorkflow;
    }

    public ApprovalRequest getApprovalRequest() {
        return approvalRequest;
    }

    public ApprovalStep getApprovalStep() {
        return approvalStep;
    }

    public ApprovalPolicy getApprovalPolicy() {
        return approvalPolicy;
    }

    public ApprovalAuditAction getAction() {
        return action;
    }

    public String getEventType() {
        return eventType;
    }

    public UUID getPerformedBy() {
        return performedBy;
    }

    public String getPerformedByName() {
        return performedByName;
    }

    public UUID getAffectedUser() {
        return affectedUser;
    }

    public String getAffectedUserName() {
        return affectedUserName;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public String getComment() {
        return comment;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSource() {
        return source;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDevice() {
        return device;
    }

    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    /*
     * ==========================================================
     * Business Helpers
     * ==========================================================
     */

    /**
     * Determines whether this audit record
     * was generated by a user.
     *
     * @return {@code true} if user generated
     */
    public boolean isUserAction() {
        return ApprovalAuditActorType.USER.equals(actorType);
    }

    /**
     * Determines whether this audit record
     * was generated by the workflow engine.
     *
     * @return {@code true} if generated by the workflow engine
     */
    public boolean isWorkflowEngineAction() {
        return ApprovalAuditActorType.WORKFLOW_ENGINE.equals(actorType);
    }

    /**
     * Determines whether this audit record
     * was generated by the scheduler.
     *
     * @return {@code true} if generated by the scheduler
     */
    public boolean isSchedulerAction() {
        return ApprovalAuditActorType.SCHEDULER.equals(actorType);
    }

    /**
     * Determines whether this audit record
     * was generated by the system.
     *
     * @return {@code true} if system generated
     */
    public boolean isSystemAction() {
        return ApprovalAuditActorType.SYSTEM.equals(actorType);
    }

    /**
     * Determines whether this audit belongs
     * to the supplied workflow.
     *
     * @param workflow workflow
     * @return {@code true} if it belongs
     */
    public boolean belongsTo(
            final ApprovalWorkflow workflow) {

        return workflow != null
                && workflow.equals(this.approvalWorkflow);
    }

    /**
     * Determines whether this audit belongs
     * to the supplied request.
     *
     * @param request approval request
     * @return {@code true} if it belongs
     */
    public boolean belongsTo(
            final ApprovalRequest request) {

        return request != null
                && request.equals(this.approvalRequest);
    }

    /**
     * Determines whether this audit belongs
     * to the supplied step.
     *
     * @param step approval step
     * @return {@code true} if it belongs
     */
    public boolean belongsTo(
            final ApprovalStep step) {

        return step != null
                && step.equals(this.approvalStep);
    }

    public UUID getWorkflowId() {

        return approvalWorkflow == null
                ? null
                : approvalWorkflow.getId();
    }

    public UUID getRequestId() {

        return approvalRequest == null
                ? null
                : approvalRequest.getId();
    }

    public UUID getStepId() {

        return approvalStep == null
                ? null
                : approvalStep.getId();
    }

    public UUID getPolicyId() {

        return approvalPolicy == null
                ? null
                : approvalPolicy.getId();
    }

}