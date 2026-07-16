package com.zallpy.openapproval.approval.service.timeout;

import com.zallpy.openapproval.approval.entity.ApprovalStepRepository;
import com.zallpy.openapproval.approval.entity.ApprovalStepSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalTimeoutScheduler}.
 *
 * <p>
 * Periodically scans approval steps requiring timeout
 * processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalTimeoutSchedulerImpl
        implements ApprovalTimeoutScheduler {

    /**
     * Approval step repository.
     */
    private final ApprovalStepRepository approvalStepRepository;

    /**
     * Timeout service.
     */
    private final ApprovalTimeoutService timeoutService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(fixedDelayString = "${openapproval.timeout.interval:300000}")
    public void processTimeouts() {

        log.debug(
                "Starting approval timeout scheduler.");

        approvalStepRepository
                .findAll(
                        ApprovalStepSpecification.requiresTimeout())
                .forEach(
                        timeoutService::timeout);

        log.debug(
                "Approval timeout scheduler completed.");
    }

}