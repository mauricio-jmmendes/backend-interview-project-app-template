package com.ninjaone.backendinterviewproject.mappings;

import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceOrderDTO;
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
	@Mapping(source = "entity.device.id", target = "deviceId")
	ServiceOrderDTO toDTO(ServiceOrder entity);

	@Mapping(source = "type", target = "serviceType")
	ServiceOrder toEntity(ServiceOrderDTO serviceOrderDTO);

	@InheritConfiguration
	void update(ServiceOrderDTO source, @MappingTarget ServiceOrder target);

}