package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalActionType;
import com.zallpy.openapproval.approval.enums.ApprovalDecision;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single runtime action performed during
 * an approval execution.
 *
 * <p>
 * Every approval decision, delegation, escalation,
 * reassignment or other operation performed by the
 * approval engine or a user is recorded as an
 * ApprovalExecutionAction.
 *
 * <p>
 * This entity provides a complete audit trail for
 * approval processing.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(
        name = "approval_execution_actions",
        indexes = {
                @Index(
                        name = "idx_execution_action_execution",
                        columnList = "approval_execution_id"
                ),
                @Index(
                        name = "idx_execution_action_stage",
                        columnList = "approval_execution_stage_id"
                ),
                @Index(
                        name = "idx_execution_action_task",
                        columnList = "approval_execution_task_id"
                ),
                @Index(
                        name = "idx_execution_action_reference",
                        columnList = "action_reference"
                ),
                @Index(
                        name = "idx_execution_action_active",
                        columnList = "active"
                )
        }
)
public class ApprovalExecutionAction extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval execution.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_id", nullable = false)
    private ApprovalExecution approvalExecution;

    /**
     * Parent execution stage.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_stage_id", nullable = false)
    private ApprovalExecutionStage approvalExecutionStage;

    /**
     * Parent execution task.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approval_execution_task_id", nullable = false)
    private ApprovalExecutionTask approvalExecutionTask;

    /**
     * Unique action identifier.
     */
    @NotNull
    @Column(name = "action_uuid", nullable = false, unique = true)
    private UUID actionUuid = UUID.randomUUID();

    /**
     * Human-readable action reference.
     */
    @NotBlank
    @Size(max = 100)
    @Column(
            name = "action_reference",
            nullable = false,
            unique = true,
            length = 100
    )
    private String actionReference;

    /**
     * Type of action performed.
     */
    @NotNull
    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 50)
    private ApprovalActionType actionType;

    /**
     * Display name of the actor.
     */
    @NotBlank
    @Size(max = 200)
    @Column(name = "actor_name", nullable = false, length = 200)
    private String actorName;

    /**
     * Identifier of the actor.
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "actor_id", nullable = false, length = 100)
    private String actorId;

        /**
     * Actor email.
     */
    @Size(max = 255)
    @Column(name = "actor_email", length = 255)
    private String actorEmail;

    /**
     * Decision recorded by this action.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "decision", length = 40)
    private ApprovalDecision decision;

    /**
     * Action remarks.
     */
    @Size(max = 4000)
    @Column(name = "remarks", length = 4000)
    private String remarks;

    /**
     * Client IP address.
     */
    @Size(max = 100)
    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    /**
     * User agent.
     */
    @Size(max = 1000)
    @Column(name = "user_agent", length = 1000)
    private String userAgent;

    /**
     * Additional metadata.
     */
    @Size(max = 4000)
    @Column(name = "metadata", length = 4000)
    private String metadata;

    /**
     * Time the action occurred.
     */
    @NotNull
    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime = LocalDateTime.now();

    /**
     * Indicates whether the action was performed
     * automatically by the workflow engine.
     */
    @Column(name = "system_action", nullable = false)
    private boolean systemAction;

    /**
     * Default constructor.
     */
    public ApprovalExecutionAction() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalExecutionAction other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

        // -------------------------------------------------------------------------
    // Business Methods
    // -------------------------------------------------------------------------

    /**
     * Marks this action as a system-generated action.
     */
    public void markAsSystemAction() {
        this.systemAction = true;
    }

    /**
     * Marks this action as a user-generated action.
     */
    public void markAsUserAction() {
        this.systemAction = false;
    }

    /**
     * Records the approval decision.
     *
     * @param decision approval decision
     */
    public void recordDecision(final ApprovalDecision decision) {
        this.decision = decision;
    }

    /**
     * Updates the action remarks.
     *
     * @param remarks action remarks
     */
    public void updateRemarks(final String remarks) {
        this.remarks = remarks;
    }

    /**
     * Updates the metadata.
     *
     * @param metadata metadata
     */
    public void updateMetadata(final String metadata) {
        this.metadata = metadata;
    }

    /**
     * Updates the client IP address.
     *
     * @param ipAddress client IP
     */
    public void updateIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Updates the user agent.
     *
     * @param userAgent user agent
     */
    public void updateUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Returns whether this action has remarks.
     *
     * @return true if remarks exist
     */
    public boolean hasRemarks() {
        return remarks != null && !remarks.isBlank();
    }

    /**
     * Returns whether this action has metadata.
     *
     * @return true if metadata exists
     */
    public boolean hasMetadata() {
        return metadata != null && !metadata.isBlank();
    }

    /**
     * Returns whether this action has a decision.
     *
     * @return true if a decision exists
     */
    public boolean hasDecision() {
        return decision != null;
    }

    /**
     * Returns whether this action originated from the workflow engine.
     *
     * @return true if system generated
     */
    public boolean isSystemAction() {
        return systemAction;
    }

    /**
     * Returns whether this action originated from a user.
     *
     * @return true if user generated
     */
    public boolean isUserAction() {
        return !systemAction;
    }

    /**
     * Returns whether an IP address was captured.
     *
     * @return true if available
     */
    public boolean hasIpAddress() {
        return ipAddress != null && !ipAddress.isBlank();
    }

    /**
     * Returns whether a user agent was captured.
     *
     * @return true if available
     */
    public boolean hasUserAgent() {
        return userAgent != null && !userAgent.isBlank();
    }

    /**
     * Refreshes the action timestamp.
     */
    public void refreshActionTime() {
        this.actionTime = LocalDateTime.now();
    }

        // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalExecution getApprovalExecution() {
        return approvalExecution;
    }

    public void setApprovalExecution(
            ApprovalExecution approvalExecution) {
        this.approvalExecution = approvalExecution;
    }

    public ApprovalExecutionStage getApprovalExecutionStage() {
        return approvalExecutionStage;
    }

    public void setApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage) {
        this.approvalExecutionStage = approvalExecutionStage;
    }

    public ApprovalExecutionTask getApprovalExecutionTask() {
        return approvalExecutionTask;
    }

    public void setApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask) {
        this.approvalExecutionTask = approvalExecutionTask;
    }

    public UUID getActionUuid() {
        return actionUuid;
    }

    public void setActionUuid(UUID actionUuid) {
        this.actionUuid = actionUuid;
    }

    public String getActionReference() {
        return actionReference;
    }

    public void setActionReference(String actionReference) {
        this.actionReference = actionReference;
    }

    public ApprovalActionType getActionType() {
        return actionType;
    }

    public void setActionType(ApprovalActionType actionType) {
        this.actionType = actionType;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    public ApprovalDecision getDecision() {
        return decision;
    }

    public void setDecision(ApprovalDecision decision) {
        this.decision = decision;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

  

    public void setSystemAction(boolean systemAction) {
        this.systemAction = systemAction;
    }

        // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Validates this approval execution action.
     *
     * @throws IllegalStateException if the action configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalExecution == null) {
            throw new IllegalStateException(
                    "Approval execution is required.");
        }

        if (approvalExecutionStage == null) {
            throw new IllegalStateException(
                    "Approval execution stage is required.");
        }

        if (approvalExecutionTask == null) {
            throw new IllegalStateException(
                    "Approval execution task is required.");
        }

        if (actionReference == null
                || actionReference.isBlank()) {

            throw new IllegalStateException(
                    "Action reference is required.");
        }

        if (actionType == null) {
            throw new IllegalStateException(
                    "Action type is required.");
        }

        if (actorId == null
                || actorId.isBlank()) {

            throw new IllegalStateException(
                    "Actor ID is required.");
        }

        if (actorName == null
                || actorName.isBlank()) {

            throw new IllegalStateException(
                    "Actor name is required.");
        }

        if (actionTime == null) {
            throw new IllegalStateException(
                    "Action time is required.");
        }
    }

    // -------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------

    /**
     * Returns whether this action belongs to a task.
     *
     * @return true if a task is associated
     */
    public boolean hasTask() {
        return approvalExecutionTask != null;
    }

    /**
     * Returns whether this action belongs to a stage.
     *
     * @return true if a stage is associated
     */
    public boolean hasStage() {
        return approvalExecutionStage != null;
    }

    /**
     * Returns whether an actor email has been captured.
     *
     * @return true if available
     */
    public boolean hasActorEmail() {
        return actorEmail != null
                && !actorEmail.isBlank();
    }

    /**
     * Returns whether this action contains audit metadata.
     *
     * @return true if metadata exists
     */
    public boolean hasAuditMetadata() {
        return metadata != null
                && !metadata.isBlank();
    }

    /**
     * Returns whether this action was captured automatically.
     *
     * @return true if generated by the workflow engine
     */
    public boolean isGeneratedBySystem() {
        return systemAction;
    }

    @Override
    public String toString() {

        return "ApprovalExecutionAction{" +
                "id=" + getId() +
                ", actionReference='" + actionReference + '\'' +
                ", actionType=" + actionType +
                ", actorId='" + actorId + '\'' +
                ", actorName='" + actorName + '\'' +
                ", actionTime=" + actionTime +
                ", systemAction=" + systemAction +
                ", active=" + isActive() +
                '}';
    }
}