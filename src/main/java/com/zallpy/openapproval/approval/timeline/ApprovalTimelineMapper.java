package com.zallpy.openapproval.approval.timeline;

import com.zallpy.openapproval.approval.entity.ApprovalAudit;

/**
 * Converts {@link ApprovalAudit} entities into
 * {@link ApprovalTimelineItem} presentation models.
 *
 * <p>
 * Implementations enrich audit records with
 * UI-friendly information such as titles,
 * descriptions, icons, badges and colors.
 * </p>
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface ApprovalTimelineMapper {

    /**
     * Converts an approval audit record into
     * a timeline item.
     *
     * @param audit approval audit
     *
     * @return timeline item
     */
    ApprovalTimelineItem toTimelineItem(
            ApprovalAudit audit);

}