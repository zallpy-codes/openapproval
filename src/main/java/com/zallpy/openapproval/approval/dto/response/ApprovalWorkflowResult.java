package com.zallpy.openapproval.approval.dto.response;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Response returned after successfully creating an approval workflow.
 *
 * <p>
 * This DTO contains the initial execution state of a newly created
 * approval workflow, including identifiers, workflow status,
 * stage information, and current approvers.
 * </p>
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
public class ApprovalWorkflowResult extends EngineResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Approval request identifier.
     */
    private UUID requestId;

    /**
     * Approval workflow identifier.
     */
    private UUID workflowId;

    /**
     * Business request reference.
     */
    private String requestReference;

    /**
     * Policy code used to create the workflow.
     */
    private String policyCode;

    /**
     * Current workflow status.
     */
    private ApprovalStatus status;

    /**
     * Current approval stage.
     */
    private String currentStage;

    /**
     * Total number of workflow stages.
     */
    private Integer totalStages;

    /**
     * Current stage sequence number.
     */
    private Integer currentStageOrder;

    /**
     * Users currently expected to approve.
     */
    private List<UUID> currentApprovers;

    /**
     * User that submitted the request.
     */
    private UUID submittedBy;

    /**
     * Submission timestamp.
     */
    private LocalDateTime submittedAt;

    /**
     * Indicates whether the workflow has already completed.
     */
    private boolean completed;

}