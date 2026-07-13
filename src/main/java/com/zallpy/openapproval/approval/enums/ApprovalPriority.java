package com.zallpy.openapproval.approval.enums;

/**
 * Defines the processing priority of an approval request.
 *
 * Higher priorities may be processed before lower priorities
 * depending on the approval engine configuration.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalPriority {

    /**
     * Lowest priority.
     */
    LOW,

    /**
     * Normal business priority.
     */
    NORMAL,

    /**
     * High priority.
     */
    HIGH,

    /**
     * Urgent request.
     */
    URGENT,

    /**
     * Critical business operation.
     */
    CRITICAL
}