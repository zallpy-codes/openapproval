package com.zallpy.openapproval.approval.enums;

/**
 * Represents the execution state of an
 * single approval stage within an approval
 * workflow.
 *
 * <p>
 * Unlike {@link ApprovalStatus}, this enum
 * describes the progress of an individual
 * approval stage rather than the entire
 * approval workflow.
 * </p>
 *
 * <p>
 * Multiple stages may transition through
 * these states independently as the workflow
 * progresses.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalStageStatus {

    /**
     * Stage has not yet started.
     */
    PENDING,

    /**
     * Stage is currently being executed.
     */
    IN_PROGRESS,

    /**
     * Stage completed successfully.
     */
    COMPLETED,

    /**
     * Stage was skipped by workflow rules.
     */
    SKIPPED,

    /**
     * Stage has been delegated to another
     * approver.
     */
    DELEGATED,

    /**
     * Stage has been escalated.
     */
    ESCALATED,

    /**
     * Stage exceeded its configured timeout.
     */
    TIMED_OUT,

    /**
     * Stage has been cancelled.
     */
    CANCELLED

}