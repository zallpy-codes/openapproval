package com.zallpy.openapproval.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Standard API response wrapper.
 *
 * @param <T> response payload type
 * @author Zallpy
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

        boolean success,

        String message,

        T data,

        Instant timestamp

) {
}