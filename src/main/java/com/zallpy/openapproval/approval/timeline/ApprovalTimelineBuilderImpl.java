package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflow;
import com.zallpy.openapproval.approval.entity.ApprovalWorkflowRepository;
import com.zallpy.openapproval.approval.service.audit.ApprovalAuditHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Default implementation of {@link ApprovalTimelineBuilder}.
 *
 * <p>
 * Responsible for constructing a complete
 * {@link ApprovalTimeline} from an approval
 * workflow and its audit history.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 * <li>Load workflow information</li>
 * <li>Load audit history</li>
 * <li>Sort audit events chronologically</li>
 * <li>Populate workflow metadata</li>
 * <li>Calculate workflow duration</li>
 * <li>Build timeline items</li>
 * </ul>
 * </p>
 *
 * <p>
 * This service does not perform entity-to-DTO
 * conversion directly. That responsibility belongs
 * to {@link ApprovalTimelineMapper}.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalTimelineBuilderImpl
                implements ApprovalTimelineBuilder {

        /**
         * Approval workflow repository.
         */
        private final ApprovalWorkflowRepository workflowRepository;

        /**
         * Audit history service.
         */
        private final ApprovalAuditHistoryService auditHistoryService;

        /**
         * Timeline mapper.
         */
        private final ApprovalTimelineMapper timelineMapper;

        /**
         * {@inheritDoc}
         */
        @Override
        public ApprovalTimeline build(
                        final UUID workflowId) {

                ApprovalWorkflow workflow = workflowRepository.findById(workflowId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Approval workflow not found: "
                                                                + workflowId));

                List<ApprovalAudit> history = auditHistoryService.findWorkflowHistory(workflowId);

                return build(workflow, history);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ApprovalTimeline build(
                        final ApprovalWorkflow workflow,
                        final List<ApprovalAudit> auditHistory) {

                ApprovalTimeline timeline = new ApprovalTimeline();

                if (workflow == null) {
                        return timeline;
                }

                timeline.setWorkflowId(workflow.getId());

                if (workflow.getApprovalRequest() != null) {
                        timeline.setRequestId(
                                        workflow.getApprovalRequest().getId());
                }

                timeline.setCurrentStatus(
                                workflow.getStatus());

                if (auditHistory == null || auditHistory.isEmpty()) {
                        return timeline;
                }

                auditHistory.sort(
                                Comparator.comparing(
                                                ApprovalAudit::getOccurredAt));

                timeline.setStartedAt(
                                auditHistory.get(0).getOccurredAt());

                timeline.setCompletedAt(
                                auditHistory.get(
                                                auditHistory.size() - 1)
                                                .getOccurredAt());

                timeline.setCompleted(
                                workflow.isCompleted());

                LocalDateTime started = timeline.getStartedAt();

                LocalDateTime completed = timeline.isCompleted()
                                ? timeline.getCompletedAt()
                                : LocalDateTime.now();

                if (started != null && completed != null) {

                        timeline.setDuration(
                                        Duration.between(
                                                        started,
                                                        completed));
                }

                timeline.setTotalEvents(
                                auditHistory.size());

                timeline.setPendingSteps(
                                workflow.getPendingSteps());

                timeline.setApprovedSteps(
                                workflow.getApprovedSteps());

                timeline.setRejectedSteps(
                                workflow.getRejectedSteps());

                timeline.setDelegatedSteps(
                                workflow.getDelegatedSteps());

                timeline.setEscalatedSteps(
                                workflow.getEscalatedSteps());

                timeline.setTotalSteps(
                                workflow.getTotalSteps());

                timeline.setCancelled(
                                workflow.isCancelled());

                timeline.setRejected(
                                workflow.isRejected());

                timeline.setWorkflowReference(
                                workflow.getWorkflowReference());

               int sequence = 1;

for (ApprovalAudit audit : auditHistory) {

    ApprovalTimelineItem item =
            timelineMapper.toTimelineItem(audit);

    item.setSequence(sequence++);

    timeline.addItem(item);
}

                return timeline;
        }

}