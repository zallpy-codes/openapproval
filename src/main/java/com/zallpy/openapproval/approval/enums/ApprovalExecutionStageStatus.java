package com.zallpy.openapproval.approval.enums;

/**
 * Represents the runtime status of an approval execution stage.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalExecutionStageStatus {

    /**
     * Stage has not started.
     */
    PENDING,

    /**
     * Stage is ready for execution.
     */
    READY,

    /**
     * Stage is currently being processed.
     */
    IN_PROGRESS,

    /**
     * Stage execution is temporarily paused.
     */
    ON_HOLD,

    /**
     * Stage approval completed successfully.
     */
    APPROVED,

    /**
     * Stage was rejected.
     */
    REJECTED,

    /**
     * Stage was skipped.
     */
    SKIPPED,

    /**
     * Stage completed successfully.
     */
    COMPLETED,

    /**
     * Stage was cancelled.
     */
    CANCELLED,

    /**
     * Stage expired before completion.
     */
    EXPIRED
}