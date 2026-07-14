package com.zallpy.openapproval.approval.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Request used to submit a new approval workflow.
 *
 * <p>
 * This DTO represents the public API contract for creating approval requests.
 * It is intentionally generic so OpenApproval can be embedded into any
 * business application without depending on domain-specific entities.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitApprovalRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Optional correlation identifier for distributed tracing.
     * If omitted, the engine will generate one.
     */
    private String correlationId;

    /**
     * Unique business reference.
     */
    @NotBlank(message = "Request reference is required.")
    private String requestReference;

    /**
     * Approval policy code.
     */
    @NotBlank(message = "Policy code is required.")
    private String policyCode;

    /**
     * Business resource type.
     *
     * Examples:
     * PURCHASE_ORDER
     * INVOICE
     * LEAVE_REQUEST
     * CUSTOMER
     */
    @NotBlank(message = "Resource type is required.")
    private String resourceType;

    /**
     * Identifier of the resource requiring approval.
     */
    @NotNull(message = "Resource ID is required.")
    private UUID resourceId;

    /**
     * Approval title displayed to approvers.
     */
    @NotBlank(message = "Title is required.")
    private String title;

    /**
     * Detailed approval description.
     */
    private String description;

    /**
     * User submitting the approval request.
     */
    @NotNull(message = "Submitted by is required.")
    private UUID submittedBy;

    /**
     * Optional business key for external systems.
     */
    private String businessKey;

    /**
     * Optional approval priority.
     */
    private String priority;

    /**
     * Optional due date.
     */
    private LocalDateTime dueDate;

    /**
     * Dynamic business attributes.
     *
     * Example:
     * amount
     * department
     * currency
     * branchCode
     */
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();

}