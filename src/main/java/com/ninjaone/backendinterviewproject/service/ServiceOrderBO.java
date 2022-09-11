package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.ServiceMapper;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServiceOrderBO {

	private final ServiceMapper serviceMapper;

	private final ServiceRepository serviceRepository;

	public ServiceOrderBO(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
		this.serviceRepository = serviceRepository;
		this.serviceMapper = serviceMapper;
	}

	public ServiceOrder save(ServiceDTO serviceDTO) {
		ServiceOrder serviceOrder = serviceMapper.toEntity(serviceDTO);
		serviceRepository.save(serviceOrder);
		return serviceOrder;
	}

	public ServiceOrder getById(Long id) throws ResourceNotFoundException {
		return serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.SERVICE, "id", id));
	}

	public void deleteById(Long id) {
		serviceRepository.deleteById(id);
	}

	public List<ServiceOrder> getAll() {
		return serviceRepository.findAll();
	}

	public ServiceMapper mapper() {
		return serviceMapper;
	}

	public ServiceRepository repository() {
		return serviceRepository;
	}
}
