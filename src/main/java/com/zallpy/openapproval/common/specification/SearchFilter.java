package com.zallpy.openapproval.common.specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of search criteria used to build
 * a dynamic JPA Specification.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class SearchFilter {

    private final List<SearchCriteria> criteria = new ArrayList<>();

    /**
     * Adds a search criterion.
     *
     * @param criteria search criterion
     * @return current filter
     */
    public SearchFilter add(SearchCriteria criteria) {

        this.criteria.add(Objects.requireNonNull(criteria));

        return this;
    }

    /**
     * Adds an AND criterion.
     *
     * @param field entity field
     * @param operation search operation
     * @param value value
     * @return current filter
     */
    public SearchFilter and(
            String field,
            SearchOperation operation,
            Object value) {

        return add(new SearchCriteria(
                field,
                operation,
                value,
                SpecificationOperator.AND
        ));
    }

    /**
     * Adds an OR criterion.
     *
     * @param field entity field
     * @param operation search operation
     * @param value value
     * @return current filter
     */
    public SearchFilter or(
            String field,
            SearchOperation operation,
            Object value) {

        return add(new SearchCriteria(
                field,
                operation,
                value,
                SpecificationOperator.OR
        ));
    }

    /**
     * Adds a BETWEEN criterion.
     *
     * @param field entity field
     * @param from lower bound
     * @param to upper bound
     * @return current filter
     */
    public SearchFilter between(
            String field,
            Object from,
            Object to) {

        return add(new SearchCriteria(
                field,
                SearchOperation.BETWEEN,
                from,
                to,
                SpecificationOperator.AND
        ));
    }

    /**
     * Returns all search criteria.
     *
     * @return immutable list
     */
    public List<SearchCriteria> getCriteria() {
        return Collections.unmodifiableList(criteria);
    }

    /**
     * Indicates whether the filter is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return criteria.isEmpty();
    }

}