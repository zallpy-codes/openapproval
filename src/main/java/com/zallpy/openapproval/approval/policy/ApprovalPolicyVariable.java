package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalVariableDataType;
import com.zallpy.openapproval.approval.enums.ApprovalVariableType;
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
 * Represents a configurable variable used by an {@link ApprovalPolicy}.
 *
 * <p>
 * Policy variables provide dynamic values that can be referenced throughout
 * the approval engine using expression syntax.
 *
 * <pre>
 * ${REQUEST_AMOUNT}
 * ${REQUESTER_DEPARTMENT}
 * ${REQUESTER_BRANCH}
 * ${CUSTOMER_TYPE}
 * ${LOAN_AMOUNT}
 * </pre>
 *
 * <p>
 * Variables are used by:
 * <ul>
 * <li>Approval Conditions</li>
 * <li>Dynamic Approver Resolution</li>
 * <li>Email & Notification Templates</li>
 * <li>Reminder Policies</li>
 * <li>Escalation Policies</li>
 * <li>Workflow Expressions</li>
 * </ul>
 *
 * <p>
 * Variables may represent:
 * <ul>
 * <li>System values</li>
 * <li>Request fields</li>
 * <li>Requester attributes</li>
 * <li>Organization metadata</li>
 * <li>Custom business values</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_variables", indexes = {
        @Index(name = "idx_policy_variable_policy", columnList = "policy_id"),
        @Index(name = "idx_policy_variable_code", columnList = "variable_code"),
        @Index(name = "idx_policy_variable_key", columnList = "variable_key"),
        @Index(name = "idx_policy_variable_type", columnList = "variable_type"),
        @Index(name = "idx_policy_variable_active", columnList = "active")
})
public class ApprovalPolicyVariable extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parent approval policy.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_id", nullable = false)
    private ApprovalPolicy approvalPolicy;

    /**
     * Unique business code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "variable_code", nullable = false, unique = true, length = 80)
    private String variableCode;

    /**
     * Display name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "variable_name", nullable = false, length = 150)
    private String variableName;

    /**
     * Variable key used in expressions.
     *
     * Example:
     * REQUEST_AMOUNT
     * REQUESTER_BRANCH
     * CUSTOMER_TYPE
     */
    @NotBlank
    @Size(max = 120)
    @Column(name = "variable_key", nullable = false, unique = true, length = 120)
    private String variableKey;

    /**
     * Variable source.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "variable_type", nullable = false, length = 30)
    private ApprovalVariableType variableType;

    /**
     * Variable data type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 30)
    private ApprovalVariableDataType dataType;

    /**
     * Default value used when
     * the variable cannot be resolved.
     */
    @Size(max = 4000)
    @Column(name = "default_value", length = 4000)
    private String defaultValue;

    /**
     * Optional expression used to compute
     * the variable value dynamically.
     */
    @Size(max = 5000)
    @Column(name = "expression", length = 5000)
    private String expression;

    @Column(name = "expression_language", length = 30)
    private String expressionLanguage = "SpEL";

    /**
     * Description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Indicates whether this variable
     * must be resolved before execution.
     */
    @Column(name = "required", nullable = false)
    private boolean required = false;

    /**
     * Indicates whether this is
     * a built-in system variable.
     */
    @Column(name = "system_variable", nullable = false)
    private boolean systemVariable = false;

    /**
     * Indicates whether the resolved value
     * should be encrypted when persisted.
     */
    @Column(name = "encrypted", nullable = false)
    private boolean encrypted = false;

    /**
     * Indicates whether this variable
     * can be used in search operations.
     */
    @Column(name = "searchable", nullable = false)
    private boolean searchable = true;

    /**
     * Display order.
     */
    @Min(1)
    @Max(10000)
    @Column(name = "display_order")
    private Integer displayOrder = 1;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicyVariable other)) {
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
    public ApprovalPolicyVariable() {
    }

    /**
     * Returns whether this is a system variable.
     *
     * @return true if system variable
     */
    public boolean isSystemVariable() {
        return systemVariable;
    }

    /**
     * Returns whether this is a custom variable.
     *
     * @return true if custom variable
     */
    public boolean isCustomVariable() {
        return variableType == ApprovalVariableType.CUSTOM;
    }

    /**
     * Returns whether this variable is required.
     *
     * @return true if required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Returns whether this variable is encrypted.
     *
     * @return true if encrypted
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * Returns whether this variable is searchable.
     *
     * @return true if searchable
     */
    public boolean isSearchable() {
        return searchable;
    }

    /**
     * Returns whether a default value exists.
     *
     * @return true if configured
     */
    public boolean hasDefaultValue() {
        return defaultValue != null && !defaultValue.isBlank();
    }

    /**
     * Returns whether an expression exists.
     *
     * @return true if configured
     */
    public boolean hasExpression() {
        return expression != null && !expression.isBlank();
    }

    /**
     * Returns the variable placeholder.
     *
     * Example:
     * ${REQUEST_AMOUNT}
     *
     * @return placeholder
     */
    public String getPlaceholder() {
        return "${" + variableKey + "}";
    }

    /**
     * Returns whether the variable
     * is expression-based.
     *
     * @return true if expression exists
     */
    public boolean isExpressionBased() {
        return hasExpression();
    }

    /**
     * Returns whether this variable
     * has a display order.
     *
     * @return true if configured
     */
    public boolean hasDisplayOrder() {
        return displayOrder != null;
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

    public String getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableKey() {
        return variableKey;
    }

    public void setVariableKey(String variableKey) {
        this.variableKey = variableKey;
    }

    public ApprovalVariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(ApprovalVariableType variableType) {
        this.variableType = variableType;
    }

    public ApprovalVariableDataType getDataType() {
        return dataType;
    }

    public void setDataType(ApprovalVariableDataType dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setSystemVariable(boolean systemVariable) {
        this.systemVariable = systemVariable;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Validates this variable configuration.
     *
     * @throws IllegalStateException if the configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicy == null) {
            throw new IllegalStateException(
                    "Approval policy is required.");
        }

        if (variableCode == null || variableCode.isBlank()) {
            throw new IllegalStateException(
                    "Variable code is required.");
        }

        if (variableName == null || variableName.isBlank()) {
            throw new IllegalStateException(
                    "Variable name is required.");
        }

        if (variableKey == null || variableKey.isBlank()) {
            throw new IllegalStateException(
                    "Variable key is required.");
        }

        if (variableType == null) {
            throw new IllegalStateException(
                    "Variable type is required.");
        }

        if (dataType == null) {
            throw new IllegalStateException(
                    "Variable data type is required.");
        }

        if (displayOrder != null && displayOrder < 1) {
            throw new IllegalStateException(
                    "Display order must be greater than zero.");
        }

        if (required && !hasDefaultValue() && !hasExpression()) {
            throw new IllegalStateException(
                    "A required variable must define either a default value or an expression.");
        }

        if (systemVariable && variableType == ApprovalVariableType.CUSTOM) {
            throw new IllegalStateException(
                    "A system variable cannot use the CUSTOM variable type.");
        }
    }

    /**
     * Returns whether this variable
     * can participate in runtime evaluation.
     *
     * @return true if executable
     */
    public boolean isExecutable() {
        return isEnabled();
    }

    /**
     * Returns whether this variable
     * supports expression evaluation.
     *
     * @return true if expression-based
     */
    public boolean supportsExpressionEvaluation() {
        return hasExpression();
    }

    /**
     * Returns whether this variable
     * can be resolved using a default value.
     *
     * @return true if a default value exists
     */
    public boolean canResolveUsingDefaultValue() {
        return hasDefaultValue();
    }

    /**
     * Returns whether this variable
     * requires runtime resolution.
     *
     * @return true if no default value is available
     */
    public boolean requiresRuntimeResolution() {
        return !hasDefaultValue();
    }

    @Override
    public String toString() {
        return "ApprovalPolicyVariable{" +
                "id=" + getId() +
                ", variableCode='" + variableCode + '\'' +
                ", variableName='" + variableName + '\'' +
                ", variableKey='" + variableKey + '\'' +
                ", variableType=" + variableType +
                ", dataType=" + dataType +
                ", systemVariable=" + systemVariable +
                ", required=" + required +
                ", active=" + isActive() +
                '}';
    }
}