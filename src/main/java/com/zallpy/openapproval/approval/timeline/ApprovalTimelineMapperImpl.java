package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;
import com.zallpy.openapproval.approval.enums.ApprovalAuditAction;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link ApprovalTimelineMapper}.
 *
 * <p>
 * Converts immutable {@link ApprovalAudit} records into
 * presentation-friendly {@link ApprovalTimelineItem}
 * instances for timeline rendering.
 * </p>
 *
 * <p>
 * This mapper performs presentation mapping only.
 * It never modifies domain entities.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@Component
public class ApprovalTimelineMapperImpl
        implements ApprovalTimelineMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public ApprovalTimelineItem toTimelineItem(
            final ApprovalAudit audit) {

        if (audit == null) {
            return null;
        }

        ApprovalTimelineItem item = new ApprovalTimelineItem();

        item.setAuditId(audit.getId());
        item.setOccurredAt(audit.getOccurredAt());

        item.setAction(audit.getAction());

        item.setActorId(audit.getPerformedBy());
        item.setActorName(audit.getPerformedByName());
        item.setActorType(audit.getActorType());

        item.setSystemGenerated(audit.isSystemGenerated());

        item.setTitle(resolveTitle(audit));
        item.setDescription(resolveDescription(audit));

        item.setBadge(resolveBadge(audit.getAction()));
        item.setIcon(resolveIcon(audit.getAction()));
        item.setColor(resolveColor(audit.getAction()));

        item.setCurrent(false);

        return item;
    }

    /**
     * Resolves the timeline title.
     *
     * @param audit approval audit
     * @return title
     */
    private String resolveTitle(
            final ApprovalAudit audit) {

        if (audit.getEventType() != null
                && !audit.getEventType().isBlank()) {
            return audit.getEventType();
        }

        return switch (audit.getAction()) {

            case REQUEST_CREATED ->
                    "Approval Request Created";

            case REQUEST_SUBMITTED ->
                    "Approval Request Submitted";

            case EXECUTION_STARTED ->
                    "Workflow Execution Started";

            case STAGE_STARTED ->
                    "Approval Stage Started";

            case STAGE_COMPLETED ->
                    "Approval Stage Completed";

            case TASK_CREATED ->
                    "Approval Task Created";

            case TASK_ASSIGNED ->
                    "Approval Task Assigned";

            case TASK_COMPLETED ->
                    "Approval Task Completed";

            case APPROVED ->
                    "Approval Granted";

            case REJECTED ->
                    "Approval Rejected";

            case RETURNED ->
                    "Request Returned";

            case CANCELLED ->
                    "Approval Cancelled";

            case REMINDER_SENT ->
                    "Reminder Sent";

            case ESCALATED ->
                    "Approval Escalated";

            case DELEGATED ->
                    "Approval Delegated";

            case COMMENT_ADDED ->
                    "Comment Added";

            case ATTACHMENT_ADDED ->
                    "Attachment Added";

            case EXECUTION_COMPLETED ->
                    "Workflow Completed";

            case EXECUTION_EXPIRED ->
                    "Workflow Expired";

            case SYSTEM_EVENT ->
                    "System Event";
        };
    }

    /**
     * Resolves the timeline description.
     *
     * @param audit approval audit
     * @return description
     */
    private String resolveDescription(
            final ApprovalAudit audit) {

        if (audit.getComment() != null
                && !audit.getComment().isBlank()) {
            return audit.getComment();
        }

        return resolveTitle(audit);
    }

    /**
     * Resolves a UI badge.
     *
     * @param action audit action
     * @return badge
     */
    private String resolveBadge(
            final ApprovalAuditAction action) {

        if (action == null) {
            return "";
        }

        return action.name().replace('_', ' ');
    }

    /**
     * Resolves an icon name.
     *
     * @param action audit action
     * @return icon
     */
    private String resolveIcon(
            final ApprovalAuditAction action) {

        if (action == null) {
            return "history";
        }

        return switch (action) {

            case REQUEST_CREATED -> "add_circle";
            case REQUEST_SUBMITTED -> "send";
            case EXECUTION_STARTED -> "play_arrow";
            case STAGE_STARTED -> "flag";
            case STAGE_COMPLETED -> "check_circle";
            case TASK_CREATED -> "assignment";
            case TASK_ASSIGNED -> "person";
            case TASK_COMPLETED -> "task_alt";
            case APPROVED -> "thumb_up";
            case REJECTED -> "cancel";
            case RETURNED -> "reply";
            case CANCELLED -> "block";
            case REMINDER_SENT -> "notifications";
            case ESCALATED -> "north";
            case DELEGATED -> "swap_horiz";
            case COMMENT_ADDED -> "comment";
            case ATTACHMENT_ADDED -> "attach_file";
            case EXECUTION_COMPLETED -> "verified";
            case EXECUTION_EXPIRED -> "schedule";
            case SYSTEM_EVENT -> "memory";
        };
    }

    /**
     * Resolves a display color.
     *
     * @param action audit action
     * @return color
     */
    private String resolveColor(
            final ApprovalAuditAction action) {

        if (action == null) {
            return "gray";
        }

        return switch (action) {

            case APPROVED,
                    TASK_COMPLETED,
                    STAGE_COMPLETED,
                    EXECUTION_COMPLETED -> "green";

            case REJECTED,
                    CANCELLED -> "red";

            case ESCALATED -> "orange";

            case DELEGATED -> "purple";

            case REMINDER_SENT -> "yellow";

            case SYSTEM_EVENT -> "gray";

            default -> "blue";
        };
    }

}