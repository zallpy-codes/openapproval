package com.zallpy.openapproval.common.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

/**
 * Builds a composite JPA {@link Specification} from a collection of
 * {@link SearchCriteria}.
 *
 * @param <T> entity type
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class GenericSpecificationBuilder<T> {

    private final SearchFilter filter = new SearchFilter();

    /**
     * Adds a search criterion.
     *
     * @param criteria search criterion
     * @return current builder
     */
    public GenericSpecificationBuilder<T> with(SearchCriteria criteria) {
        filter.add(criteria);
        return this;
    }

    /**
     * Adds multiple search criteria.
     *
     * @param criteriaList search criteria
     * @return current builder
     */
    public GenericSpecificationBuilder<T> with(List<SearchCriteria> criteriaList) {
        Objects.requireNonNull(criteriaList)
                .forEach(filter::add);
        return this;
    }

    /**
     * Adds a complete search filter.
     *
     * @param filter search filter
     * @return current builder
     */
    public GenericSpecificationBuilder<T> with(SearchFilter filter) {

        if (filter != null) {
            filter.getCriteria().forEach(this.filter::add);
        }

        return this;
    }

    /**
     * Builds the composed specification.
     *
     * @return specification or {@code null} if no criteria exist
     */
    public Specification<T> build() {

        if (filter.isEmpty()) {
            return null;
        }

        Specification<T> specification = null;

        for (SearchCriteria criteria : filter.getCriteria()) {

            Specification<T> current =
                    Specification.where(new GenericSpecification<>(criteria));

            if (specification == null) {
                specification = current;
                continue;
            }

            specification = criteria.getOperator() == SpecificationOperator.AND
                    ? specification.and(current)
                    : specification.or(current);
        }

        return specification;
    }

}