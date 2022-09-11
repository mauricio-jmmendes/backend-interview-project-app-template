package com.ninjaone.backendinterviewproject.mappings;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = ServiceMapper.class)
public interface DeviceMapper {

	DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

	@Mapping(source = "deviceType", target = "type")
	@Mapping(source = "services", target = "purchasedServices")
	DeviceDTO toDTO(Device device);

	@Mapping(source = "type", target = "deviceType")
	@Mapping(source = "purchasedServices", target = "services")
	Device toEntity(DeviceDTO deviceDTO);

	@InheritConfiguration
	void update(DeviceDTO source, @MappingTarget Device target);

}