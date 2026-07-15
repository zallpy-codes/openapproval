package com.zallpy.openapproval.approval.service.submission;

import com.zallpy.openapproval.approval.dto.request.SubmitApprovalRequest;
import com.zallpy.openapproval.approval.dto.response.ApprovalSubmissionResponse;

/**
 * Service responsible for submitting approval requests into the
 * OpenApproval workflow engine.
 *
 * <p>
 * The submission service coordinates validation, policy resolution,
 * request creation, workflow generation and persistence.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalSubmissionService {

    /**
     * Submits a new approval request.
     *
     * @param request submission request
     * @return submission response
     */
    ApprovalSubmissionResponse submit(
            SubmitApprovalRequest request);

}