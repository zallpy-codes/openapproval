package com.zallpy.openapproval.approval.enums;

/**
 * Defines how multiple approval conditions are combined.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalLogicalOperator {

    /**
     * All connected conditions must evaluate to true.
     */
    AND,

    /**
     * At least one connected condition must evaluate to true.
     */
    OR
}