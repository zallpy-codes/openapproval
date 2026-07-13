package com.zallpy.openapproval.approval.enums;

/**
 * Defines the comparison operators supported by the
 * OpenApproval Rule Engine.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalConditionOperator {

    /**
     * Equal to.
     */
    EQUALS,

    /**
     * Not equal to.
     */
    NOT_EQUALS,

    /**
     * Greater than.
     */
    GREATER_THAN,

    /**
     * Greater than or equal to.
     */
    GREATER_THAN_OR_EQUAL,

    /**
     * Less than.
     */
    LESS_THAN,

    /**
     * Less than or equal to.
     */
    LESS_THAN_OR_EQUAL,

    /**
     * Value exists within a collection.
     */
    IN,

    /**
     * Value does not exist within a collection.
     */
    NOT_IN,

    /**
     * Between two values.
     */
    BETWEEN,

    /**
     * Contains a value.
     */
    CONTAINS,

    /**
     * Does not contain a value.
     */
    NOT_CONTAINS,

    /**
     * Starts with.
     */
    STARTS_WITH,

    /**
     * Ends with.
     */
    ENDS_WITH,

    /**
     * String matches a regular expression.
     */
    MATCHES,

    /**
     * Field is null.
     */
    IS_NULL,

    /**
     * Field is not null.
     */
    IS_NOT_NULL,

    /**
     * Field is empty.
     */
    IS_EMPTY,

    /**
     * Field is not empty.
     */
    IS_NOT_EMPTY
}