package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;
import com.zallpy.openapproval.approval.event.ApprovalReminderSentEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepApprovedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepDelegatedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepEscalatedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepRejectedEvent;
import com.zallpy.openapproval.approval.event.ApprovalStepTimedOutEvent;
import com.zallpy.openapproval.approval.event.ApprovalWorkflowCancelledEvent;
import com.zallpy.openapproval.approval.event.ApprovalWorkflowCompletedEvent;
import com.zallpy.openapproval.approval.event.ApprovalWorkflowRejectedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ApprovalNotificationTemplateResolver}.
 *
 * <p>
 * Resolves notification content for approval domain events.
 * </p>
 *
 * <p>
 * Future versions will integrate with database-backed or
 * template-engine based notification templates.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class ApprovalNotificationTemplateResolverImpl
        implements ApprovalNotificationTemplateResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalNotification resolve(
            final ApprovalDomainEvent event) {

        ApprovalNotification notification =
                new ApprovalNotification();

        notification.setChannel(
                ApprovalNotificationChannel.EMAIL);

        if (event instanceof ApprovalWorkflowCompletedEvent) {

            notification.setSubject(
                    "Approval Workflow Completed");

            notification.setBody(
                    "The approval workflow has completed successfully.");

        } else if (event instanceof ApprovalWorkflowRejectedEvent) {

            notification.setSubject(
                    "Approval Workflow Rejected");

            notification.setBody(
                    "The approval workflow has been rejected.");

        } else if (event instanceof ApprovalWorkflowCancelledEvent) {

            notification.setSubject(
                    "Approval Workflow Cancelled");

            notification.setBody(
                    "The approval workflow has been cancelled.");

        } else if (event instanceof ApprovalStepApprovedEvent) {

            notification.setSubject(
                    "Approval Step Approved");

            notification.setBody(
                    "An approval step has been approved.");

        } else if (event instanceof ApprovalStepRejectedEvent) {

            notification.setSubject(
                    "Approval Step Rejected");

            notification.setBody(
                    "An approval step has been rejected.");

        } else if (event instanceof ApprovalStepDelegatedEvent) {

            notification.setSubject(
                    "Approval Step Delegated");

            notification.setBody(
                    "An approval step has been delegated.");

        } else if (event instanceof ApprovalStepEscalatedEvent) {

            notification.setSubject(
                    "Approval Step Escalated");

            notification.setBody(
                    "An approval step has been escalated.");

        } else if (event instanceof ApprovalStepTimedOutEvent) {

            notification.setSubject(
                    "Approval Step Timed Out");

            notification.setBody(
                    "An approval step has timed out.");

        } else if (event instanceof ApprovalReminderSentEvent) {

            notification.setSubject(
                    "Approval Reminder");

            notification.setBody(
                    "A reminder has been sent for a pending approval.");

        } else {

            notification.setSubject(
                    "Approval Notification");

            notification.setBody(
                    "An approval event has occurred.");
        }

        return notification;
    }

}