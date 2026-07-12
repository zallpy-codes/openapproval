package com.zallpy.openapproval.common.response;

import java.util.List;

/**
 * Standard paginated response model.
 *
 * @param <T> content type
 * @author Zallpy
 * @since 1.0.0
 */
public record PageResponse<T>(

        List<T> content,

        int page,

        int size,

        long totalElements,

        int totalPages,

        boolean first,

        boolean last,

        boolean empty

) {
}