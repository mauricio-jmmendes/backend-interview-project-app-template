package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.ServiceMapper;
import com.ninjaone.backendinterviewproject.model.ServiceEntity;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager {

	private final ServiceRepository serviceRepository;
	private final ServiceMapper serviceMapper;

	public ServiceManager(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
		this.serviceRepository = serviceRepository;
		this.serviceMapper = serviceMapper;
	}

	public ServiceDTO saveEntity(ServiceDTO deviceDTO) {
		ServiceEntity device = serviceMapper.toEntity(deviceDTO);
		serviceRepository.save(device);
		return serviceMapper.toDTO(device);
	}

	public ServiceDTO getServiceById(Long id) throws ResourceNotFoundException {
		ServiceEntity device = serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.SERVICE, "id", id));
		return serviceMapper.toDTO(device);
	}

	public void deleteServiceById(Long id) {
		serviceRepository.deleteById(id);
	}

	public List<ServiceDTO> getAll() {
		return serviceRepository.findAll().stream().map(serviceMapper::toDTO)
														.collect(Collectors.toList());
	}
}
