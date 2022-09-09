package com.ninjaone.backendinterviewproject.mappings;

import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

	DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

	@Mapping(source = "deviceType", target = "type")
	DeviceDTO toDTO(Device device);

	@Mapping(source = "type", target = "deviceType")
	Device toEntity(DeviceDTO deviceDTO);


}