package com.zallpy.openapproval.common.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * Utility methods for working with Spring Data JPA Specifications.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    /**
     * Creates a specification from a search filter.
     *
     * @param filter search filter
     * @param <T> entity type
     * @return specification
     */
    public static <T> Specification<T> from(SearchFilter filter) {

        return new GenericSpecificationBuilder<T>()
                .with(filter)
                .build();
    }

    /**
     * Creates an empty specification.
     *
     * @param <T> entity type
     * @return empty specification
     */
    public static <T> Specification<T> empty() {
        return Specification.where(null);
    }

    /**
     * Combines two specifications with AND.
     *
     * @param left left specification
     * @param right right specification
     * @param <T> entity type
     * @return combined specification
     */
    public static <T> Specification<T> and(
            Specification<T> left,
            Specification<T> right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        return left.and(right);
    }

    /**
     * Combines two specifications with OR.
     *
     * @param left left specification
     * @param right right specification
     * @param <T> entity type
     * @return combined specification
     */
    public static <T> Specification<T> or(
            Specification<T> left,
            Specification<T> right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        return left.or(right);
    }

}