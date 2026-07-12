package com.zallpy.openapproval.common.specification;

import java.util.Objects;

/**
 * Represents a single search criterion.
 *
 * <p>
 * Examples:
 *
 * <pre>
 * new SearchCriteria(
 *      "status",
 *      SearchOperation.EQUAL,
 *      ApprovalStatus.ACTIVE,
 *      SpecificationOperator.AND
 * );
 * </pre>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class SearchCriteria {

    /**
     * Entity property.
     */
    private final String field;

    /**
     * Search operation.
     */
    private final SearchOperation operation;

    /**
     * Search value.
     */
    private final Object value;

    /**
     * Secondary value.
     * Used for BETWEEN operations.
     */
    private final Object secondValue;

    /**
     * Logical operator.
     */
    private final SpecificationOperator operator;

    /**
     * Creates a search criterion.
     *
     * @param field entity field
     * @param operation operation
     * @param value value
     * @param operator logical operator
     */
    public SearchCriteria(
            String field,
            SearchOperation operation,
            Object value,
            SpecificationOperator operator) {

        this(field, operation, value, null, operator);
    }

    /**
     * Creates a search criterion.
     *
     * @param field entity field
     * @param operation operation
     * @param value first value
     * @param secondValue second value
     * @param operator logical operator
     */
    public SearchCriteria(
            String field,
            SearchOperation operation,
            Object value,
            Object secondValue,
            SpecificationOperator operator) {

        this.field = Objects.requireNonNull(field);
        this.operation = Objects.requireNonNull(operation);
        this.value = value;
        this.secondValue = secondValue;
        this.operator = Objects.requireNonNull(operator);
    }

    public String getField() {
        return field;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    public Object getSecondValue() {
        return secondValue;
    }

    public SpecificationOperator getOperator() {
        return operator;
    }

}