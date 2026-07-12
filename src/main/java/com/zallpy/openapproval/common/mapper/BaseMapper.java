package com.zallpy.openapproval.common.mapper;

import java.util.List;

/**
 * Generic mapper contract.
 *
 * @param <E> Entity type
 * @param <REQ> Request DTO type
 * @param <RES> Response DTO type
 *
 * @author Zallpy
 * @since 1.0.0
 */
public interface BaseMapper<E, REQ, RES> {

    /**
     * Converts a request DTO to an entity.
     *
     * @param request request DTO
     * @return entity
     */
    E toEntity(REQ request);

    /**
     * Converts an entity to a response DTO.
     *
     * @param entity entity
     * @return response DTO
     */
    RES toResponse(E entity);

    /**
     * Converts entities to response DTOs.
     *
     * @param entities entity collection
     * @return response DTO collection
     */
    List<RES> toResponse(List<E> entities);

    /**
     * Updates an existing entity from a request DTO.
     *
     * @param request request DTO
     * @param entity existing entity
     */
    void updateEntity(REQ request, E entity);

}