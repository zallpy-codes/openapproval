package com.zallpy.openapproval.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * Base auditable entity for OpenApproval.
 *
 * <p>
 * Extends {@link BaseEntity} by providing automatic auditing fields that are
 * populated by Spring Data JPA Auditing.
 * </p>
 *
 * <ul>
 *     <li>Created date</li>
 *     <li>Created by</li>
 *     <li>Last modified date</li>
 *     <li>Last modified by</li>
 * </ul>
 *
 * All persistent domain entities should extend this class.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity {

    /**
     * Timestamp when the entity was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * User that created the entity.
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private UUID createdBy;

    /**
     * Timestamp when the entity was last modified.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * User that last modified the entity.
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private UUID updatedBy;

    /**
     * Default constructor.
     */
    protected AuditableEntity() {
        super();
    }

    /**
     * Returns the creation timestamp.
     *
     * @return creation timestamp
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the creator identifier.
     *
     * @return creator UUID
     */
    public UUID getCreatedBy() {
        return createdBy;
    }

    /**
     * Returns the last modification timestamp.
     *
     * @return last modification timestamp
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns the last modifier identifier.
     *
     * @return last modifier UUID
     */
    public UUID getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt creation timestamp
     */
    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets the creator identifier.
     *
     * @param createdBy creator UUID
     */
    public void setCreatedBy(final UUID createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the last modification timestamp.
     *
     * @param updatedAt last modification timestamp
     */
    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Sets the last modifier identifier.
     *
     * @param updatedBy last modifier UUID
     */
    public void setUpdatedBy(final UUID updatedBy) {
        this.updatedBy = updatedBy;
    }
}