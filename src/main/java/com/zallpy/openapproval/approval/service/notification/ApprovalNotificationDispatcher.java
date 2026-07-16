package com.zallpy.openapproval.approval.service.notification;

import com.zallpy.openapproval.approval.event.ApprovalDomainEvent;

import java.util.Collection;

/**
 * Dispatches approval notifications to one or more
 * notification channels.
 *
 * <p>
 * The dispatcher is responsible for delivering notifications
 * after recipients and templates have been resolved.
 * </p>
 *
 * <p>
 * Implementations may support multiple delivery channels such as:
 * <ul>
 * <li>Email</li>
 * <li>SMS</li>
 * <li>In-App Notification</li>
 * <li>Webhook</li>
 * <li>Microsoft Teams</li>
 * <li>Slack</li>
 * </ul>
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalNotificationDispatcher {

    /**
     * Dispatches a notification.
     *
     * @param notification notification to dispatch
     */
    void dispatch(
            ApprovalNotification notification);

}