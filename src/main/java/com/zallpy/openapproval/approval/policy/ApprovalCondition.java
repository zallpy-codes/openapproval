package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalConditionDataType;
import com.zallpy.openapproval.approval.enums.ApprovalConditionOperator;
import com.zallpy.openapproval.approval.enums.ApprovalLogicalOperator;
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
 * Represents a single rule used to determine whether an
 * {@link ApprovalPolicy} or {@link ApprovalPolicyStage}
 * should be executed.
 *
 * <p>
 * Multiple conditions may be combined using logical operators
 * (AND / OR) to form complex business rules.
 *
 * <p>
 * Examples:
 *
 * <pre>
 * Amount > 100000
 * Currency == USD
 * Branch == LAGOS
 * Department != HR
 * CustomerRisk >= HIGH
 * </pre>
 *
 * <p>
 * Conditions are evaluated by the OpenApproval Rule Engine
 * before workflow execution.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_conditions", indexes = {
        @Index(name = "idx_condition_policy", columnList = "policy_id"),
        @Index(name = "idx_condition_stage", columnList = "stage_id"),
        @Index(name = "idx_condition_order", columnList = "execution_order"),
        @Index(name = "idx_condition_field", columnList = "field_name"),
        @Index(name = "idx_condition_active", columnList = "active")
})
public class ApprovalCondition extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval policy.
     *
     * A condition may belong directly to a policy.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private ApprovalPolicy approvalPolicy;

    /**
     * Parent approval stage.
     *
     * A condition may alternatively belong to a stage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private ApprovalPolicyStage approvalPolicyStage;

    /**
     * Unique business code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "condition_code", nullable = false, unique = true, length = 80)
    private String conditionCode;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "condition_name", nullable = false, length = 150)
    private String conditionName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Request field to evaluate.
     *
     * Examples:
     * amount
     * department
     * currency
     * customerType
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "field_name", nullable = false, length = 150)
    private String fieldName;

    /**
     * Comparison operator.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false, length = 40)
    private ApprovalConditionOperator operator;

    /**
     * Value used during comparison.
     */
    @NotBlank
    @Size(max = 1000)
    @Column(name = "comparison_value", nullable = false, length = 1000)
    private String comparisonValue;

    /**
     * Data type of the comparison value.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 40)
    private ApprovalConditionDataType dataType;

    /**
     * Logical operator joining this condition
     * with the next condition.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "logical_operator", nullable = false, length = 20)
    private ApprovalLogicalOperator logicalOperator = ApprovalLogicalOperator.AND;

    /**
     * Optional expression.
     *
     * Used for advanced rule evaluation
     * (SpEL, MVEL, etc.).
     */
    @Size(max = 4000)
    @Column(name = "expression", length = 4000)
    private String expression;

    /**
     * Evaluation order.
     */
    @NotNull
    @Min(1)
    @Column(name = "execution_order", nullable = false)
    private Integer executionOrder = 1;

    /**
     * Rule priority.
     */
    @Min(1)
    @Max(1000)
    @Column(name = "priority")
    private Integer priority = 100;

    /**
     * Indicates whether this condition
     * must evaluate successfully.
     */
    @Column(name = "mandatory", nullable = false)
    private boolean mandatory = true;

    /**
     * Continue evaluating subsequent
     * conditions after this one.
     */
    @Column(name = "continue_on_match", nullable = false)
    private boolean continueOnMatch = true;

    /**
     * Stop evaluation immediately if
     * this condition fails.
     */
    @Column(name = "stop_on_failure", nullable = false)
    private boolean stopOnFailure = false;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalCondition other)) {
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
    public ApprovalCondition() {
    }

    /**
     * Returns whether this condition belongs to a policy.
     *
     * @return true if attached directly to an approval policy
     */
    public boolean isPolicyCondition() {
        return approvalPolicy != null;
    }

    /**
     * Returns whether this condition belongs to a stage.
     *
     * @return true if attached to an approval stage
     */
    public boolean isStageCondition() {
        return approvalPolicyStage != null;
    }

    /**
     * Determines whether an expression-based rule is configured.
     *
     * @return true if an expression exists
     */
    public boolean hasExpression() {
        return expression != null && !expression.isBlank();
    }

    /**
     * Determines whether evaluation should stop immediately
     * after this condition fails.
     *
     * @return true if fail-fast evaluation is enabled
     */
    public boolean shouldStopOnFailure() {
        return stopOnFailure;
    }

    /**
     * Determines whether evaluation should continue after
     * this condition succeeds.
     *
     * @return true if evaluation should continue
     */
    public boolean shouldContinueOnMatch() {
        return continueOnMatch;
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

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public ApprovalConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(ApprovalConditionOperator operator) {
        this.operator = operator;
    }

    public String getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(String comparisonValue) {
        this.comparisonValue = comparisonValue;
    }

    public ApprovalConditionDataType getDataType() {
        return dataType;
    }

    public void setDataType(ApprovalConditionDataType dataType) {
        this.dataType = dataType;
    }

    public ApprovalLogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(ApprovalLogicalOperator logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isContinueOnMatch() {
        return continueOnMatch;
    }

    public void setContinueOnMatch(boolean continueOnMatch) {
        this.continueOnMatch = continueOnMatch;
    }

    public boolean isStopOnFailure() {
        return stopOnFailure;
    }

    public void setStopOnFailure(boolean stopOnFailure) {
        this.stopOnFailure = stopOnFailure;
    }

    /**
     * Validates this approval condition.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null && approvalPolicyStage == null) {
            throw new IllegalStateException(
                    "An approval condition must belong to either an approval policy or an approval stage.");
        }

        if (approvalPolicy != null && approvalPolicyStage != null) {
            throw new IllegalStateException(
                    "An approval condition cannot belong to both an approval policy and an approval stage.");
        }

        if (conditionCode == null || conditionCode.isBlank()) {
            throw new IllegalStateException(
                    "Condition code is required.");
        }

        if (conditionName == null || conditionName.isBlank()) {
            throw new IllegalStateException(
                    "Condition name is required.");
        }

        if (fieldName == null || fieldName.isBlank()) {
            throw new IllegalStateException(
                    "Field name is required.");
        }

        if (operator == null) {
            throw new IllegalStateException(
                    "Condition operator is required.");
        }

        if (dataType == null) {
            throw new IllegalStateException(
                    "Condition data type is required.");
        }

        if (logicalOperator == null) {
            throw new IllegalStateException(
                    "Logical operator is required.");
        }

        if ((comparisonValue == null || comparisonValue.isBlank())
                && !hasExpression()) {
            throw new IllegalStateException(
                    "Either a comparison value or an expression must be provided.");
        }

        if (executionOrder == null || executionOrder < 1) {
            throw new IllegalStateException(
                    "Execution order must be greater than zero.");
        }

        if (priority == null || priority < 1) {
            throw new IllegalStateException(
                    "Priority must be greater than zero.");
        }
    }

    /**
     * Returns whether this condition uses a literal comparison.
     *
     * @return {@code true} if a comparison value is configured
     */
    public boolean hasComparisonValue() {
        return comparisonValue != null && !comparisonValue.isBlank();
    }

    /**
     * Returns whether this condition should be evaluated.
     *
     * @return {@code true} if the condition is active
     */
    public boolean isExecutable() {
        return isEnabled();
    }

    /**
     * Determines whether this condition is configured as
     * an expression-based rule.
     *
     * @return {@code true} if an expression is present
     */
    public boolean isExpressionBased() {
        return hasExpression();
    }

    /**
     * Determines whether this condition is configured as
     * a field comparison rule.
     *
     * @return {@code true} if field comparison is configured
     */
    public boolean isFieldComparison() {
        return !isExpressionBased();
    }

    @Override
    public String toString() {
        return "ApprovalCondition{" +
                "id=" + getId() +
                ", conditionCode='" + conditionCode + '\'' +
                ", conditionName='" + conditionName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", operator=" + operator +
                ", dataType=" + dataType +
                ", executionOrder=" + executionOrder +
                ", priority=" + priority +
                ", active=" + isActive() +
                '}';
    }

}