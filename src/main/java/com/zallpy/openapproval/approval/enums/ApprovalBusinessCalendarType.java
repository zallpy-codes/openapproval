package com.zallpy.openapproval.approval.enums;

/**
 * Calendar used when calculating escalation
 * and reminder schedules.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalBusinessCalendarType {

    /**
     * Calendar days.
     */
    CALENDAR_DAYS,

    /**
     * Working days only.
     */
    BUSINESS_DAYS,

    /**
     * Business hours.
     */
    BUSINESS_HOURS,

    /**
     * Organization-specific calendar.
     */
    CUSTOM
}