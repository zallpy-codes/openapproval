package com.zallpy.openapproval.approval.enums;

/**
 * Represents the business classification of an approval comment.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalCommentType {

    /**
     * General discussion.
     */
    GENERAL,

    /**
     * Approval justification.
     */
    APPROVAL,

    /**
     * Rejection reason.
     */
    REJECTION,

    /**
     * Request returned for correction.
     */
    RETURN,

    /**
     * Delegation comment.
     */
    DELEGATION,

    /**
     * Escalation comment.
     */
    ESCALATION,

    /**
     * System generated comment.
     */
    SYSTEM
}