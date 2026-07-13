package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalApproverType;
import com.zallpy.openapproval.approval.enums.ApprovalAssignmentSource;
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
 * Defines an approver configuration for an {@link ApprovalPolicyStage}.
 *
 * <p>
 * Each approval stage may contain one or more approver definitions.
 * Rather than storing a single user, this entity supports multiple
 * assignment strategies including:
 *
 * <ul>
 * <li>Specific User</li>
 * <li>Role</li>
 * <li>Department</li>
 * <li>Position</li>
 * <li>Business Unit</li>
 * <li>Manager Hierarchy</li>
 * <li>Dynamic Resolver</li>
 * </ul>
 *
 * The approval engine resolves the actual approvers during workflow
 * execution based on these rules.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_approvers", indexes = {
        @Index(name = "idx_policy_approver_stage", columnList = "stage_id"),
        @Index(name = "idx_policy_approver_order", columnList = "execution_order"),
        @Index(name = "idx_policy_approver_type", columnList = "approver_type"),
        @Index(name = "idx_policy_approver_active", columnList = "active")
})
public class ApprovalPolicyApprover extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval stage.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stage_id", nullable = false)
    private ApprovalPolicyStage approvalPolicyStage;

    /**
     * Execution order inside the stage.
     */
    @NotNull
    @Min(1)
    @Column(name = "execution_order", nullable = false)
    private Integer executionOrder;

    /**
     * Approver type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "approver_type", nullable = false, length = 40)
    private ApprovalApproverType approverType;

    /**
     * Assignment source.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_source", nullable = false, length = 40)
    private ApprovalAssignmentSource assignmentSource;

    /**
     * Business identifier of the approver.
     * May represent a User ID, Role ID, Department ID, etc.
     */
    @NotBlank
    @Size(max = 120)
    @Column(name = "reference_id", nullable = false, length = 120)
    private String referenceId;

    /**
     * Human-readable reference name.
     */
    @Size(max = 200)
    @Column(name = "reference_name", length = 200)
    private String referenceName;

    /**
     * Dynamic resolver bean or expression.
     */
    @Size(max = 255)
    @Column(name = "resolver_expression", length = 255)
    private String resolverExpression;

    /**
     * Hierarchy level for manager escalation.
     */
    @Min(1)
    @Max(20)
    @Column(name = "hierarchy_level")
    private Integer hierarchyLevel;

    /**
     * Indicates whether this approver is mandatory.
     */
    @Column(name = "mandatory", nullable = false)
    private boolean mandatory = true;

    /**
     * Indicates whether delegation is allowed.
     */
    @Column(name = "allow_delegation", nullable = false)
    private boolean allowDelegation = true;

    /**
     * Default constructor.
     */
    public ApprovalPolicyApprover() {
    }

    public ApprovalPolicyStage getApprovalPolicyStage() {
        return approvalPolicyStage;
    }

    public void setApprovalPolicyStage(ApprovalPolicyStage approvalPolicyStage) {
        this.approvalPolicyStage = approvalPolicyStage;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public ApprovalApproverType getApproverType() {
        return approverType;
    }

    public void setApproverType(ApprovalApproverType approverType) {
        this.approverType = approverType;
    }

    public ApprovalAssignmentSource getAssignmentSource() {
        return assignmentSource;
    }

    public void setAssignmentSource(ApprovalAssignmentSource assignmentSource) {
        this.assignmentSource = assignmentSource;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getResolverExpression() {
        return resolverExpression;
    }

    public void setResolverExpression(String resolverExpression) {
        this.resolverExpression = resolverExpression;
    }

    public Integer getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isAllowDelegation() {
        return allowDelegation;
    }

    public void setAllowDelegation(boolean allowDelegation) {
        this.allowDelegation = allowDelegation;
    }

    /**
     * Validates the approver configuration.
     *
     * @throws IllegalStateException if configuration is invalid
     */
    public void validateConfiguration() {

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (referenceId == null || referenceId.isBlank()) {
            throw new IllegalStateException(
                    "Reference ID is required.");
        }

        if (approverType == null) {
            throw new IllegalStateException(
                    "Approver type is required.");
        }

        if (assignmentSource == null) {
            throw new IllegalStateException(
                    "Assignment source is required.");
        }
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyApprover other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ApprovalPolicyApprover{" +
                "id=" + getId() +
                ", executionOrder=" + executionOrder +
                ", approverType=" + approverType +
                ", assignmentSource=" + assignmentSource +
                ", referenceId='" + referenceId + '\'' +
                ", active=" + isActive() +
                '}';
    }
}