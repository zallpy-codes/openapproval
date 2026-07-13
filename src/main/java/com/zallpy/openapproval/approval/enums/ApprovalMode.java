package com.zallpy.openapproval.approval.enums;

/**
 * Defines the approval strategy for an approval stage.
 *
 * <p>
 * ApprovalMode determines how many approvals are required for a stage
 * to be considered successfully approved.
 *
 * <ul>
 *     <li>ANY - Any one approver can approve the stage.</li>
 *     <li>ALL - Every assigned approver must approve.</li>
 *     <li>MAJORITY - More than half of the assigned approvers must approve.</li>
 *     <li>UNANIMOUS - All approvers must approve with no rejection.</li>
 *     <li>QUORUM - A configured minimum number of approvers must approve.</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalMode {

    /**
     * Any one approver can approve the stage.
     */
    ANY,

    /**
     * Every assigned approver must approve.
     */
    ALL,

    /**
     * More than half of the assigned approvers
     * must approve the stage.
     */
    MAJORITY,

    /**
     * Every assigned approver must approve and
     * no rejection is allowed.
     */
    UNANIMOUS,

    /**
     * A configurable minimum number of approvals
     * is required before the stage is approved.
     */
    QUORUM
}