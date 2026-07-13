package com.zallpy.openapproval.approval.policy;

import com.zallpy.openapproval.approval.enums.ApprovalScheduleAction;
import com.zallpy.openapproval.approval.enums.ApprovalScheduleType;
import com.zallpy.openapproval.common.entity.ActiveEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Defines scheduled lifecycle actions for an {@link ApprovalPolicyVersion}.
 *
 * <p>
 * This entity enables automatic policy lifecycle management by allowing
 * scheduled activation, retirement, publication and archival of policy
 * versions without manual intervention.
 *
 * <p>
 * Examples:
 * 
 * <pre>
 * 1. Activate Version 2.0 on 01-Jan-2027 00:00 UTC
 * 2. Retire Version 1.5 on 31-Dec-2026 23:59 UTC
 * 3. Publish Version every Sunday at 02:00 AM
 * </pre>
 *
 * <p>
 * The Scheduler Engine periodically evaluates active schedules and
 * executes the configured lifecycle action.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Entity
@Table(name = "approval_policy_schedules", indexes = {
        @Index(name = "idx_schedule_version", columnList = "policy_version_id"),
        @Index(name = "idx_schedule_action", columnList = "schedule_action"),
        @Index(name = "idx_schedule_type", columnList = "schedule_type"),
        @Index(name = "idx_schedule_execution", columnList = "next_execution_time"),
        @Index(name = "idx_schedule_active", columnList = "active")
})
public class ApprovalPolicySchedule extends ActiveEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Target policy version.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policy_version_id", nullable = false)
    private ApprovalPolicyVersion approvalPolicyVersion;

    /**
     * Unique schedule code.
     */
    @NotBlank
    @Size(max = 80)
    @Column(name = "schedule_code", nullable = false, unique = true, length = 80)
    private String scheduleCode;

    /**
     * Schedule name.
     */
    @NotBlank
    @Size(max = 150)
    @Column(name = "schedule_name", nullable = false, length = 150)
    private String scheduleName;

    /**
     * Optional description.
     */
    @Size(max = 2000)
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * Scheduling strategy.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false, length = 30)
    private ApprovalScheduleType scheduleType = ApprovalScheduleType.ONE_TIME;

    /**
     * Lifecycle action to execute.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_action", nullable = false, length = 40)
    private ApprovalScheduleAction scheduleAction;

    /**
     * Scheduled execution date/time.
     */
    @FutureOrPresent
    @Column(name = "scheduled_datetime")
    private LocalDateTime scheduledDateTime;

    /**
     * Cron expression used for recurring schedules.
     */
    @Size(max = 120)
    @Column(name = "cron_expression", length = 120)
    private String cronExpression;

    /**
     * Time zone identifier.
     *
     * Examples:
     * Africa/Lagos
     * UTC
     * Europe/London
     */
    @Size(max = 80)
    @Column(name = "time_zone", length = 80)
    private String timeZone = "UTC";

    /**
     * Indicates whether this schedule repeats.
     */
    @Column(name = "recurring", nullable = false)
    private boolean recurring = false;

    /**
     * Next execution time.
     */
    @Column(name = "next_execution_time")
    private LocalDateTime nextExecutionTime;

    /**
     * Last successful execution time.
     */
    @Column(name = "last_execution_time")
    private LocalDateTime lastExecutionTime;

    /**
     * Indicates whether this schedule
     * has already executed.
     */
    @Column(name = "executed", nullable = false)
    private boolean executed = false;

    /**
     * Number of successful executions.
     */
    @Column(name = "execution_count", nullable = false)
    private Integer executionCount = 0;

    @Column(name = "scheduler_node", length = 100)
    private String schedulerNode;

    @Column(name = "lock_acquired_at")
    private LocalDateTime lockAcquiredAt;

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof ApprovalPolicySchedule other)) {
            return false;
        }

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Default constructor.
     */
    public ApprovalPolicySchedule() {
    }

    /**
     * Returns whether this is a one-time schedule.
     *
     * @return true if one-time
     */
    public boolean isOneTimeSchedule() {
        return scheduleType == ApprovalScheduleType.ONE_TIME;
    }

    /**
     * Returns whether this is a recurring schedule.
     *
     * @return true if recurring
     */
    public boolean isRecurringSchedule() {
        return scheduleType == ApprovalScheduleType.RECURRING;
    }

    /**
     * Returns whether this schedule uses a CRON expression.
     *
     * @return true if CRON schedule
     */
    public boolean isCronSchedule() {
        return scheduleType == ApprovalScheduleType.CRON;
    }

    /**
     * Returns whether the configured action activates
     * a policy version.
     *
     * @return true if activation action
     */
    public boolean isActivationSchedule() {
        return scheduleAction == ApprovalScheduleAction.ACTIVATE_VERSION;
    }

    /**
     * Returns whether the configured action retires
     * a policy version.
     *
     * @return true if retirement action
     */
    public boolean isRetirementSchedule() {
        return scheduleAction == ApprovalScheduleAction.RETIRE_VERSION;
    }

    /**
     * Returns whether this schedule has already executed.
     *
     * @return true if executed
     */
    public boolean hasExecuted() {
        return executed;
    }

    /**
     * Returns whether a next execution has been scheduled.
     *
     * @return true if next execution exists
     */
    public boolean hasNextExecution() {
        return nextExecutionTime != null;
    }

    /**
     * Returns whether this schedule is due for execution.
     *
     * @return true if due
     */
    public boolean isDueForExecution() {

        if (!isEnabled()) {
            return false;
        }

        if (executed && isOneTimeSchedule()) {
            return false;
        }

        if (nextExecutionTime == null) {
            return false;
        }

        return !LocalDateTime.now().isBefore(nextExecutionTime);
    }

    /**
     * Marks this schedule as executed.
     */
    public void markExecuted() {
        this.executed = true;
        this.lastExecutionTime = LocalDateTime.now();
        this.executionCount++;
    }

    /**
     * Resets execution state.
     */
    public void resetExecution() {
        this.executed = false;
        this.lastExecutionTime = null;
        this.executionCount = 0;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public ApprovalPolicyVersion getApprovalPolicyVersion() {
        return approvalPolicyVersion;
    }

    public void setApprovalPolicyVersion(ApprovalPolicyVersion approvalPolicyVersion) {
        this.approvalPolicyVersion = approvalPolicyVersion;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        this.scheduleCode = scheduleCode;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ApprovalScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    public ApprovalScheduleAction getScheduleAction() {
        return scheduleAction;
    }

    public void setScheduleAction(ApprovalScheduleAction scheduleAction) {
        this.scheduleAction = scheduleAction;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public LocalDateTime getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(LocalDateTime nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(LocalDateTime lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Integer getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(Integer executionCount) {
        this.executionCount = executionCount;
    }

    /**
     * Validates the schedule configuration.
     *
     * @throws IllegalStateException if the schedule configuration is invalid
     */
    public void validateConfiguration() {

        if (approvalPolicyVersion == null) {
            throw new IllegalStateException(
                    "Approval policy version is required.");
        }

        if (scheduleCode == null || scheduleCode.isBlank()) {
            throw new IllegalStateException(
                    "Schedule code is required.");
        }

        if (scheduleName == null || scheduleName.isBlank()) {
            throw new IllegalStateException(
                    "Schedule name is required.");
        }

        if (scheduleType == null) {
            throw new IllegalStateException(
                    "Schedule type is required.");
        }

        if (scheduleAction == null) {
            throw new IllegalStateException(
                    "Schedule action is required.");
        }

        switch (scheduleType) {

            case ONE_TIME -> {

                if (scheduledDateTime == null) {
                    throw new IllegalStateException(
                            "Scheduled date/time is required for a one-time schedule.");
                }
            }

            case CRON -> {

                if (cronExpression == null || cronExpression.isBlank()) {
                    throw new IllegalStateException(
                            "Cron expression is required for CRON schedules.");
                }
            }

            case RECURRING -> {

                if (nextExecutionTime == null) {
                    throw new IllegalStateException(
                            "Next execution time is required for recurring schedules.");
                }
            }
        }

        if (executionCount == null || executionCount < 0) {
            throw new IllegalStateException(
                    "Execution count cannot be negative.");
        }
    }

    /**
     * Returns whether this schedule
     * can be executed.
     *
     * @return true if executable
     */
    public boolean isExecutable() {
        return isEnabled() && isDueForExecution();
    }

    /**
     * Returns whether this schedule
     * has a CRON expression.
     *
     * @return true if configured
     */
    public boolean hasCronExpression() {
        return cronExpression != null
                && !cronExpression.isBlank();
    }

    /**
     * Returns whether this schedule
     * has a scheduled execution date.
     *
     * @return true if configured
     */
    public boolean hasScheduledDateTime() {
        return scheduledDateTime != null;
    }

    /**
     * Returns whether this schedule
     * has been executed at least once.
     *
     * @return true if execution count is greater than zero
     */
    public boolean hasExecutionHistory() {
        return executionCount != null && executionCount > 0;
    }

    @Override
    public String toString() {
        return "ApprovalPolicySchedule{" +
                "id=" + getId() +
                ", scheduleCode='" + scheduleCode + '\'' +
                ", scheduleName='" + scheduleName + '\'' +
                ", scheduleType=" + scheduleType +
                ", scheduleAction=" + scheduleAction +
                ", recurring=" + recurring +
                ", executed=" + executed +
                ", executionCount=" + executionCount +
                ", active=" + isActive() +
                '}';
    }
}