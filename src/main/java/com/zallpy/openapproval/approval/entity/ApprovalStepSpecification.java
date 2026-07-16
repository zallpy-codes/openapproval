package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Specifications for {@link ApprovalStep}.
 *
 * <p>
 * Encapsulates reusable query predicates for runtime
 * approval step processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public final class ApprovalStepSpecification {

        /**
         * Utility class.
         */
        private ApprovalStepSpecification() {
        }

        /**
         * Returns approval steps requiring reminder processing.
         *
         * @return reminder specification
         */
        public static Specification<ApprovalStep> requiresReminder() {

                return (root, query, cb) -> cb.and(

                                cb.equal(
                                                root.get("status"),
                                                ApprovalStatus.PENDING),

                                cb.isFalse(
                                                root.get("completed")),

                                cb.isFalse(
                                                root.get("timedOut")),

                                cb.isNotNull(
                                                root.get("dueAt")),

                                cb.greaterThan(
                                                root.get("dueAt"),
                                                LocalDateTime.now()));
        }

        /**
         * Returns approval steps requiring timeout processing.
         *
         * @return timeout specification
         */
        public static Specification<ApprovalStep> requiresTimeout() {

                return (root, query, cb) -> cb.and(

                                cb.equal(
                                                root.get("status"),
                                                ApprovalStatus.PENDING),

                                cb.isFalse(
                                                root.get("completed")),

                                cb.isFalse(
                                                root.get("timedOut")),

                                cb.isNotNull(
                                                root.get("dueAt")),

                                cb.lessThanOrEqualTo(
                                                root.get("dueAt"),
                                                LocalDateTime.now()));
        }

        /**
         * Returns approval steps eligible for escalation.
         *
         * <p>
         * An approval step is eligible when:
         * <ul>
         * <li>It is pending</li>
         * <li>It has not completed</li>
         * <li>It has not timed out</li>
         * <li>It has a due date</li>
         * <li>The due date has passed</li>
         * <li>It has not already been escalated</li>
         * </ul>
         * </p>
         *
         * @return escalation specification
         */
        public static Specification<ApprovalStep> requiresEscalation() {

                return (root, query, cb) -> cb.and(

                                cb.equal(
                                                root.get("status"),
                                                ApprovalStatus.PENDING),

                                cb.isFalse(
                                                root.get("completed")),

                                cb.isFalse(
                                                root.get("timedOut")),

                                cb.isFalse(
                                                root.get("escalated")),

                                cb.isNotNull(
                                                root.get("dueAt")),

                                cb.lessThanOrEqualTo(
                                                root.get("dueAt"),
                                                LocalDateTime.now()));
        }

}