package com.zallpy.openapproval.approval.enums;

/**
 * Identifies the source that created an approval request.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalRequestSource {

    WEB,

    MOBILE,

    API,

    SYSTEM,

    WORKFLOW,

    BATCH,

    SCHEDULER,

    IMPORT,

    EMAIL,

    WEBHOOK,

    EXTERNAL_SYSTEM
}