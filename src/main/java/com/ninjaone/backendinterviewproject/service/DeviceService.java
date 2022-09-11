package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
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

	private final CustomerRepository customerRepository;

	private final DeviceRepository deviceRepository;

	private final DeviceMapper deviceMapper;

	public DeviceService(CustomerRepository customerRepository, DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
		this.customerRepository = customerRepository;
		this.deviceRepository = deviceRepository;
		this.deviceMapper = deviceMapper;
	}

	public DeviceDTO createEntity(DeviceDTO deviceDTO) {
		Device savedDevice = deviceRepository.save(deviceMapper.toEntity(deviceDTO));
		return deviceMapper.toDTO(savedDevice);
	}

	public DeviceDTO getDeviceById(Long id) throws ResourceNotFoundException {
		Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.DEVICE, "Id", id));
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
		Device storedDevice = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.DEVICE, "Id", id));
		Device updatedDevice = deviceMapper.toEntity(deviceDTO);
		storedDevice.setSystemName(updatedDevice.getSystemName());
		storedDevice.setDeviceType(updatedDevice.getDeviceType());
		deviceRepository.save(storedDevice);
	}
}
