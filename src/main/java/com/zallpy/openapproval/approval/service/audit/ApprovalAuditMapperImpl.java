package com.zallpy.openapproval.approval.service.audit;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import com.zallpy.openapproval.approval.enums.ApprovalAuditActorType;
import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link ApprovalAuditMapper}.
 *
 * <p>
 * Maps approval domain events into immutable
 * {@link ApprovalAudit} entities.
 * </p>
 *
 * <p>
 * This mapper provides the common audit information.
 * Event-specific enrichment is handled by the audit
 * service before persistence.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
public class ApprovalAuditMapperImpl
        implements ApprovalAuditMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalAudit toEntity(
            final ApprovalDomainEvent event) {

        if (event == null) {
            return null;
        }

        ApprovalAudit audit = new ApprovalAudit();

        audit.setEventType(
                event.getClass().getSimpleName());

        audit.setAction(
                resolveAction(event));

        audit.setOccurredAt(
                event.getOccurredAt());

        audit.setActorType(
                ApprovalAuditActorType.SYSTEM);

        audit.setSystemGenerated(true);

        return audit;
    }

    /**
     * Resolves the audit action from
     * the supplied domain event.
     *
     * @param event approval domain event
     * @return audit action
     */
    protected ApprovalAuditAction resolveAction(
            final ApprovalDomainEvent event) {

        String eventName = event.getClass().getSimpleName();

        return switch (eventName) {

            case "ApprovalCreatedEvent" ->
                ApprovalAuditAction.REQUEST_CREATED;

            case "ApprovalSubmittedEvent" ->
                ApprovalAuditAction.REQUEST_SUBMITTED;

            case "ApprovalApprovedEvent" ->
                ApprovalAuditAction.APPROVED;

            case "ApprovalRejectedEvent" ->
                ApprovalAuditAction.REJECTED;

            case "ApprovalStepDelegatedEvent" ->
                ApprovalAuditAction.DELEGATED;

            case "ApprovalStepEscalatedEvent" ->
                ApprovalAuditAction.ESCALATED;

            case "ApprovalReminderSentEvent" ->
                ApprovalAuditAction.REMINDER_SENT;

            case "ApprovalStepTimedOutEvent" ->
                ApprovalAuditAction.EXECUTION_EXPIRED;

            case "ApprovalWorkflowCompletedEvent" ->
                ApprovalAuditAction.EXECUTION_COMPLETED;

            default ->
                ApprovalAuditAction.SYSTEM_EVENT;
        };
    }

}