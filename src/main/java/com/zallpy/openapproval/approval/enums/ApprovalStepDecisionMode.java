package com.zallpy.openapproval.approval.enums;

/**
 * Determines how approval decisions are evaluated.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalStepDecisionMode {

    /**
     * Every approver must approve.
     */
    ALL,

    /**
     * One approval is sufficient.
     */
    ANY,

    /**
     * Majority approval.
     */
    MAJORITY,

    /**
     * Approval quorum.
     */
    QUORUM,

    /**
     * Configurable threshold.
     */
    THRESHOLD,

    /**
     * Sequential approval.
     */
    SEQUENTIAL
}