package com.zallpy.openapproval.common.response;

import org.springframework.data.domain.Page;

import java.time.Instant;

/**
 * Factory utility for API responses.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ResponseBuilder {

    private ResponseBuilder() {
    }

    /**
     * Creates a success response with data.
     *
     * @param message response message
     * @param data response data
     * @return api response
     * @param <T> payload type
     */
    public static <T> ApiResponse<T> success(
            String message,
            T data) {

        return new ApiResponse<>(
                true,
                message,
                data,
                Instant.now()
        );
    }

    /**
     * Creates a success response without data.
     *
     * @param message response message
     * @return api response
     */
    public static ApiResponse<Void> success(
            String message) {

        return new ApiResponse<>(
                true,
                message,
                null,
                Instant.now()
        );
    }

    /**
     * Creates a paginated response.
     *
     * @param page spring page
     * @return page response
     * @param <T> content type
     */
    public static <T> PageResponse<T> page(Page<T> page) {

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }
}