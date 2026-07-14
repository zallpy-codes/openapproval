package com.zallpy.openapproval.common.specification;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic search request.
 *
 * <p>
 * Holds multiple search criteria which are converted into
 * a JPA Specification.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class SearchRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Search filters.
     */
    private List<SearchCriteria> criteria = new ArrayList<>();

    public SearchRequest() {
    }

    public List<SearchCriteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(
            List<SearchCriteria> criteria) {

        this.criteria = criteria;
    }

    /**
     * Adds a search criterion.
     *
     * @param criteria search criteria
     */
    public void add(SearchCriteria criteria) {
        this.criteria.add(criteria);
    }

    /**
     * Returns whether the request contains filters.
     *
     * @return true if criteria exist
     */
    public boolean hasCriteria() {
        return !criteria.isEmpty();
    }
}