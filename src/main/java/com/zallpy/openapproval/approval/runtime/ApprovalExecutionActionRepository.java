package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalActionType;
import com.zallpy.openapproval.approval.enums.ApprovalDecision;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionAction}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution actions.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionActionRepository extends
        JpaRepository<ApprovalExecutionAction, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionAction> {

    /**
     * Finds an action by its business reference.
     *
     * @param actionReference action reference
     * @return matching action
     */
    Optional<ApprovalExecutionAction> findByActionReference(
            String actionReference);

    /**
     * Returns whether an action reference exists.
     *
     * @param actionReference action reference
     * @return true if found
     */
    boolean existsByActionReference(
            String actionReference);

    /**
     * Finds actions belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Finds actions belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds actions belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds actions by action type.
     *
     * @param actionType action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionType(
            ApprovalActionType actionType);

    /**
     * Finds actions by approval decision.
     *
     * @param decision approval decision
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByDecision(
            ApprovalDecision decision);

    /**
     * Finds actions performed by the specified actor.
     *
     * @param actorId actor identifier
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorId(
            String actorId);

    /**
     * Finds actions by actor email.
     *
     * @param actorEmail actor email
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorEmail(
            String actorEmail);

    /**
     * Finds system-generated actions.
     *
     * @param systemAction system-generated flag
     * @return matching actions
     */
    List<ApprovalExecutionAction> findBySystemAction(
            boolean systemAction);

    /**
     * Finds active actions.
     *
     * @param active active flag
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActive(
            boolean active);

    /**
     * Finds actions before the specified time.
     *
     * @param actionTime action timestamp
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionTimeBefore(
            LocalDateTime actionTime);

    /**
     * Finds actions after the specified time.
     *
     * @param actionTime action timestamp
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionTimeAfter(
            LocalDateTime actionTime);

    /**
     * Finds actions by approval execution ordered by action time.
     *
     * @param approvalExecution approval execution
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionOrderByActionTimeAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds actions by execution stage ordered by action time.
     *
     * @param approvalExecutionStage execution stage
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionStageOrderByActionTimeAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds actions by execution task ordered by action time.
     *
     * @param approvalExecutionTask execution task
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionTaskOrderByActionTimeAsc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds actions performed by an actor ordered by action time.
     *
     * @param actorId actor identifier
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorIdOrderByActionTimeDesc(
            String actorId);

    /**
     * Finds actions by action type ordered by action time.
     *
     * @param actionType action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionTypeOrderByActionTimeDesc(
            ApprovalActionType actionType);

    /**
     * Finds actions by approval decision ordered by action time.
     *
     * @param decision approval decision
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByDecisionOrderByActionTimeDesc(
            ApprovalDecision decision);

    /**
     * Finds actions occurring between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionTimeBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds system-generated actions ordered by action time.
     *
     * @param systemAction system-generated flag
     * @return matching actions
     */
    List<ApprovalExecutionAction> findBySystemActionOrderByActionTimeDesc(
            boolean systemAction);

    /**
     * Finds actions by approval execution and action type.
     *
     * @param approvalExecution approval execution
     * @param actionType        action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionAndActionType(
            ApprovalExecution approvalExecution,
            ApprovalActionType actionType);

    /**
     * Finds actions by execution stage and action type.
     *
     * @param approvalExecutionStage execution stage
     * @param actionType             action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionStageAndActionType(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalActionType actionType);

    /**
     * Finds actions by execution task and action type.
     *
     * @param approvalExecutionTask execution task
     * @param actionType            action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionTaskAndActionType(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalActionType actionType);

    /**
     * Finds actions by approval execution and decision.
     *
     * @param approvalExecution approval execution
     * @param decision          approval decision
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionAndDecision(
            ApprovalExecution approvalExecution,
            ApprovalDecision decision);

    /**
     * Counts actions by action type.
     *
     * @param actionType action type
     * @return action count
     */
    long countByActionType(
            ApprovalActionType actionType);

    /**
     * Counts actions by approval decision.
     *
     * @param decision approval decision
     * @return action count
     */
    long countByDecision(
            ApprovalDecision decision);

    /**
     * Counts actions belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return action count
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts actions belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return action count
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts actions belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return action count
     */
    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Counts actions by actor identifier.
     *
     * @param actorId actor identifier
     * @return action count
     */
    long countByActorId(
            String actorId);

    /**
     * Counts system-generated actions.
     *
     * @param systemAction system-generated flag
     * @return action count
     */
    long countBySystemAction(
            boolean systemAction);

    /**
     * Counts active actions.
     *
     * @param active active flag
     * @return action count
     */
    long countByActive(
            boolean active);

    /**
     * Returns whether actions exist for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return true if found
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether actions exist for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return true if found
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns whether actions exist for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return true if found
     */
    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds the earliest recorded action.
     *
     * @return earliest action
     */
    Optional<ApprovalExecutionAction> findTopByOrderByActionTimeAsc();

    /**
     * Finds the most recently recorded action.
     *
     * @return latest action
     */
    Optional<ApprovalExecutionAction> findTopByOrderByActionTimeDesc();

    /**
     * Deletes an action by its business reference.
     *
     * @param actionReference action reference
     */
    void deleteByActionReference(
            String actionReference);

    /**
     * Finds actions whose action reference contains the supplied text.
     *
     * @param actionReference action reference fragment
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActionReferenceContainingIgnoreCase(
            String actionReference);

    /**
     * Finds actions performed by actors whose names contain the supplied text.
     *
     * @param actorName actor name
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorNameContainingIgnoreCase(
            String actorName);

    /**
     * Finds actions by actor email ignoring case.
     *
     * @param actorEmail actor email
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorEmailContainingIgnoreCase(
            String actorEmail);

    /**
     * Finds actions by approval execution ordered by creation date.
     *
     * @param approvalExecution approval execution
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionOrderByCreatedDateDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds actions by execution stage ordered by creation date.
     *
     * @param approvalExecutionStage execution stage
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionStageOrderByCreatedDateDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds actions by execution task ordered by creation date.
     *
     * @param approvalExecutionTask execution task
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByApprovalExecutionTaskOrderByCreatedDateDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds actions created after the specified date.
     *
     * @param createdDate creation date
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByCreatedDateAfter(
            LocalDateTime createdDate);

    /**
     * Finds actions created between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds actions modified after the specified date.
     *
     * @param lastModifiedDate modification date
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    /**
     * Finds actions modified between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds actions for the specified actor and action type.
     *
     * @param actorId    actor identifier
     * @param actionType action type
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorIdAndActionType(
            String actorId,
            ApprovalActionType actionType);

    /**
     * Finds actions for the specified actor and decision.
     *
     * @param actorId  actor identifier
     * @param decision approval decision
     * @return matching actions
     */
    List<ApprovalExecutionAction> findByActorIdAndDecision(
            String actorId,
            ApprovalDecision decision);

    /**
     * Counts actions for an approval execution by action type.
     *
     * @param approvalExecution approval execution
     * @param actionType        action type
     * @return action count
     */
    long countByApprovalExecutionAndActionType(
            ApprovalExecution approvalExecution,
            ApprovalActionType actionType);

    /**
     * Counts actions for an execution stage by action type.
     *
     * @param approvalExecutionStage execution stage
     * @param actionType             action type
     * @return action count
     */
    long countByApprovalExecutionStageAndActionType(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalActionType actionType);

    /**
     * Counts actions for an execution task by action type.
     *
     * @param approvalExecutionTask execution task
     * @param actionType            action type
     * @return action count
     */
    long countByApprovalExecutionTaskAndActionType(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalActionType actionType);

    /**
     * Counts actions for an approval execution by decision.
     *
     * @param approvalExecution approval execution
     * @param decision          approval decision
     * @return action count
     */
    long countByApprovalExecutionAndDecision(
            ApprovalExecution approvalExecution,
            ApprovalDecision decision);

    /**
     * Counts actions for an execution stage by decision.
     *
     * @param approvalExecutionStage execution stage
     * @param decision               approval decision
     * @return action count
     */
    long countByApprovalExecutionStageAndDecision(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalDecision decision);

    /**
     * Counts actions for an execution task by decision.
     *
     * @param approvalExecutionTask execution task
     * @param decision              approval decision
     * @return action count
     */
    long countByApprovalExecutionTaskAndDecision(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalDecision decision);

    /**
     * Finds the earliest action for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return earliest action
     */
    Optional<ApprovalExecutionAction> findTopByApprovalExecutionOrderByActionTimeAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest action for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return latest action
     */
    Optional<ApprovalExecutionAction> findTopByApprovalExecutionOrderByActionTimeDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest action for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return latest action
     */
    Optional<ApprovalExecutionAction> findTopByApprovalExecutionStageOrderByActionTimeDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the latest action for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return latest action
     */
    Optional<ApprovalExecutionAction> findTopByApprovalExecutionTaskOrderByActionTimeDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds the latest action performed by an actor.
     *
     * @param actorId actor identifier
     * @return latest action
     */
    Optional<ApprovalExecutionAction> findTopByActorIdOrderByActionTimeDesc(
            String actorId);
}