package com.zallpy.openapproval.approval.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base response for all OpenApproval engine operations.
 *
 * <p>
 * Every response returned by the approval engine extends this class to provide
 * consistent metadata across all engine operations.
 * </p>
 *
 * <p>
 * The correlation ID enables end-to-end tracing across logs, audit records,
 * notifications, and external integrations.
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
public class EngineResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the operation completed successfully.
     */
    @Builder.Default
    private boolean success = true;

    /**
     * Human-readable message describing the outcome.
     */
    private String message;

    /**
     * Unique identifier used to trace the request across the platform.
     */
    private String correlationId;

    /**
     * Time the response was generated.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}