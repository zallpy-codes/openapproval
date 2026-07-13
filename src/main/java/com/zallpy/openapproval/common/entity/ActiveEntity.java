package com.zallpy.openapproval.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Base class for entities that support activation and deactivation.
 *
 * <p>
 * Extends {@link BaseEntity} by adding an active flag together with
 * helper methods used consistently throughout OpenApproval.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class ActiveEntity extends BaseEntity {

    /**
     * Indicates whether this entity is active.
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Returns whether the entity is active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Updates the active state.
     *
     * @param active active flag
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns whether the entity is enabled.
     *
     * @return true if active
     */
    public boolean isEnabled() {
        return active;
    }

    /**
     * Returns whether the entity is disabled.
     *
     * @return true if inactive
     */
    public boolean isDisabled() {
        return !active;
    }

    /**
     * Activates the entity.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivates the entity.
     */
    public void deactivate() {
        this.active = false;
    }
}