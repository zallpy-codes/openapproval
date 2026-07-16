package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Specifications for {@link ApprovalAudit}.
 *
 * <p>
 * Centralizes all audit search criteria used by
 * repositories, history services, dashboards and
 * reporting components.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalAuditSpecification {

    /**
     * Prevent instantiation.
     */
    private ApprovalAuditSpecification() {
    }

    /**
     * Filters by workflow.
     *
     * @param workflowId workflow identifier
     * @return specification
     */
    public static Specification<ApprovalAudit> workflowId(
            final UUID workflowId) {

        return (root, query, cb) ->
                workflowId == null
                        ? cb.conjunction()
                        : cb.equal(
                                root.get("approvalWorkflow").get("id"),
                                workflowId);
    }

    /**
     * Filters by approval request.
     *
     * @param requestId request identifier
     * @return specification
     */
    public static Specification<ApprovalAudit> requestId(
            final UUID requestId) {

        return (root, query, cb) ->
                requestId == null
                        ? cb.conjunction()
                        : cb.equal(
                                root.get("approvalRequest").get("id"),
                                requestId);
    }

    /**
     * Filters by approval step.
     *
     * @param stepId approval step identifier
     * @return specification
     */
    public static Specification<ApprovalAudit> stepId(
            final UUID stepId) {

        return (root, query, cb) ->
                stepId == null
                        ? cb.conjunction()
                        : cb.equal(
                                root.get("approvalStep").get("id"),
                                stepId);
    }

    /**
     * Filters by approval policy.
     *
     * @param policyId approval policy identifier
     * @return specification
     */
    public static Specification<ApprovalAudit> policyId(
            final UUID policyId) {

        return (root, query, cb) ->
                policyId == null
                        ? cb.conjunction()
                        : cb.equal(
                                root.get("approvalPolicy").get("id"),
                                policyId);
    }

    /**
     * Filters by action.
     *
     * @param action audit action
     * @return specification
     */
    public static Specification<ApprovalAudit> action(
            final ApprovalAuditAction action) {

        return (root, query, cb) ->
                action == null
                        ? cb.conjunction()
                        : cb.equal(root.get("action"), action);
    }

    /**
     * Filters by actor type.
     *
     * @param actorType actor type
     * @return specification
     */
    public static Specification<ApprovalAudit> actorType(
            final ApprovalAuditActorType actorType) {

        return (root, query, cb) ->
                actorType == null
                        ? cb.conjunction()
                        : cb.equal(root.get("actorType"), actorType);
    }

    /**
     * Filters by user that performed the action.
     *
     * @param performedBy user identifier
     * @return specification
     */
    public static Specification<ApprovalAudit> performedBy(
            final UUID performedBy) {

        return (root, query, cb) ->
                performedBy == null
                        ? cb.conjunction()
                        : cb.equal(root.get("performedBy"), performedBy);
    }

    /**
     * Filters by event type.
     *
     * @param eventType event type
     * @return specification
     */
    public static Specification<ApprovalAudit> eventType(
            final String eventType) {

        return (root, query, cb) -> {

            if (eventType == null || eventType.isBlank()) {
                return cb.conjunction();
            }

            return cb.equal(
                    cb.lower(root.get("eventType")),
                    eventType.toLowerCase());
        };
    }

    /**
     * Filters audit records occurring between the
     * supplied dates.
     *
     * @param from start date
     * @param to end date
     * @return specification
     */
    public static Specification<ApprovalAudit> occurredBetween(
            final LocalDateTime from,
            final LocalDateTime to) {

        return (root, query, cb) -> {

            List<Predicate> predicates =
                    new ArrayList<>();

            if (from != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("occurredAt"),
                                from));
            }

            if (to != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("occurredAt"),
                                to));
            }

            return cb.and(
                    predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Performs a case-insensitive search across
     * audit fields.
     *
     * @param keyword search keyword
     * @return specification
     */
    public static Specification<ApprovalAudit> search(
            final String keyword) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }

            String value =
                    "%" + keyword.toLowerCase() + "%";

            return cb.or(

                    cb.like(
                            cb.lower(root.get("eventType")),
                            value),

                    cb.like(
                            cb.lower(root.get("performedByName")),
                            value),

                    cb.like(
                            cb.lower(root.get("affectedUserName")),
                            value),

                    cb.like(
                            cb.lower(root.get("comment")),
                            value),

                    cb.like(
                            cb.lower(root.get("metadata")),
                            value));
        };
    }

}