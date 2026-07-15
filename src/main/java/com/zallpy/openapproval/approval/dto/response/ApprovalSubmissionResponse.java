package com.zallpy.openapproval.approval.dto.response;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response returned after successfully submitting an approval request.
 *
 * <p>
 * Contains the identifiers and runtime information required by the
 * calling application to continue tracking the approval workflow.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class ApprovalSubmissionResponse {

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
     * Current approval status.
     */
    private ApprovalStatus status;

    /**
     * Date and time the request was submitted.
     */
    private LocalDateTime submittedAt;

    /**
     * Operation result message.
     */
    private String message;

    /**
     * Returns the approval request identifier.
     *
     * @return request identifier
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Sets the approval request identifier.
     *
     * @param requestId request identifier
     */
    public void setRequestId(final UUID requestId) {
        this.requestId = requestId;
    }

    /**
     * Returns the workflow identifier.
     *
     * @return workflow identifier
     */
    public UUID getWorkflowId() {
        return workflowId;
    }

    /**
     * Sets the workflow identifier.
     *
     * @param workflowId workflow identifier
     */
    public void setWorkflowId(final UUID workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * Returns the request reference.
     *
     * @return request reference
     */
    public String getRequestReference() {
        return requestReference;
    }

    /**
     * Sets the request reference.
     *
     * @param requestReference request reference
     */
    public void setRequestReference(final String requestReference) {
        this.requestReference = requestReference;
    }

    /**
     * Returns the approval status.
     *
     * @return approval status
     */
    public ApprovalStatus getStatus() {
        return status;
    }

    /**
     * Sets the approval status.
     *
     * @param status approval status
     */
    public void setStatus(final ApprovalStatus status) {
        this.status = status;
    }

    /**
     * Returns the submission timestamp.
     *
     * @return submission timestamp
     */
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    /**
     * Sets the submission timestamp.
     *
     * @param submittedAt submission timestamp
     */
    public void setSubmittedAt(final LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    /**
     * Returns the response message.
     *
     * @return response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message response message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

}