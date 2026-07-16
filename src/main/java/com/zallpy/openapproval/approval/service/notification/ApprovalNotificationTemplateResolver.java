package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

/**
 * Resolves notification templates for approval domain events.
 *
 * <p>
 * Implementations construct an {@link ApprovalNotification}
 * by selecting the appropriate template and populating its
 * subject, body and template variables.
 * </p>
 *
 * <p>
 * Template resolution is independent of notification delivery.
 * The resulting notification is passed to the dispatcher for
 * channel-specific processing.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalNotificationTemplateResolver {

    /**
     * Resolves the notification template for the supplied event.
     *
     * @param event approval domain event
     * @return notification ready for recipient assignment
     */
    ApprovalNotification resolve(
            ApprovalDomainEvent event);

}