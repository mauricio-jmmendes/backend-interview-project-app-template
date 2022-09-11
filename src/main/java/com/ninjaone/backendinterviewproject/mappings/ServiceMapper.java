package com.ninjaone.backendinterviewproject.mappings;

import com.ninjaone.backendinterviewproject.model.ServiceEntity;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceMapper {

	ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

	@Mapping(source = "serviceType", target = "type")
	ServiceDTO toDTO(ServiceEntity service);

	@Mapping(source = "type", target = "serviceType")
	ServiceEntity toEntity(ServiceDTO serviceDTO);

}