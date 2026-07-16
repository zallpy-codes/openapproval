package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

import java.util.Collection;

/**
 * Resolves notification recipients for approval domain events.
 *
 * <p>
 * Implementations determine who should receive a notification
 * based on the supplied approval domain event.
 * </p>
 *
 * <p>
 * Typical recipients include:
 * <ul>
 *     <li>Assigned approver</li>
 *     <li>Workflow requester</li>
 *     <li>Delegated approver</li>
 *     <li>Escalated approver</li>
 *     <li>Workflow administrator</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalNotificationRecipientResolver {

    /**
     * Resolves the notification recipients.
     *
     * @param event approval domain event
     * @return notification recipients
     */
    Collection<String> resolveRecipients(
            ApprovalDomainEvent event);

}