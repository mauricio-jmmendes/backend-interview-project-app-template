package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeviceBO {

	private final DeviceMapper deviceMapper;

	private final DeviceRepository deviceRepository;

	public DeviceBO(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
		this.deviceRepository = deviceRepository;
		this.deviceMapper = deviceMapper;
	}

	public Device save(DeviceDTO deviceDTO) {
		return deviceRepository.save(deviceMapper.toEntity(deviceDTO));
	}

	public Device getById(Long id) throws ResourceNotFoundException {
		return deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.DEVICE, "Id", id));
	}

	public void deleteById(Long id) {
		deviceRepository.deleteById(id);
	}

	public List<Device> getAll() {
		return deviceRepository.findAll();
	}

	public Device update(DeviceDTO deviceDTO) throws ResourceNotFoundException {
		Device device = deviceRepository.findById(deviceDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.DEVICE, "Id", deviceDTO.getId()));
		deviceMapper.update(deviceDTO, device);
		return deviceRepository.save(device);
	}

	public DeviceMapper mapper() {
		return deviceMapper;
	}

	public DeviceRepository repository() {
		return deviceRepository;
	}
}
