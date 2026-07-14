package com.zallpy.openapproval.approval.entity;

import com.zallpy.openapproval.approval.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalRequest}.
 *
 * <p>
 * Provides persistence operations for approval requests.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalRequestRepository
        extends JpaRepository<ApprovalRequest, UUID>,
        JpaSpecificationExecutor<ApprovalRequest> {

    /**
     * Finds an approval request by request reference.
     *
     * @param requestReference request reference
     * @return approval request
     */
    Optional<ApprovalRequest> findByRequestReference(String requestReference);

    /**
     * Determines whether a request reference already exists.
     *
     * @param requestReference request reference
     * @return true if it exists
     */
    boolean existsByRequestReference(String requestReference);

    /**
     * Finds all requests submitted by a user.
     *
     * @param submittedBy submitting user
     * @return approval requests
     */
    List<ApprovalRequest> findBySubmittedBy(UUID submittedBy);

    /**
     * Finds all requests with the specified status.
     *
     * @param status approval status
     * @return approval requests
     */
    List<ApprovalRequest> findByStatus(ApprovalStatus status);

    /**
     * Finds all requests for a business resource.
     *
     * @param resourceType resource type
     * @param resourceId resource identifier
     * @return approval requests
     */
    List<ApprovalRequest> findByResourceTypeAndResourceId(
            String resourceType,
            UUID resourceId);

    /**
     * Finds all requests for a business key.
     *
     * @param businessKey business key
     * @return approval requests
     */
    List<ApprovalRequest> findByBusinessKey(String businessKey);

    /**
     * Finds all completed requests.
     *
     * @return completed approval requests
     */
    List<ApprovalRequest> findByCompletedTrue();

    /**
     * Finds all active requests.
     *
     * @return active approval requests
     */
    List<ApprovalRequest> findByCompletedFalseAndCancelledFalseAndRecalledFalse();

}