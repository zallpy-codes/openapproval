package com.zallpy.openapproval.approval.enums;

/**
 * Defines who can view an approval comment.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalCommentVisibility {

    /**
     * Visible to everyone participating
     * in the approval workflow.
     */
    PUBLIC,

    /**
     * Visible only to internal approvers.
     */
    INTERNAL,

    /**
     * Visible only for audit purposes.
     */
    AUDIT_ONLY
}