package com.zallpy.openapproval.approval.runtime;

import com.zallpy.openapproval.approval.enums.ApprovalReminderChannel;
import com.zallpy.openapproval.approval.enums.ApprovalReminderStatus;
import com.zallpy.openapproval.approval.execution.ApprovalExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link ApprovalExecutionReminder}.
 *
 * <p>
 * Provides persistence operations and runtime query methods
 * for approval execution reminders.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Repository
public interface ApprovalExecutionReminderRepository extends
        JpaRepository<ApprovalExecutionReminder, UUID>,
        JpaSpecificationExecutor<ApprovalExecutionReminder> {

    /**
     * Finds a reminder by its business reference.
     *
     * @param reminderReference reminder reference
     * @return matching reminder
     */
    Optional<ApprovalExecutionReminder> findByReminderReference(
            String reminderReference);

    /**
     * Returns whether a reminder reference exists.
     *
     * @param reminderReference reminder reference
     * @return true if found
     */
    boolean existsByReminderReference(
            String reminderReference);

    /**
     * Finds reminders belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Finds reminders belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds reminders belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds reminders by reminder channel.
     *
     * @param channel reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByChannel(
            ApprovalReminderChannel channel);

    /**
     * Finds reminders by reminder status.
     *
     * @param status reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByStatus(
            ApprovalReminderStatus status);

    /**
     * Finds reminders by recipient identifier.
     *
     * @param recipientId recipient identifier
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientId(
            String recipientId);

    /**
     * Finds reminders by recipient email.
     *
     * @param recipientEmail recipient email
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientEmail(
            String recipientEmail);

    /**
     * Finds active reminders.
     *
     * @param active active flag
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByActive(
            boolean active);

    /**
     * Finds reminders scheduled before the specified time.
     *
     * @param scheduledAt scheduled timestamp
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByScheduledAtBefore(
            LocalDateTime scheduledAt);

    /**
     * Finds reminders scheduled after the specified time.
     *
     * @param scheduledAt scheduled timestamp
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByScheduledAtAfter(
            LocalDateTime scheduledAt);

    /**
     * Finds reminders sent before the specified time.
     *
     * @param sentAt sent timestamp
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findBySentAtBefore(
            LocalDateTime sentAt);

    /**
     * Finds reminders sent after the specified time.
     *
     * @param sentAt sent timestamp
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findBySentAtAfter(
            LocalDateTime sentAt);

    /**
     * Finds reminders scheduled between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByScheduledAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds reminders sent between the specified dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findBySentAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Finds reminders by approval execution ordered by scheduled time.
     *
     * @param approvalExecution approval execution
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionOrderByScheduledAtAsc(
            ApprovalExecution approvalExecution);

    /**
     * Finds reminders by execution stage ordered by scheduled time.
     *
     * @param approvalExecutionStage execution stage
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStageOrderByScheduledAtAsc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds reminders by execution task ordered by scheduled time.
     *
     * @param approvalExecutionTask execution task
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTaskOrderByScheduledAtAsc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds reminders for a recipient ordered by scheduled time.
     *
     * @param recipientId recipient identifier
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientIdOrderByScheduledAtDesc(
            String recipientId);

    /**
     * Finds reminders by reminder status ordered by scheduled time.
     *
     * @param status reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByStatusOrderByScheduledAtDesc(
            ApprovalReminderStatus status);

    /**
     * Finds reminders by reminder channel ordered by scheduled time.
     *
     * @param channel reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByChannelOrderByScheduledAtDesc(
            ApprovalReminderChannel channel);

    /**
     * Finds active reminders ordered by scheduled time.
     *
     * @param active active flag
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByActiveOrderByScheduledAtAsc(
            boolean active);

    /**
     * Finds reminders by approval execution ordered by creation date.
     *
     * @param approvalExecution approval execution
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionOrderByCreatedDateDesc(
            ApprovalExecution approvalExecution);

    /**
     * Finds reminders by execution stage ordered by creation date.
     *
     * @param approvalExecutionStage execution stage
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStageOrderByCreatedDateDesc(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Finds reminders by execution task ordered by creation date.
     *
     * @param approvalExecutionTask execution task
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTaskOrderByCreatedDateDesc(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Finds reminders by approval execution and reminder status.
     *
     * @param approvalExecution approval execution
     * @param status            reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalReminderStatus status);

    /**
     * Finds reminders by execution stage and reminder status.
     *
     * @param approvalExecutionStage execution stage
     * @param status                 reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStageAndStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalReminderStatus status);

    /**
     * Finds reminders by execution task and reminder status.
     *
     * @param approvalExecutionTask execution task
     * @param status                reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTaskAndStatus(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalReminderStatus status);

    /**
     * Finds reminders by approval execution and reminder channel.
     *
     * @param approvalExecution approval execution
     * @param channel           reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionAndChannel(
            ApprovalExecution approvalExecution,
            ApprovalReminderChannel channel);

    /**
     * Finds reminders by execution stage and reminder channel.
     *
     * @param approvalExecutionStage execution stage
     * @param channel                reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStageAndChannel(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalReminderChannel channel);

    /**
     * Finds reminders by execution task and reminder channel.
     *
     * @param approvalExecutionTask execution task
     * @param channel               reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTaskAndChannel(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalReminderChannel channel);

    /**
     * Finds reminders for a recipient and reminder status.
     *
     * @param recipientId recipient identifier
     * @param status      reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientIdAndStatus(
            String recipientId,
            ApprovalReminderStatus status);

    /**
     * Finds reminders for a recipient and reminder channel.
     *
     * @param recipientId recipient identifier
     * @param channel     reminder channel
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientIdAndChannel(
            String recipientId,
            ApprovalReminderChannel channel);

    /**
     * Finds reminders by recipient email and reminder status.
     *
     * @param recipientEmail recipient email
     * @param status         reminder status
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByRecipientEmailAndStatus(
            String recipientEmail,
            ApprovalReminderStatus status);

    /**
     * Finds reminders by approval execution and recipient identifier.
     *
     * @param approvalExecution approval execution
     * @param recipientId       recipient identifier
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionAndRecipientId(
            ApprovalExecution approvalExecution,
            String recipientId);

    /**
     * Finds reminders by execution stage and recipient identifier.
     *
     * @param approvalExecutionStage execution stage
     * @param recipientId            recipient identifier
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionStageAndRecipientId(
            ApprovalExecutionStage approvalExecutionStage,
            String recipientId);

    /**
     * Finds reminders by execution task and recipient identifier.
     *
     * @param approvalExecutionTask execution task
     * @param recipientId           recipient identifier
     * @return matching reminders
     */
    List<ApprovalExecutionReminder> findByApprovalExecutionTaskAndRecipientId(
            ApprovalExecutionTask approvalExecutionTask,
            String recipientId);

    /**
     * Counts reminders by reminder status.
     *
     * @param status reminder status
     * @return reminder count
     */
    long countByStatus(
            ApprovalReminderStatus status);

    /**
     * Counts reminders by reminder channel.
     *
     * @param channel reminder channel
     * @return reminder count
     */
    long countByChannel(
            ApprovalReminderChannel channel);

    /**
     * Counts reminders belonging to an approval execution.
     *
     * @param approvalExecution approval execution
     * @return reminder count
     */
    long countByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Counts reminders belonging to an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return reminder count
     */
    long countByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Counts reminders belonging to an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return reminder count
     */
    long countByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Counts reminders for a recipient.
     *
     * @param recipientId recipient identifier
     * @return reminder count
     */
    long countByRecipientId(
            String recipientId);

    /**
     * Counts active reminders.
     *
     * @param active active flag
     * @return reminder count
     */
    long countByActive(
            boolean active);

    /**
     * Counts reminders by approval execution and status.
     *
     * @param approvalExecution approval execution
     * @param status            reminder status
     * @return reminder count
     */
    long countByApprovalExecutionAndStatus(
            ApprovalExecution approvalExecution,
            ApprovalReminderStatus status);

    /**
     * Counts reminders by execution stage and status.
     *
     * @param approvalExecutionStage execution stage
     * @param status                 reminder status
     * @return reminder count
     */
    long countByApprovalExecutionStageAndStatus(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalReminderStatus status);

    /**
     * Counts reminders by execution task and status.
     *
     * @param approvalExecutionTask execution task
     * @param status                reminder status
     * @return reminder count
     */
    long countByApprovalExecutionTaskAndStatus(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalReminderStatus status);

    /**
     * Counts reminders by approval execution and channel.
     *
     * @param approvalExecution approval execution
     * @param channel           reminder channel
     * @return reminder count
     */
    long countByApprovalExecutionAndChannel(
            ApprovalExecution approvalExecution,
            ApprovalReminderChannel channel);

    /**
     * Counts reminders by execution stage and channel.
     *
     * @param approvalExecutionStage execution stage
     * @param channel                reminder channel
     * @return reminder count
     */
    long countByApprovalExecutionStageAndChannel(
            ApprovalExecutionStage approvalExecutionStage,
            ApprovalReminderChannel channel);

    /**
     * Counts reminders by execution task and channel.
     *
     * @param approvalExecutionTask execution task
     * @param channel               reminder channel
     * @return reminder count
     */
    long countByApprovalExecutionTaskAndChannel(
            ApprovalExecutionTask approvalExecutionTask,
            ApprovalReminderChannel channel);

    /**
     * Returns whether reminders exist for an approval execution.
     *
     * @param approvalExecution approval execution
     * @return true if found
     */
    boolean existsByApprovalExecution(
            ApprovalExecution approvalExecution);

    /**
     * Returns whether reminders exist for an execution stage.
     *
     * @param approvalExecutionStage execution stage
     * @return true if found
     */
    boolean existsByApprovalExecutionStage(
            ApprovalExecutionStage approvalExecutionStage);

    /**
     * Returns whether reminders exist for an execution task.
     *
     * @param approvalExecutionTask execution task
     * @return true if found
     */
    boolean existsByApprovalExecutionTask(
            ApprovalExecutionTask approvalExecutionTask);

    /**
     * Returns whether reminders exist for a recipient.
     *
     * @param recipientId recipient identifier
     * @return true if found
     */
    boolean existsByRecipientId(
            String recipientId);

    /**
     * Returns whether reminders exist with the specified status.
     *
     * @param status reminder status
     * @return true if found
     */
    boolean existsByStatus(
            ApprovalReminderStatus status);

    /**
     * Returns whether reminders exist with the specified channel.
     *
     * @param channel reminder channel
     * @return true if found
     */
    boolean existsByChannel(
            ApprovalReminderChannel channel);

}