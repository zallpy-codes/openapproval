package com.zallpy.openapproval.common.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

/**
 * Generic JPA Specification implementation.
 *
 * @param <T> entity type
 *
 * @author Zallpy
 * @since 1.0.0
 */
public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(
            Root<T> root,
            jakarta.persistence.criteria.CriteriaQuery<?> query,
            CriteriaBuilder cb) {

        Path<?> path = getPath(root, criteria.getField());

        Object value = criteria.getValue();

        return switch (criteria.getOperation()) {

            case EQUAL ->
                    cb.equal(path, value);

            case NOT_EQUAL ->
                    cb.notEqual(path, value);

            case GREATER_THAN ->
                    cb.greaterThan((Expression<? extends Comparable>) path,
                            (Comparable) value);

            case GREATER_THAN_OR_EQUAL ->
                    cb.greaterThanOrEqualTo((Expression<? extends Comparable>) path,
                            (Comparable) value);

            case LESS_THAN ->
                    cb.lessThan((Expression<? extends Comparable>) path,
                            (Comparable) value);

            case LESS_THAN_OR_EQUAL ->
                    cb.lessThanOrEqualTo((Expression<? extends Comparable>) path,
                            (Comparable) value);

            case LIKE ->
                    cb.like(cb.lower(path.as(String.class)),
                            value.toString().toLowerCase());

            case CONTAINS ->
                    cb.like(cb.lower(path.as(String.class)),
                            "%" + value.toString().toLowerCase() + "%");

            case STARTS_WITH ->
                    cb.like(cb.lower(path.as(String.class)),
                            value.toString().toLowerCase() + "%");

            case ENDS_WITH ->
                    cb.like(cb.lower(path.as(String.class)),
                            "%" + value.toString().toLowerCase());

            case IN ->
                    path.in((Collection<?>) value);

            case NOT_IN ->
                    cb.not(path.in((Collection<?>) value));

            case BETWEEN ->
                    cb.between(
                            (Expression<? extends Comparable>) path,
                            (Comparable) criteria.getValue(),
                            (Comparable) criteria.getSecondValue());

            case IS_NULL ->
                    cb.isNull(path);

            case IS_NOT_NULL ->
                    cb.isNotNull(path);
        };
    }

    /**
     * Resolves nested entity properties.
     *
     * Example:
     * department.name
     *
     * @param root entity root
     * @param property property path
     * @return resolved path
     */
    private Path<?> getPath(
            Root<T> root,
            String property) {

        if (!property.contains(".")) {
            return root.get(property);
        }

        String[] parts = property.split("\\.");

        From<?, ?> from = root;

        for (int i = 0; i < parts.length - 1; i++) {
            from = from.join(parts[i]);
        }

        return from.get(parts[parts.length - 1]);
    }

}