package com.zallpy.openapproval.common.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;

/**
 * Standard pagination request DTO.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public record PageRequestDto(

        @Min(value = 0, message = "Page index cannot be negative.")
        Integer page,

        @Min(value = 1, message = "Page size must be at least 1.")
        @Max(value = 100, message = "Page size cannot exceed 100.")
        Integer size,

        String sortBy,

        SortDirection direction,

        List<String> search

) {

    public PageRequestDto {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        sortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        direction = direction == null ? SortDirection.DESC : direction;
    }

}