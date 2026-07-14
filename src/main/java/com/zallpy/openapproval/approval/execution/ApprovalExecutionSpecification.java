package com.zallpy.openapproval.approval.execution;

import com.zallpy.openapproval.approval.enums.ApprovalExecutionStatus;
import com.zallpy.openapproval.approval.policy.ApprovalPolicy;
import com.zallpy.openapproval.approval.runtime.ApprovalRequest;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JPA Specifications for {@link ApprovalExecution}.
 *
 * <p>
 * Provides reusable query predicates for ApprovalExecution filtering,
 * searching and dynamic query composition.
 *
 * <p>
 * All specifications are null-safe and may be freely combined.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalExecutionSpecification {

    /**
     * Private constructor.
     */
    private ApprovalExecutionSpecification() {
    }

    // -------------------------------------------------------------------------
    // Generic Search
    // -------------------------------------------------------------------------

    /**
     * Performs a keyword search across searchable execution fields.
     *
     * @param keyword search keyword
     * @return specification
     */
    public static Specification<ApprovalExecution> search(
            final String keyword) {

        return (root, query, builder) -> {

            if (keyword == null || keyword.isBlank()) {
                return builder.conjunction();
            }

            String pattern = "%" + keyword.trim().toLowerCase() + "%";

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.like(
                    builder.lower(root.get("executionReference")),
                    pattern));

            predicates.add(builder.like(
                    builder.lower(root.get("executionName")),
                    pattern));

            predicates.add(builder.like(
                    builder.lower(root.get("description")),
                    pattern));

            predicates.add(builder.like(
                    builder.lower(root.get("completionRemarks")),
                    pattern));

            predicates.add(builder.like(
                    builder.lower(root.get("currentStageCode")),
                    pattern));

            predicates.add(builder.like(
                    builder.lower(root.get("currentStageName")),
                    pattern));

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    // -------------------------------------------------------------------------
    // Identity
    // -------------------------------------------------------------------------

    /**
     * Filters by execution UUID.
     *
     * @param executionUuid execution UUID
     * @return specification
     */
    public static Specification<ApprovalExecution> hasExecutionUuid(
            final UUID executionUuid) {

        return (root, query, builder) -> executionUuid == null
                ? builder.conjunction()
                : builder.equal(root.get("executionUuid"), executionUuid);
    }

    /**
     * Filters by execution reference.
     *
     * @param executionReference execution reference
     * @return specification
     */
    public static Specification<ApprovalExecution> hasExecutionReference(
            final String executionReference) {

        return (root, query, builder) -> executionReference == null || executionReference.isBlank()
                ? builder.conjunction()
                : builder.equal(root.get("executionReference"),
                        executionReference);
    }

    /**
     * Filters by execution reference containing text.
     *
     * @param executionReference reference fragment
     * @return specification
     */
    public static Specification<ApprovalExecution> executionReferenceContains(
            final String executionReference) {

        return (root, query, builder) -> {

            if (executionReference == null || executionReference.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("executionReference")),
                    "%" + executionReference.toLowerCase() + "%");
        };
    }

    /**
     * Filters by execution name containing text.
     *
     * @param executionName execution name
     * @return specification
     */
    public static Specification<ApprovalExecution> executionNameContains(
            final String executionName) {

        return (root, query, builder) -> {

            if (executionName == null || executionName.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("executionName")),
                    "%" + executionName.toLowerCase() + "%");
        };
    }

    /**
     * Filters by description containing text.
     *
     * @param description description fragment
     * @return specification
     */
    public static Specification<ApprovalExecution> descriptionContains(
            final String description) {

        return (root, query, builder) -> {

            if (description == null || description.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("description")),
                    "%" + description.toLowerCase() + "%");
        };
    }

    /**
     * Filters by completion remarks containing text.
     *
     * @param remarks remarks fragment
     * @return specification
     */
    public static Specification<ApprovalExecution> completionRemarksContains(
            final String remarks) {

        return (root, query, builder) -> {

            if (remarks == null || remarks.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("completionRemarks")),
                    "%" + remarks.toLowerCase() + "%");
        };
    }

    // -------------------------------------------------------------------------
    // Relationships
    // -------------------------------------------------------------------------

    /**
     * Filters by approval request.
     *
     * @param approvalRequest approval request
     * @return specification
     */
    public static Specification<ApprovalExecution> hasApprovalRequest(
            final ApprovalRequest approvalRequest) {

        return (root, query, builder) -> approvalRequest == null
                ? builder.conjunction()
                : builder.equal(root.get("approvalRequest"),
                        approvalRequest);
    }

    /**
     * Filters by approval policy.
     *
     * @param approvalPolicy approval policy
     * @return specification
     */
    public static Specification<ApprovalExecution> hasApprovalPolicy(
            final ApprovalPolicy approvalPolicy) {

        return (root, query, builder) -> approvalPolicy == null
                ? builder.conjunction()
                : builder.equal(root.get("approvalPolicy"),
                        approvalPolicy);
    }

    // -------------------------------------------------------------------------
    // Status
    // -------------------------------------------------------------------------

    /**
     * Filters by execution status.
     *
     * @param executionStatus execution status
     * @return specification
     */
    public static Specification<ApprovalExecution> hasExecutionStatus(
            final ApprovalExecutionStatus executionStatus) {

        return (root, query, builder) -> executionStatus == null
                ? builder.conjunction()
                : builder.equal(root.get("executionStatus"),
                        executionStatus);
    }

    /**
     * Filters started executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> isStarted() {

        return (root, query, builder) -> builder.isTrue(root.get("started"));
    }

    /**
     * Filters completed executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> isCompleted() {

        return (root, query, builder) -> builder.isTrue(root.get("completed"));
    }

    /**
     * Filters cancelled executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> isCancelled() {

        return (root, query, builder) -> builder.isTrue(root.get("cancelled"));
    }

    /**
     * Filters suspended executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> isSuspended() {

        return (root, query, builder) -> builder.isTrue(root.get("suspended"));
    }

    /**
     * Filters active executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> isActive() {

        return (root, query, builder) -> builder.isTrue(root.get("active"));
    }

    // -------------------------------------------------------------------------
    // Current Stage
    // -------------------------------------------------------------------------

    /**
     * Filters by current stage order.
     *
     * @param stageOrder stage order
     * @return specification
     */
    public static Specification<ApprovalExecution> hasCurrentStageOrder(
            final Integer stageOrder) {

        return (root, query, builder) -> stageOrder == null
                ? builder.conjunction()
                : builder.equal(root.get("currentStageOrder"),
                        stageOrder);
    }

    /**
     * Filters by current stage code.
     *
     * @param stageCode stage code
     * @return specification
     */
    public static Specification<ApprovalExecution> hasCurrentStageCode(
            final String stageCode) {

        return (root, query, builder) -> stageCode == null || stageCode.isBlank()
                ? builder.conjunction()
                : builder.equal(root.get("currentStageCode"),
                        stageCode);
    }

    /**
     * Filters by current stage code containing text.
     *
     * @param stageCode stage code fragment
     * @return specification
     */
    public static Specification<ApprovalExecution> currentStageCodeContains(
            final String stageCode) {

        return (root, query, builder) -> {

            if (stageCode == null || stageCode.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("currentStageCode")),
                    "%" + stageCode.toLowerCase() + "%");
        };
    }

    /**
     * Filters by current stage name.
     *
     * @param stageName stage name
     * @return specification
     */
    public static Specification<ApprovalExecution> hasCurrentStageName(
            final String stageName) {

        return (root, query, builder) -> stageName == null || stageName.isBlank()
                ? builder.conjunction()
                : builder.equal(root.get("currentStageName"),
                        stageName);
    }

    /**
     * Filters by current stage name containing text.
     *
     * @param stageName stage name fragment
     * @return specification
     */
    public static Specification<ApprovalExecution> currentStageNameContains(
            final String stageName) {

        return (root, query, builder) -> {

            if (stageName == null || stageName.isBlank()) {
                return builder.conjunction();
            }

            return builder.like(
                    builder.lower(root.get("currentStageName")),
                    "%" + stageName.toLowerCase() + "%");
        };
    }

    // -------------------------------------------------------------------------
    // Stage Statistics
    // -------------------------------------------------------------------------

    /**
     * Filters by total stages.
     *
     * @param totalStages total number of stages
     * @return specification
     */
    public static Specification<ApprovalExecution> hasTotalStages(
            final Integer totalStages) {

        return (root, query, builder) -> totalStages == null
                ? builder.conjunction()
                : builder.equal(root.get("totalStages"), totalStages);
    }

    /**
     * Filters by completed stages.
     *
     * @param completedStages completed stages
     * @return specification
     */
    public static Specification<ApprovalExecution> hasCompletedStages(
            final Integer completedStages) {

        return (root, query, builder) -> completedStages == null
                ? builder.conjunction()
                : builder.equal(root.get("completedStages"),
                        completedStages);
    }

    /**
     * Filters by pending stages.
     *
     * @param pendingStages pending stages
     * @return specification
     */
    public static Specification<ApprovalExecution> hasPendingStages(
            final Integer pendingStages) {

        return (root, query, builder) -> pendingStages == null
                ? builder.conjunction()
                : builder.equal(root.get("pendingStages"),
                        pendingStages);
    }

    /**
     * Filters by rejected stages.
     *
     * @param rejectedStages rejected stages
     * @return specification
     */
    public static Specification<ApprovalExecution> hasRejectedStages(
            final Integer rejectedStages) {

        return (root, query, builder) -> rejectedStages == null
                ? builder.conjunction()
                : builder.equal(root.get("rejectedStages"),
                        rejectedStages);
    }

    /**
     * Filters by skipped stages.
     *
     * @param skippedStages skipped stages
     * @return specification
     */
    public static Specification<ApprovalExecution> hasSkippedStages(
            final Integer skippedStages) {

        return (root, query, builder) -> skippedStages == null
                ? builder.conjunction()
                : builder.equal(root.get("skippedStages"),
                        skippedStages);
    }

    // -------------------------------------------------------------------------
    // Started Date
    // -------------------------------------------------------------------------

    /**
     * Filters executions started after the specified date.
     *
     * @param startedAt start date
     * @return specification
     */
    public static Specification<ApprovalExecution> startedAfter(
            final LocalDateTime startedAt) {

        return (root, query, builder) -> startedAt == null
                ? builder.conjunction()
                : builder.greaterThan(root.get("startedAt"), startedAt);
    }

    /**
     * Filters executions started before the specified date.
     *
     * @param startedAt start date
     * @return specification
     */
    public static Specification<ApprovalExecution> startedBefore(
            final LocalDateTime startedAt) {

        return (root, query, builder) -> startedAt == null
                ? builder.conjunction()
                : builder.lessThan(root.get("startedAt"), startedAt);
    }

    /**
     * Filters executions started within a period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return specification
     */
    public static Specification<ApprovalExecution> startedBetween(
            final LocalDateTime startDate,
            final LocalDateTime endDate) {

        return (root, query, builder) -> {

            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            return builder.between(root.get("startedAt"),
                    startDate,
                    endDate);
        };
    }

    // -------------------------------------------------------------------------
    // Completed Date
    // -------------------------------------------------------------------------

    /**
     * Filters executions completed after the specified date.
     *
     * @param completedAt completion date
     * @return specification
     */
    public static Specification<ApprovalExecution> completedAfter(
            final LocalDateTime completedAt) {

        return (root, query, builder) -> completedAt == null
                ? builder.conjunction()
                : builder.greaterThan(root.get("completedAt"),
                        completedAt);
    }

    /**
     * Filters executions completed before the specified date.
     *
     * @param completedAt completion date
     * @return specification
     */
    public static Specification<ApprovalExecution> completedBefore(
            final LocalDateTime completedAt) {

        return (root, query, builder) -> completedAt == null
                ? builder.conjunction()
                : builder.lessThan(root.get("completedAt"),
                        completedAt);
    }

    /**
     * Filters executions completed within a period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return specification
     */
    public static Specification<ApprovalExecution> completedBetween(
            final LocalDateTime startDate,
            final LocalDateTime endDate) {

        return (root, query, builder) -> {

            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            return builder.between(root.get("completedAt"),
                    startDate,
                    endDate);
        };
    }

    // -------------------------------------------------------------------------
    // Due Date
    // -------------------------------------------------------------------------

    /**
     * Filters executions due after the specified date.
     *
     * @param dueAt due date
     * @return specification
     */
    public static Specification<ApprovalExecution> dueAfter(
            final LocalDateTime dueAt) {

        return (root, query, builder) -> dueAt == null
                ? builder.conjunction()
                : builder.greaterThan(root.get("dueAt"), dueAt);
    }

    /**
     * Filters executions due before the specified date.
     *
     * @param dueAt due date
     * @return specification
     */
    public static Specification<ApprovalExecution> dueBefore(
            final LocalDateTime dueAt) {

        return (root, query, builder) -> dueAt == null
                ? builder.conjunction()
                : builder.lessThan(root.get("dueAt"), dueAt);
    }

    /**
     * Filters executions due within a period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return specification
     */
    public static Specification<ApprovalExecution> dueBetween(
            final LocalDateTime startDate,
            final LocalDateTime endDate) {

        return (root, query, builder) -> {

            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            return builder.between(root.get("dueAt"),
                    startDate,
                    endDate);
        };
    }

    // -------------------------------------------------------------------------
    // Audit Dates
    // -------------------------------------------------------------------------

    /**
     * Filters executions created after the specified date.
     *
     * @param createdDate created date
     * @return specification
     */
    public static Specification<ApprovalExecution> createdAfter(
            final LocalDateTime createdDate) {

        return (root, query, builder) -> createdDate == null
                ? builder.conjunction()
                : builder.greaterThan(root.get("createdDate"),
                        createdDate);
    }

    /**
     * Filters executions created before the specified date.
     *
     * @param createdDate created date
     * @return specification
     */
    public static Specification<ApprovalExecution> createdBefore(
            final LocalDateTime createdDate) {

        return (root, query, builder) -> createdDate == null
                ? builder.conjunction()
                : builder.lessThan(root.get("createdDate"),
                        createdDate);
    }

    /**
     * Filters executions created within a period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return specification
     */
    public static Specification<ApprovalExecution> createdBetween(
            final LocalDateTime startDate,
            final LocalDateTime endDate) {

        return (root, query, builder) -> {

            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            return builder.between(root.get("createdDate"),
                    startDate,
                    endDate);
        };
    }

    /**
     * Filters executions modified after the specified date.
     *
     * @param modifiedDate modified date
     * @return specification
     */
    public static Specification<ApprovalExecution> modifiedAfter(
            final LocalDateTime modifiedDate) {

        return (root, query, builder) -> modifiedDate == null
                ? builder.conjunction()
                : builder.greaterThan(root.get("lastModifiedDate"),
                        modifiedDate);
    }

    /**
     * Filters executions modified before the specified date.
     *
     * @param modifiedDate modified date
     * @return specification
     */
    public static Specification<ApprovalExecution> modifiedBefore(
            final LocalDateTime modifiedDate) {

        return (root, query, builder) -> modifiedDate == null
                ? builder.conjunction()
                : builder.lessThan(root.get("lastModifiedDate"),
                        modifiedDate);
    }

    /**
     * Filters executions modified within a period.
     *
     * @param startDate period start
     * @param endDate   period end
     * @return specification
     */
    public static Specification<ApprovalExecution> modifiedBetween(
            final LocalDateTime startDate,
            final LocalDateTime endDate) {

        return (root, query, builder) -> {

            if (startDate == null || endDate == null) {
                return builder.conjunction();
            }

            return builder.between(root.get("lastModifiedDate"),
                    startDate,
                    endDate);
        };
    }

    // -------------------------------------------------------------------------
    // Helper Specifications
    // -------------------------------------------------------------------------

    /**
     * Filters executions having a due date.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> hasDueDate() {

        return (root, query, builder) -> builder.isNotNull(root.get("dueAt"));
    }

    /**
     * Filters executions having completion remarks.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> hasCompletionRemarks() {

        return (root, query, builder) -> builder.and(
                builder.isNotNull(root.get("completionRemarks")),
                builder.notEqual(root.get("completionRemarks"), ""));
    }

    /**
     * Filters overdue executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> overdue() {

        return (root, query, builder) -> builder.and(
                builder.isNotNull(root.get("dueAt")),
                builder.lessThan(root.get("dueAt"), LocalDateTime.now()),
                builder.isFalse(root.get("completed")),
                builder.isFalse(root.get("cancelled")));
    }

    /**
     * Filters finished executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> finished() {

        return hasExecutionStatus(
                ApprovalExecutionStatus.COMPLETED);
    }

    /**
     * Filters pending executions.
     *
     * <p>
     * A pending execution is one that has been created
     * but has not yet started.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> pending() {

        return hasExecutionStatus(ApprovalExecutionStatus.CREATED)
                .and((root, query, builder) -> builder.isFalse(root.get("started")));
    }

    // -------------------------------------------------------------------------
    // Composite Specifications
    // -------------------------------------------------------------------------

    /**
     * Filters active executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> activeExecutions() {

        return isActive()
                .and(isStarted())
                .and(Specification.not(isCompleted()));
    }

    /**
     * Filters completed executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> completedExecutions() {

        return hasExecutionStatus(
                ApprovalExecutionStatus.COMPLETED);
    }

    /**
     * Filters cancelled executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> cancelledExecutions() {

        return hasExecutionStatus(
                ApprovalExecutionStatus.CANCELLED);
    }

    /**
     * Filters suspended executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> suspendedExecutions() {

        return hasExecutionStatus(
                ApprovalExecutionStatus.SUSPENDED);
    }

    /**
     * Filters running executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> runningExecutions() {

        return hasExecutionStatus(
                ApprovalExecutionStatus.IN_PROGRESS);
    }

    /**
     * Filters overdue executions.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> overdueExecutions() {

        return runningExecutions()
                .and(overdue());
    }

    // -------------------------------------------------------------------------
    // Sorting
    // -------------------------------------------------------------------------

    /**
     * Orders results by creation date ascending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByCreatedDateAsc() {

        return (root, query, builder) -> {

            query.orderBy(builder.asc(root.get("createdDate")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by creation date descending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByCreatedDateDesc() {

        return (root, query, builder) -> {

            query.orderBy(builder.desc(root.get("createdDate")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by start time ascending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByStartedAtAsc() {

        return (root, query, builder) -> {

            query.orderBy(builder.asc(root.get("startedAt")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by start time descending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByStartedAtDesc() {

        return (root, query, builder) -> {

            query.orderBy(builder.desc(root.get("startedAt")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by completion time ascending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByCompletedAtAsc() {

        return (root, query, builder) -> {

            query.orderBy(builder.asc(root.get("completedAt")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by completion time descending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByCompletedAtDesc() {

        return (root, query, builder) -> {

            query.orderBy(builder.desc(root.get("completedAt")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by due date ascending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByDueAtAsc() {

        return (root, query, builder) -> {

            query.orderBy(builder.asc(root.get("dueAt")));
            return builder.conjunction();
        };
    }

    /**
     * Orders results by due date descending.
     *
     * @return specification
     */
    public static Specification<ApprovalExecution> orderByDueAtDesc() {

        return (root, query, builder) -> {

            query.orderBy(builder.desc(root.get("dueAt")));
            return builder.conjunction();
        };
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    /**
     * Creates a new specification builder.
     *
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Fluent Specification Builder.
     */
    public static final class Builder {

        /**
         * Current specification.
         */
        private Specification<ApprovalExecution> specification = Specification.where(null);

        /**
         * Default constructor.
         */
        private Builder() {
        }

        /**
         * Starts the specification chain.
         *
         * @param specification specification
         * @return builder
         */
        public Builder where(
                final Specification<ApprovalExecution> specification) {

            if (specification != null) {
                this.specification = Specification.where(specification);
            }

            return this;
        }

        /**
         * Adds an AND specification.
         *
         * @param specification specification
         * @return builder
         */
        public Builder and(
                final Specification<ApprovalExecution> specification) {

            if (specification != null) {
                this.specification = this.specification.and(specification);
            }

            return this;
        }

        /**
         * Adds an OR specification.
         *
         * @param specification specification
         * @return builder
         */
        public Builder or(
                final Specification<ApprovalExecution> specification) {

            if (specification != null) {
                this.specification = this.specification.or(specification);
            }

            return this;
        }

        /**
         * Builds the composed specification.
         *
         * @return specification
         */
        public Specification<ApprovalExecution> build() {
            return specification;
        }

    }

}