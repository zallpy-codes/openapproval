package com.zallpy.openapproval.approval.enums;

/**
 * Defines the type of an approval stage.
 *
 * @author Zallpy
 * @since 1.0.0
 */
public enum ApprovalStageType {

    /**
     * Standard approval stage.
     */
    STANDARD,

    /**
     * First approval stage.
     */
    INITIAL,

    /**
     * Final approval stage.
     */
    FINAL,

    /**
     * Review-only stage.
     */
    REVIEW,

    /**
     * Verification stage.
     */
    VERIFICATION,

    /**
     * Compliance stage.
     */
    COMPLIANCE,

    /**
     * Risk assessment stage.
     */
    RISK,

    /**
     * Financial approval stage.
     */
    FINANCIAL,

    /**
     * Executive approval stage.
     */
    EXECUTIVE,

    /**
     * Automatic system approval.
     */
    AUTOMATIC
}