package com.zallpy.openapproval.approval.service.reminder;

import com.zallpy.openapproval.approval.entity.ApprovalStepRepository;
import com.zallpy.openapproval.approval.entity.ApprovalStepSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default reminder scheduler.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalReminderSchedulerImpl
        implements ApprovalReminderScheduler {

    private final ApprovalStepRepository
            approvalStepRepository;

    private final ApprovalReminderService
            reminderService;

    @Override
    @Scheduled(
            fixedDelayString =
                    "${openapproval.reminder.interval:300000}")
    public void processReminders() {

        log.debug("Starting reminder scheduler...");

        approvalStepRepository
                .findAll(
                        ApprovalStepSpecification.requiresReminder())
                .forEach(
                        reminderService::sendReminder);

        log.debug("Reminder scheduler completed.");
    }

}