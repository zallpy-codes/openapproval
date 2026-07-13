package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalTaskStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionTask}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution tasks.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionTaskRepository extends
        JpaRepository<ApprovalExecutionTask, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionTask> {

    /**
     * Finds a task by its unique business reference.
     *
     * @param taskReference task reference
     * @return matching task
     */
    Optional<ApprovalExecutionTask> findByTaskReference(
            String taskReference);

    /**
     * Returns whether a task reference exists.
     *
     * @param taskReference task reference
     * @return true if found
     */
    boolean existsByTaskReference(
            String taskReference);

    /**
     * Finds all tasks belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Finds all tasks belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds tasks by task status.
     *
     * @param taskStatus task status
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByTaskStatus(
            ApprovalTaskStatus taskStatus);

    /**
     * Finds tasks assigned to the specified user.
     *
     * @param assigneeId assignee identifier
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByAssigneeId(
            String assigneeId);

    /**
     * Finds tasks assigned to the specified email.
     *
     * @param assigneeEmail assignee email
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByAssigneeEmail(
            String assigneeEmail);

    /**
     * Finds delegated tasks.
     *
     * @param delegated delegated flag
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByDelegated(
            boolean delegated);

    /**
     * Finds escalated tasks.
     *
     * @param escalated escalated flag
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByEscalated(
            boolean escalated);

    /**
     * Finds active tasks.
     *
     * @param active active flag
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByActive(
            boolean active);

    /**
     * Finds tasks due before the specified date/time.
     *
     * @param dueAt due date/time
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByDueAtBefore(
            LocalDateTime dueAt);

    /**
     * Finds tasks due after the specified date/time.
     *
     * @param dueAt due date/time
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByDueAtAfter(
            LocalDateTime dueAt);

    /**
     * Finds tasks belonging to an approval execution ordered by assignment time.
     *
     * @param approvalExecution approval execution
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionOrderByAssignedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds tasks belonging to an execution stage ordered by assignment time.
     *
     * @param approvalExecutionStage execution stage
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionStageOrderByAssignedAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds tasks for an approval execution by task status.
     *
     * @param approvalExecution approval execution
     * @param taskStatus        task status
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionAndTaskStatus(
            ApprovalExecution approvalExecution,
            ApprovalTaskStatus taskStatus);

    /**
     * Finds tasks for an execution stage by task status.
     *
     * @param approvalExecutionStage execution stage
     * @param taskStatus             task status
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionStageAndTaskStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalTaskStatus taskStatus);

    /**
     * Finds active tasks for an approval execution.
     *
     * @param approvalExecution approval execution
     * @param active            active flag
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionAndActive(
            ApprovalExecution approvalExecution,
            boolean active);

    /**
     * Finds active tasks for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @param active                 active flag
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionStageAndActive(
            ApprovalExecutionStage approvalExecutionStage,
            boolean active);

    /**
     * Finds tasks assigned to the specified user ordered by due date.
     *
     * @param assigneeId assignee identifier
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findByAssigneeIdOrderByDueAtAsc(
            String assigneeId);

    /**
     * Finds tasks assigned to the specified email ordered by due date.
     *
     * @param assigneeEmail assignee email
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findByAssigneeEmailOrderByDueAtAsc(
            String assigneeEmail);

    /**
     * Finds tasks ordered by due date.
     *
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findAllByOrderByDueAtAsc();

    /**
     * Finds tasks ordered by assignment time.
     *
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findAllByOrderByAssignedAtAsc();

    /**
     * Finds tasks ordered by creation date.
     *
     * @return ordered execution tasks
     */
    List<ApprovalExecutionTask> findAllByOrderByCreatedDateDesc();

    /**
     * Finds tasks due within the specified period.
     *
     * @param startDate start date/time
     * @param endDate   end date/time
     * @return execution tasks
     */
    List<ApprovalExecutionTask> findByDueAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts tasks by task status.
     *
     * @param taskStatus task status
     * @return task count
     */
    long countByTaskStatus(
            ApprovalTaskStatus taskStatus);

    /**
     * Counts tasks belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return task count
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts tasks belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return task count
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts tasks for an approval execution by status.
     *
     * @param approvalExecution approval execution
     * @param taskStatus        task status
     * @return task count
     */
    long countByApprovalExecutionAndTaskStatus(
            ApprovalExecution approvalExecution,
            ApprovalTaskStatus taskStatus);

    /**
     * Counts tasks for an execution stage by status.
     *
     * @param approvalExecutionStage execution stage
     * @param taskStatus             task status
     * @return task count
     */
    long countByApprovalExecutionStageAndTaskStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalTaskStatus taskStatus);

    /**
     * Counts active tasks.
     *
     * @param active active flag
     * @return task count
     */
    long countByActive(
            boolean active);

    /**
     * Returns whether tasks exist for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return true if found
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether tasks exist for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return true if found
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the first assigned task for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return first task
     */
    Optional<ApprovalExecutionTask> findTopByApprovalExecutionOrderByAssignedAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest assigned task for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByApprovalExecutionOrderByAssignedAtDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the first assigned task for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return first task
     */
    Optional<ApprovalExecutionTask> findTopByApprovalExecutionStageOrderByAssignedAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the most recently created task.
     *
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByOrderByCreatedDateDesc();

    /**
     * Deletes a task by its business reference.
     *
     * @param taskReference task reference
     */
    void deleteByTaskReference(
            String taskReference);

    /**
     * Finds tasks by task reference ignoring case.
     *
     * @param taskReference task reference
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByTaskReferenceContainingIgnoreCase(
            String taskReference);

    /**
     * Finds tasks whose task name contains the supplied text.
     *
     * @param taskName task name fragment
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByTaskNameContainingIgnoreCase(
            String taskName);

    /**
     * Finds tasks assigned to the specified assignee name.
     *
     * @param assigneeName assignee name
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByAssigneeNameContainingIgnoreCase(
            String assigneeName);

    /**
     * Finds tasks by approval execution and task reference.
     *
     * @param approvalExecution approval execution
     * @param taskReference     task reference
     * @return matching task
     */
    Optional<ApprovalExecutionTask> findByApprovalExecutionAndTaskReference(
            ApprovalExecution approvalExecution,
            String taskReference);

    /**
     * Finds tasks by approval execution stage and task reference.
     *
     * @param approvalExecutionStage execution stage
     * @param taskReference          task reference
     * @return matching task
     */
    Optional<ApprovalExecutionTask> findByApprovalExecutionStageAndTaskReference(
            ApprovalExecutionStage approvalExecutionStage,
            String taskReference);

    /**
     * Finds tasks by task status ordered by due date.
     *
     * @param taskStatus task status
     * @return ordered tasks
     */
    List<ApprovalExecutionTask> findByTaskStatusOrderByDueAtAsc(
            ApprovalTaskStatus taskStatus);

    /**
     * Finds tasks by approval execution ordered by due date.
     *
     * @param approvalExecution approval execution
     * @return ordered tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionOrderByDueAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds tasks by execution stage ordered by due date.
     *
     * @param approvalExecutionStage execution stage
     * @return ordered tasks
     */
    List<ApprovalExecutionTask> findByApprovalExecutionStageOrderByDueAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds tasks created after the specified date.
     *
     * @param createdDate creation date
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByCreatedDateAfter(
            LocalDateTime createdDate);

    /**
     * Finds tasks created between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByCreatedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds tasks modified after the specified date.
     *
     * @param lastModifiedDate last modification date
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByLastModifiedDateAfter(
            LocalDateTime lastModifiedDate);

    /**
     * Finds tasks modified between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching tasks
     */
    List<ApprovalExecutionTask> findByLastModifiedDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Counts active tasks for an approval execution.
     *
     * @param approvalExecution approval execution
     * @param active            active flag
     * @return task count
     */
    long countByApprovalExecutionAndActive(
            ApprovalExecution approvalExecution,
            boolean active);

    /**
     * Counts active tasks for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @param active                 active flag
     * @return task count
     */
    long countByApprovalExecutionStageAndActive(
            ApprovalExecutionStage approvalExecutionStage,
            boolean active);

    /**
     * Counts tasks by status and active flag.
     *
     * @param taskStatus task status
     * @param active     active flag
     * @return task count
     */
    long countByTaskStatusAndActive(
            ApprovalTaskStatus taskStatus,
            boolean active);

    /**
     * Finds the latest task created for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByApprovalExecutionOrderByCreatedDateDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds the latest task created for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByApprovalExecutionStageOrderByCreatedDateDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds the latest task by task status.
     *
     * @param taskStatus task status
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByTaskStatusOrderByCreatedDateDesc(
            ApprovalTaskStatus taskStatus);

    /**
     * Finds the latest active task.
     *
     * @param active active flag
     * @return latest task
     */
    Optional<ApprovalExecutionTask> findTopByActiveOrderByCreatedDateDesc(
            boolean active);
}