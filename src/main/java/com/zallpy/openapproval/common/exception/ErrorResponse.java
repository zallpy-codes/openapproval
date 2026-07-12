package com.zallpy.openapproval.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

/**
 * Standard API error response.
 *
 * @param timestamp error timestamp
 * @param status HTTP status code
 * @param error HTTP status text
 * @param message error message
 * @param path request path
 * @param validationErrors validation errors
 */
public record ErrorResponse(

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant timestamp,

        int status,

        String error,

        String message,

        String path,

        List<String> validationErrors

) {
}