package com.zallpy.openapproval.approval.dto.response;

import com.zallpy.openapproval.approval.enums.ApprovalAction;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents the outcome of an approval engine operation.
 *
 * <p>
 * This response is returned after operations such as:
 * </p>
 *
 * <ul>
 *     <li>Approve</li>
 *     <li>Reject</li>
 *     <li>Delegate</li>
 *     <li>Return for Correction</li>
 *     <li>Recall</li>
 *     <li>Cancel</li>
 * </ul>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApprovalResult extends EngineResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Approval request identifier.
     */
    private UUID requestId;

    /**
     * Workflow identifier.
     */
    private UUID workflowId;

    /**
     * Approval step identifier.
     */
    private UUID stepId;

    /**
     * Action executed by the engine.
     */
    private ApprovalAction action;

    /**
     * Current approval status.
     */
    private ApprovalStatus status;

    /**
     * Indicates whether the workflow has been completed.
     */
    private boolean completed;

    /**
     * Name of the next approval stage.
     */
    private String nextStage;

    /**
     * User that processed the action.
     */
    private UUID processedBy;

    /**
     * Date and time the action was processed.
     */
    private LocalDateTime processedAt;

}