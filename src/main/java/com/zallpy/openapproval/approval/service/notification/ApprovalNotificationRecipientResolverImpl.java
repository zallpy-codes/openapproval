package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import com.zallpy.openapproval.approval.event.ApprovalReminderSentEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepApprovedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepDelegatedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepEscalatedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepRejectedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepTimedOutEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Default implementation of {@link ApprovalNotificationRecipientResolver}.
 *
 * <p>
 * Resolves notification recipients from approval domain events.
 * </p>
 *
 * <p>
 * Future versions may integrate with the Identity service
 * to resolve email addresses, phone numbers and notification
 * preferences.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ApprovalNotificationRecipientResolverImpl
        implements ApprovalNotificationRecipientResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> resolveRecipients(
            final ApprovalDomainEvent event) {

        Collection<String> recipients =
                new LinkedHashSet<>();

        if (event == null) {
            return recipients;
        }

        if (event instanceof ApprovalStepApprovedEvent approvedEvent) {

            recipients.add(
                    approvedEvent.getApproverId().toString());

        } else if (event instanceof ApprovalStepRejectedEvent rejectedEvent) {

            recipients.add(
                    rejectedEvent.getApproverId().toString());

        } else if (event instanceof ApprovalStepDelegatedEvent delegatedEvent) {

            recipients.add(
                    delegatedEvent.getDelegatedTo().toString());

        } else if (event instanceof ApprovalStepEscalatedEvent escalatedEvent) {

            recipients.add(
                    escalatedEvent.getEscalatedTo().toString());

        } else if (event instanceof ApprovalStepTimedOutEvent timedOutEvent) {

            recipients.add(
                    timedOutEvent.getApproverId().toString());

        } else if (event instanceof ApprovalReminderSentEvent reminderEvent) {

            recipients.add(
                    reminderEvent.getApproverId().toString());
        }

        log.debug(
                "Resolved {} notification recipient(s).",
                recipients.size());

        return recipients;
    }

}