package com.zallpy.openapproval.approval.service.escalation;

import com.zallpy.openapproval.approval.entity.ApprovalStepRepository;
import com.zallpy.openapproval.approval.entity.ApprovalStepSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalEscalationScheduler}.
 *
 * <p>
 * Periodically scans approval steps requiring escalation.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalEscalationSchedulerImpl
        implements ApprovalEscalationScheduler {

    /**
     * Approval step repository.
     */
    private final ApprovalStepRepository approvalStepRepository;

    /**
     * Escalation service.
     */
    private final ApprovalEscalationService escalationService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(fixedDelayString = "${openapproval.escalation.interval:300000}")
    public void processEscalations() {

        log.debug(
                "Starting approval escalation scheduler.");

        approvalStepRepository
                .findAll(
                        ApprovalStepSpecification.requiresEscalation())
                .forEach(
                        escalationService::escalate);

        log.debug(
                "Approval escalation scheduler completed.");
    }

}