package com.zallpy.openapproval.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Base entity for all persistent domain objects in OpenApproval.
 *
 * <p>
 * Provides:
 * <ul>
 *     <li>UUID primary key</li>
 *     <li>Optimistic locking support</li>
 * </ul>
 *
 * <p>
 * All entities should extend {@link AuditableEntity}.
 *
 * @author Zallpy
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    /**
     * Optimistic locking version.
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    /**
     * Default constructor.
     */
    protected BaseEntity() {
    }

    /**
     * Returns the entity identifier.
     *
     * @return entity UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the entity version.
     *
     * @return optimistic lock version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Indicates whether the entity has been persisted.
     *
     * @return {@code true} if the entity has an identifier
     */
    public boolean isPersisted() {
        return id != null;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BaseEntity that = (BaseEntity) object;

        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }

}