package com.zallpy.openapproval.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Global MapStruct configuration for OpenApproval.
 *
 * <p>
 * All MapStruct mappers should reference this configuration:
 *
 * <pre>
 * &#64;Mapper(config = MapperConfiguration.class)
 * </pre>
 *
 * @author Zallpy
 * @since 1.0.0
 */
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MapperConfiguration {
}