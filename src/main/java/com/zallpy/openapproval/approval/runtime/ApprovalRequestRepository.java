package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalInitiatorType;
import com.zallpy.openapproval.approval.enums.ApprovalPriority;
import com.zallpy.openapproval.approval.enums.ApprovalRequestSource;
import com.zallpy.openapproval.approval.enums.ApprovalRequestStatus;
import com.zallpy.openapproval.approval.enums.ApprovalRequestType;
import com.zallpy.openapproval.approval.policy.ApprovalPolicy;
import com.zallpy.openapproval.approval.policy.ApprovalPolicyVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalRequest}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval requests.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalRequestRepository extends
                JpaRepository<ApprovalRequest, UUID>,
                JpaSpecificationExecutor<ApprovalRequest> {

        /**
         * Finds an approval request by request number.
         *
         * @param requestNumber request number
         * @return matching request
         */
        Optional<ApprovalRequest> findByRequestNumber(
                        String requestNumber);

        /**
         * Finds an approval request by business key.
         *
         * @param businessKey business key
         * @return matching request
         */
        Optional<ApprovalRequest> findByBusinessKey(
                        String businessKey);

        /**
         * Finds an approval request by correlation identifier.
         *
         * @param correlationId correlation identifier
         * @return matching request
         */
        Optional<ApprovalRequest> findByCorrelationId(
                        UUID correlationId);

        /**
         * Returns whether a request number already exists.
         *
         * @param requestNumber request number
         * @return true if found
         */
        boolean existsByRequestNumber(
                        String requestNumber);

        /**
         * Returns whether a business key already exists.
         *
         * @param businessKey business key
         * @return true if found
         */
        boolean existsByBusinessKey(
                        String businessKey);

        /**
         * Returns whether a correlation identifier exists.
         *
         * @param correlationId correlation identifier
         * @return true if found
         */
        boolean existsByCorrelationId(
                        UUID correlationId);

        /**
         * Finds requests belonging to the specified tenant.
         *
         * @param tenantId tenant identifier
         * @return matching requests
         */
        List<ApprovalRequest> findByTenantId(
                        UUID tenantId);

        /**
         * Finds requests for the specified approval policy.
         *
         * @param approvalPolicy approval policy
         * @return matching requests
         */
        List<ApprovalRequest> findByApprovalPolicy(
                        ApprovalPolicy approvalPolicy);

        /**
         * Finds requests for the specified policy version.
         *
         * @param approvalPolicyVersion approval policy version
         * @return matching requests
         */
        List<ApprovalRequest> findByApprovalPolicyVersion(
                        ApprovalPolicyVersion approvalPolicyVersion);

        /**
         * Finds requests by runtime status.
         *
         * @param status request status
         * @return matching requests
         */
        List<ApprovalRequest> findByStatus(
                        ApprovalRequestStatus status);

        /**
         * Finds requests by priority.
         *
         * @param priority approval priority
         * @return matching requests
         */
        List<ApprovalRequest> findByPriority(
                        ApprovalPriority priority);

        /**
         * Finds requests by request type.
         *
         * @param requestType request type
         * @return matching requests
         */
        List<ApprovalRequest> findByRequestType(
                        ApprovalRequestType requestType);

        /**
         * Finds requests by request source.
         *
         * @param requestSource request source
         * @return matching requests
         */
        List<ApprovalRequest> findByRequestSource(
                        ApprovalRequestSource requestSource);

        /**
         * Finds requests by initiator type.
         *
         * @param initiatorType initiator type
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorType(
                        ApprovalInitiatorType initiatorType);

        /**
         * Finds requests initiated by the specified user.
         *
         * @param initiatorId initiator identifier
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorId(
                        UUID initiatorId);

        /**
         * Finds requests by initiator email.
         *
         * @param initiatorEmail initiator email
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorEmail(
                        String initiatorEmail);

        /**
         * Finds requests by module name.
         *
         * @param moduleName module name
         * @return matching requests
         */
        List<ApprovalRequest> findByModuleName(
                        String moduleName);

        /**
         * Finds requests by business entity type.
         *
         * @param entityType entity type
         * @return matching requests
         */
        List<ApprovalRequest> findByEntityType(
                        String entityType);

        /**
         * Finds requests by business entity identifier.
         *
         * @param entityId entity identifier
         * @return matching requests
         */
        List<ApprovalRequest> findByEntityId(
                        String entityId);

        /**
         * Finds requests by business entity name.
         *
         * @param entityName entity name
         * @return matching requests
         */
        List<ApprovalRequest> findByEntityName(
                        String entityName);

        /**
         * Finds requests by reference number.
         *
         * @param referenceNumber reference number
         * @return matching requests
         */
        List<ApprovalRequest> findByReferenceNumber(
                        String referenceNumber);

        /**
         * Finds active requests.
         *
         * @param active active flag
         * @return matching requests
         */
        List<ApprovalRequest> findByActive(
                        boolean active);

        /**
         * Finds completed requests.
         *
         * @param completed completed flag
         * @return matching requests
         */
        List<ApprovalRequest> findByCompleted(
                        boolean completed);

        /**
         * Finds cancelled requests.
         *
         * @param cancelled cancelled flag
         * @return matching requests
         */
        List<ApprovalRequest> findByCancelled(
                        boolean cancelled);

        /**
         * Finds suspended requests.
         *
         * @param suspended suspended flag
         * @return matching requests
         */
        List<ApprovalRequest> findBySuspended(
                        boolean suspended);

        /**
         * Finds delegated requests.
         *
         * @param delegated delegated flag
         * @return matching requests
         */
        List<ApprovalRequest> findByDelegated(
                        boolean delegated);

        /**
         * Finds escalated requests.
         *
         * @param escalated escalated flag
         * @return matching requests
         */
        List<ApprovalRequest> findByEscalated(
                        boolean escalated);

        /**
         * Finds overdue requests.
         *
         * @param overdue overdue flag
         * @return matching requests
         */
        List<ApprovalRequest> findByOverdue(
                        boolean overdue);

        /**
         * Finds requests with breached SLA.
         *
         * @param slaBreached SLA breached flag
         * @return matching requests
         */
        List<ApprovalRequest> findBySlaBreached(
                        boolean slaBreached);

        /**
         * Finds requests submitted before the specified date.
         *
         * @param submittedAt submission timestamp
         * @return matching requests
         */
        List<ApprovalRequest> findBySubmittedAtBefore(
                        LocalDateTime submittedAt);

        /**
         * Finds requests submitted after the specified date.
         *
         * @param submittedAt submission timestamp
         * @return matching requests
         */
        List<ApprovalRequest> findBySubmittedAtAfter(
                        LocalDateTime submittedAt);

        /**
         * Finds requests due before the specified date.
         *
         * @param dueAt due timestamp
         * @return matching requests
         */
        List<ApprovalRequest> findByDueAtBefore(
                        LocalDateTime dueAt);

        /**
         * Finds requests completed after the specified date.
         *
         * @param completedAt completion timestamp
         * @return matching requests
         */
        List<ApprovalRequest> findByCompletedAtAfter(
                        LocalDateTime completedAt);

        /**
         * Counts requests by runtime status.
         *
         * @param status request status
         * @return request count
         */
        long countByStatus(
                        ApprovalRequestStatus status);

        /**
         * Counts requests by priority.
         *
         * @param priority approval priority
         * @return request count
         */
        long countByPriority(
                        ApprovalPriority priority);

        /**
         * Counts requests by request type.
         *
         * @param requestType request type
         * @return request count
         */
        long countByRequestType(
                        ApprovalRequestType requestType);

        /**
         * Counts requests by request source.
         *
         * @param requestSource request source
         * @return request count
         */
        long countByRequestSource(
                        ApprovalRequestSource requestSource);

        /**
         * Counts requests by approval policy.
         *
         * @param approvalPolicy approval policy
         * @return request count
         */
        long countByApprovalPolicy(
                        ApprovalPolicy approvalPolicy);

        /**
         * Counts requests by approval policy version.
         *
         * @param approvalPolicyVersion approval policy version
         * @return request count
         */
        long countByApprovalPolicyVersion(
                        ApprovalPolicyVersion approvalPolicyVersion);

        /**
         * Counts requests belonging to a tenant.
         *
         * @param tenantId tenant identifier
         * @return request count
         */
        long countByTenantId(
                        UUID tenantId);

        /**
         * Counts requests initiated by the specified user.
         *
         * @param initiatorId initiator identifier
         * @return request count
         */
        long countByInitiatorId(
                        UUID initiatorId);

        /**
         * Counts requests for the specified module.
         *
         * @param moduleName module name
         * @return request count
         */
        long countByModuleName(
                        String moduleName);

        /**
         * Counts requests by business entity type.
         *
         * @param entityType entity type
         * @return request count
         */
        long countByEntityType(
                        String entityType);

        /**
         * Counts requests by business entity identifier.
         *
         * @param entityId entity identifier
         * @return request count
         */
        long countByEntityId(
                        String entityId);

        /**
         * Counts active requests.
         *
         * @param active active flag
         * @return request count
         */
        long countByActive(
                        boolean active);

        /**
         * Counts completed requests.
         *
         * @param completed completed flag
         * @return request count
         */
        long countByCompleted(
                        boolean completed);

        /**
         * Counts cancelled requests.
         *
         * @param cancelled cancelled flag
         * @return request count
         */
        long countByCancelled(
                        boolean cancelled);

        /**
         * Counts suspended requests.
         *
         * @param suspended suspended flag
         * @return request count
         */
        long countBySuspended(
                        boolean suspended);

        /**
         * Counts delegated requests.
         *
         * @param delegated delegated flag
         * @return request count
         */
        long countByDelegated(
                        boolean delegated);

        /**
         * Counts escalated requests.
         *
         * @param escalated escalated flag
         * @return request count
         */
        long countByEscalated(
                        boolean escalated);

        /**
         * Counts overdue requests.
         *
         * @param overdue overdue flag
         * @return request count
         */
        long countByOverdue(
                        boolean overdue);

        /**
         * Counts requests with breached SLA.
         *
         * @param slaBreached SLA breached flag
         * @return request count
         */
        long countBySlaBreached(
                        boolean slaBreached);

        /**
         * Returns whether requests exist for the specified approval policy.
         *
         * @param approvalPolicy approval policy
         * @return true if found
         */
        boolean existsByApprovalPolicy(
                        ApprovalPolicy approvalPolicy);

        /**
         * Returns whether requests exist for the specified tenant.
         *
         * @param tenantId tenant identifier
         * @return true if found
         */
        boolean existsByTenantId(
                        UUID tenantId);

        /**
         * Returns whether requests exist for the specified initiator.
         *
         * @param initiatorId initiator identifier
         * @return true if found
         */
        boolean existsByInitiatorId(
                        UUID initiatorId);

        /**
         * Returns whether requests exist for the specified business entity.
         *
         * @param entityId business entity identifier
         * @return true if found
         */
        boolean existsByEntityId(
                        String entityId);

        /**
         * Returns whether requests exist for the specified module.
         *
         * @param moduleName module name
         * @return true if found
         */
        boolean existsByModuleName(
                        String moduleName);

        /**
         * Deletes a request by request number.
         *
         * @param requestNumber request number
         */
        void deleteByRequestNumber(
                        String requestNumber);

        /**
         * Deletes a request by business key.
         *
         * @param businessKey business key
         */
        void deleteByBusinessKey(
                        String businessKey);

        /**
         * Deletes requests belonging to the specified tenant.
         *
         * @param tenantId tenant identifier
         */
        void deleteByTenantId(
                        UUID tenantId);

        /**
         * Finds requests by request number containing the supplied text.
         *
         * @param requestNumber request number fragment
         * @return matching requests
         */
        List<ApprovalRequest> findByRequestNumberContainingIgnoreCase(
                        String requestNumber);

        /**
         * Finds requests by business key containing the supplied text.
         *
         * @param businessKey business key fragment
         * @return matching requests
         */
        List<ApprovalRequest> findByBusinessKeyContainingIgnoreCase(
                        String businessKey);

        /**
         * Finds requests whose title contains the supplied text.
         *
         * @param title request title
         * @return matching requests
         */
        List<ApprovalRequest> findByTitleContainingIgnoreCase(
                        String title);

        /**
         * Finds requests whose description contains the supplied text.
         *
         * @param description description fragment
         * @return matching requests
         */
        List<ApprovalRequest> findByDescriptionContainingIgnoreCase(
                        String description);

        /**
         * Finds requests by module name containing the supplied text.
         *
         * @param moduleName module name
         * @return matching requests
         */
        List<ApprovalRequest> findByModuleNameContainingIgnoreCase(
                        String moduleName);

        /**
         * Finds requests by entity type containing the supplied text.
         *
         * @param entityType entity type
         * @return matching requests
         */
        List<ApprovalRequest> findByEntityTypeContainingIgnoreCase(
                        String entityType);

        /**
         * Finds requests by entity name containing the supplied text.
         *
         * @param entityName entity name
         * @return matching requests
         */
        List<ApprovalRequest> findByEntityNameContainingIgnoreCase(
                        String entityName);

        /**
         * Finds requests by initiator name containing the supplied text.
         *
         * @param initiatorName initiator name
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorNameContainingIgnoreCase(
                        String initiatorName);

        /**
         * Finds requests by initiator email containing the supplied text.
         *
         * @param initiatorEmail initiator email
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorEmailContainingIgnoreCase(
                        String initiatorEmail);

        /**
         * Finds requests by approval policy ordered by creation date.
         *
         * @param approvalPolicy approval policy
         * @return matching requests
         */
        List<ApprovalRequest> findByApprovalPolicyOrderByCreatedDateDesc(
                        ApprovalPolicy approvalPolicy);

        /**
         * Finds requests by tenant ordered by creation date.
         *
         * @param tenantId tenant identifier
         * @return matching requests
         */
        List<ApprovalRequest> findByTenantIdOrderByCreatedDateDesc(
                        UUID tenantId);

        /**
         * Finds requests by initiator ordered by creation date.
         *
         * @param initiatorId initiator identifier
         * @return matching requests
         */
        List<ApprovalRequest> findByInitiatorIdOrderByCreatedDateDesc(
                        UUID initiatorId);

        /**
         * Finds requests by status ordered by creation date.
         *
         * @param status request status
         * @return matching requests
         */
        List<ApprovalRequest> findByStatusOrderByCreatedDateDesc(
                        ApprovalRequestStatus status);

        /**
         * Finds requests by priority ordered by creation date.
         *
         * @param priority request priority
         * @return matching requests
         */
        List<ApprovalRequest> findByPriorityOrderByCreatedDateDesc(
                        ApprovalPriority priority);

        /**
         * Finds requests created after the specified date.
         *
         * @param createdDate creation date
         * @return matching requests
         */
        List<ApprovalRequest> findByCreatedDateAfter(
                        LocalDateTime createdDate);

        /**
         * Finds requests created between the specified dates.
         *
         * @param startDate start date
         * @param endDate   end date
         * @return matching requests
         */
        List<ApprovalRequest> findByCreatedDateBetween(
                        LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * Finds requests modified after the specified date.
         *
         * @param lastModifiedDate modification date
         * @return matching requests
         */
        List<ApprovalRequest> findByLastModifiedDateAfter(
                        LocalDateTime lastModifiedDate);

        /**
         * Finds requests modified between the specified dates.
         *
         * @param startDate start date
         * @param endDate   end date
         * @return matching requests
         */
        List<ApprovalRequest> findByLastModifiedDateBetween(
                        LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * Finds requests submitted between the specified dates.
         *
         * @param startDate start date
         * @param endDate   end date
         * @return matching requests
         */
        List<ApprovalRequest> findBySubmittedAtBetween(
                        LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * Finds requests due between the specified dates.
         *
         * @param startDate start date
         * @param endDate   end date
         * @return matching requests
         */
        List<ApprovalRequest> findByDueAtBetween(
                        LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * Finds requests completed between the specified dates.
         *
         * @param startDate start date
         * @param endDate   end date
         * @return matching requests
         */
        List<ApprovalRequest> findByCompletedAtBetween(
                        LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * Finds the earliest recorded request.
         *
         * @return earliest request
         */
        Optional<ApprovalRequest> findTopByOrderByCreatedDateAsc();

        /**
         * Finds the most recently created request.
         *
         * @return latest request
         */
        Optional<ApprovalRequest> findTopByOrderByCreatedDateDesc();

        /**
         * Finds the earliest submitted request.
         *
         * @return earliest submitted request
         */
        Optional<ApprovalRequest> findTopByOrderBySubmittedAtAsc();

        /**
         * Finds the latest submitted request.
         *
         * @return latest submitted request
         */
        Optional<ApprovalRequest> findTopByOrderBySubmittedAtDesc();

        /**
         * Finds the latest request for an approval policy.
         *
         * @param approvalPolicy approval policy
         * @return latest request
         */
        Optional<ApprovalRequest> findTopByApprovalPolicyOrderByCreatedDateDesc(
                        ApprovalPolicy approvalPolicy);

        /**
         * Finds the latest request for a tenant.
         *
         * @param tenantId tenant identifier
         * @return latest request
         */
        Optional<ApprovalRequest> findTopByTenantIdOrderByCreatedDateDesc(
                        UUID tenantId);

        /**
         * Finds the latest request for an initiator.
         *
         * @param initiatorId initiator identifier
         * @return latest request
         */
        Optional<ApprovalRequest> findTopByInitiatorIdOrderByCreatedDateDesc(
                        UUID initiatorId);

        /**
         * Finds the latest request having the specified status.
         *
         * @param status request status
         * @return latest request
         */
        Optional<ApprovalRequest> findTopByStatusOrderByCreatedDateDesc(
                        ApprovalRequestStatus status);

}