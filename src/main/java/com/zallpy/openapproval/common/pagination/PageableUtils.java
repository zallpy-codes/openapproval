package com.zallpy.openapproval.common.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility class for creating Spring Pageable instances.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class PageableUtils {

    private PageableUtils() {
    }

    /**
     * Creates a Pageable from a PageRequestDto.
     *
     * @param request pagination request
     * @return pageable instance
     */
    public static Pageable from(PageRequestDto request) {

        Sort.Direction direction = request.direction() == SortDirection.ASC
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(
                request.page(),
                request.size(),
                Sort.by(direction, request.sortBy())
        );
    }

    /**
     * Creates a Pageable with default values.
     *
     * @return default pageable
     */
    public static Pageable defaultPageable() {
        return PageRequest.of(
                0,
                20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}