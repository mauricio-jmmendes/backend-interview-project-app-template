package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.controller.DeviceDTO;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

	public DeviceDTO getDeviceById(String id) throws DeviceNotFoundException {
		Device device = deviceRepository.findById(id).orElseThrow(DeviceNotFoundException::new);
		return deviceMapper.toDTO(device);
	}

	public void deleteDeviceById(String id) {
		deviceRepository.deleteById(id);
	}

	public List<DeviceDTO> getAll() {
		return deviceRepository.findAll().stream().map(deviceMapper::toDTO).collect(Collectors.toList());
	}

	public void mergeEntity(String id, DeviceDTO deviceDTO) throws DeviceNotFoundException {
		Device device = deviceRepository.findById(id).orElseThrow(DeviceNotFoundException::new);
		Device toBeUpdated = deviceMapper.toEntity(deviceDTO);
		device.setSystemName(toBeUpdated.getSystemName());
		device.setDeviceType(toBeUpdated.getDeviceType());
		deviceRepository.save(device);
	}
}
