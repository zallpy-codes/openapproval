package com.zallpy.openapproval.approval.enums;

/**
 * Defines the supported data types for approval conditions.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalConditionDataType {

    /**
     * Text value.
     */
    STRING,

    /**
     * Integer value.
     */
    INTEGER,

    /**
     * Decimal value.
     */
    DECIMAL,

    /**
     * Boolean value.
     */
    BOOLEAN,

    /**
     * Date only.
     */
    DATE,

    /**
     * Date and time.
     */
    DATETIME,

    /**
     * Time only.
     */
    TIME,

    /**
     * Currency amount.
     */
    MONEY,

    /**
     * Percentage.
     */
    PERCENTAGE,

    /**
     * Enumeration value.
     */
    ENUM,

    /**
     * UUID value.
     */
    UUID,

    /**
     * JSON document.
     */
    JSON
}