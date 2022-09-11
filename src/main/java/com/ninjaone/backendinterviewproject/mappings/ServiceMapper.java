package com.ninjaone.backendinterviewproject.mappings;

import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceMapper {

	ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

	@Mapping(source = "serviceType", target = "type")
	ServiceDTO toDTO(ServiceOrder service);

	@Mapping(source = "type", target = "serviceType")
	ServiceOrder toEntity(ServiceDTO serviceDTO);

	@InheritConfiguration
	void update(ServiceDTO source, @MappingTarget ServiceOrder target);

}