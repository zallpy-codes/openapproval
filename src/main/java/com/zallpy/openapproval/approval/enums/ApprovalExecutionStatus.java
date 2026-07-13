package com.zallpy.openapproval.approval.enums;

/**
 * Represents the lifecycle status of an approval execution.
 *
 * <p>
 * Unlike {@link ApprovalExecutionStageStatus}, this enum represents
 * the overall runtime state of an approval execution from start to finish.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalExecutionStatus {

    /**
     * Execution has been created but not started.
     */
    CREATED,

    /**
     * Execution is currently in progress.
     */
    IN_PROGRESS,

    /**
     * Execution has been temporarily suspended.
     */
    SUSPENDED,

    /**
     * Execution completed successfully.
     */
    COMPLETED,

    /**
     * Execution was cancelled.
     */
    CANCELLED,

    /**
     * Execution failed due to an unexpected error.
     */
    FAILED
}