package com.zallpy.openapproval.approval.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base response returned by all OpenApproval engine operations.
 *
 * <p>
 * This class provides common metadata shared across every response
 * produced by the approval engine.
 * </p>
 *
 * <p>
 * Specialized engine responses should extend this class instead of
 * duplicating common response fields.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder
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
     * Human-readable response message.
     */
    private String message;

    /**
     * Correlation identifier used for request tracing.
     */
    private String correlationId;

    /**
     * Timestamp when the response was generated.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}