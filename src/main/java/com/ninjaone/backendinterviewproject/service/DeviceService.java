package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

	private final DeviceRepository deviceRepository;
	private final DeviceMapper deviceMapper;

	public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
		this.deviceRepository = deviceRepository;
		this.deviceMapper = deviceMapper;
	}

	public DeviceDTO saveEntity(DeviceDTO deviceDTO) {
		Device device = deviceMapper.toEntity(deviceDTO);
		deviceRepository.save(device);
		return deviceMapper.toDTO(device);
	}

	public DeviceDTO getDeviceById(Long id) throws ResourceNotFoundException {
		Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device", "Id", id));
		return deviceMapper.toDTO(device);
	}

	public void deleteDeviceById(Long id) {
		deviceRepository.deleteById(id);
	}

	public List<DeviceDTO> getAll() {
		return deviceRepository.findAll().stream().map(deviceMapper::toDTO)
													 .collect(Collectors.toList());
	}

	public void mergeEntity(Long id, DeviceDTO deviceDTO) throws ResourceNotFoundException {
		Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device", "Id", id));
		Device toBeUpdated = deviceMapper.toEntity(deviceDTO);
		device.setSystemName(toBeUpdated.getSystemName());
		device.setDeviceType(toBeUpdated.getDeviceType());
		deviceRepository.save(device);
	}
}
