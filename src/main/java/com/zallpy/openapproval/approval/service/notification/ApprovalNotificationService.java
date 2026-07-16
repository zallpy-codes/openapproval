package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

/**
 * Coordinates notification delivery for approval domain events.
 *
 * <p>
 * This service is the primary entry point into the notification
 * subsystem. Event listeners delegate notification responsibilities
 * to this service instead of interacting directly with channels
 * such as email, SMS or webhooks.
 * </p>
 *
 * <p>
 * Implementations are responsible for:
 * <ul>
 *     <li>Resolving recipients</li>
 *     <li>Selecting notification templates</li>
 *     <li>Dispatching notifications</li>
 *     <li>Applying notification policies</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalNotificationService {

    /**
     * Sends notifications for the supplied approval domain event.
     *
     * <p>
     * The implementation determines the notification recipients,
     * template and delivery channels based on the event type.
     * </p>
     *
     * @param event approval domain event
     */
    void notify(ApprovalDomainEvent event);

}