package com.zallpy.openapproval.approval.service.workflow;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalPolicy;
import com.zallpy.openapproval.approval.entity.ApprovalRequest;
import com.zallpy.openapproval.approval.entity.ApprovalStage;
import com.zallpy.openapproval.approval.entity.ApprovalStep;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import com.zallpy.openapproval.approval.service.validation.ApprovalValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalWorkflowBuilder}.
 *
 * <p>
 * Responsible for constructing executable approval workflows from
 * approval policies. The builder converts the policy definition into
 * runtime approval steps while preserving the policy configuration.
 * </p>
 *
 * <p>
 * This class is responsible only for building the workflow graph.
 * Workflow execution is handled by the workflow engine.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional
public class ApprovalWorkflowBuilderImpl
        implements ApprovalWorkflowBuilder {

    /**
     * Validation service.
     */
    private final ApprovalValidationService validationService;

    /**
     * Creates a new workflow builder.
     *
     * @param validationService validation service
     */
    public ApprovalWorkflowBuilderImpl(
            final ApprovalValidationService validationService) {

        this.validationService = validationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalWorkflow buildWorkflow(
            final SubmitApprovalRequest submissionRequest,
            final ApprovalPolicy approvalPolicy,
            final ApprovalRequest approvalRequest) {

        /*
         * Validate incoming objects.
         */
        validationService.validateSubmission(submissionRequest);
        validationService.validatePolicy(approvalPolicy);
        validationService.validateRequest(approvalRequest);

        /*
         * Create workflow aggregate.
         */
        ApprovalWorkflow workflow = new ApprovalWorkflow();

        /*
         * Link workflow to request.
         *
         * The aggregate keeps both sides synchronized.
         */
        workflow.setApprovalRequest(approvalRequest);

        /*
         * Create executable approval steps from the configured
         * approval policy stages.
         */
        for (ApprovalStage stage : approvalPolicy.getStages()) {

            /*
             * Ignore inactive stages.
             */
            if (!stage.isExecutable()) {
                continue;
            }

            ApprovalStep step = new ApprovalStep();

            /*
             * Link runtime step to its source stage.
             */
            step.setApprovalStage(stage);

            /*
             * Copy execution order.
             */
            step.setStageOrder(stage.getStageOrder());

            /*
             * Copy timeout configuration.
             */
            if (stage.isTimeoutEnabled()
                    && stage.getTimeoutMinutes() != null) {

                step.setDueAt(
                        workflow.getStartedAt() == null
                                ? null
                                : workflow.getStartedAt()
                                        .plusMinutes(stage.getTimeoutMinutes()));
            }

            /*
             * Attach runtime step to the workflow.
             *
             * The aggregate automatically maintains the
             * bidirectional relationship and statistics.
             */
            workflow.addApprovalStep(step);
        }

        /*
         * Nothing to execute if the policy produced
         * no executable approval steps.
         */
        if (workflow.getApprovalSteps().isEmpty()) {
            return workflow;
        }

        /*
         * Activate the first approval step.
         */
        ApprovalStep firstStep = workflow.getApprovalSteps()
                .stream()
                .min((left, right) -> Integer.compare(
                        left.getStageOrder(),
                        right.getStageOrder()))
                .orElse(null);

        if (firstStep != null) {
            firstStep.activate();
        }

        /*
         * Start the workflow.
         *
         * This method is responsible for setting the
         * workflow status, timestamps and runtime state.
         */
        workflow.start();

        return workflow;
    }

}
