package com.zallpy.openapproval.approval.enums;

/**
 * Defines the execution strategy used by an approval policy.
 *
 * <p>
 * The approval strategy determines how approval stages and approvers
 * are processed by the OpenApproval Engine.
 * </p>
 *
 * <ul>
 *     <li><b>SEQUENTIAL</b> - Approvers act one after another in a defined order.</li>
 *     <li><b>PARALLEL</b> - All approvers receive approval tasks simultaneously.</li>
 *     <li><b>FIRST_RESPONSE</b> - The first approval decision completes the stage.</li>
 *     <li><b>ANY_ONE</b> - Any single approver may approve the stage.</li>
 *     <li><b>UNANIMOUS</b> - Every assigned approver must approve.</li>
 *     <li><b>MAJORITY</b> - More than half of the assigned approvers must approve.</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalStrategy {

    /**
     * Approvers process the workflow sequentially.
     */
    SEQUENTIAL,

    /**
     * All approvers receive the approval request simultaneously.
     */
    PARALLEL,

    /**
     * The first approval decision completes the stage.
     */
    FIRST_RESPONSE,

    /**
     * Any one approver may approve the stage.
     */
    ANY_ONE,

    /**
     * Every assigned approver must approve.
     */
    UNANIMOUS,

    /**
     * More than half of the assigned approvers must approve.
     */
    MAJORITY

}