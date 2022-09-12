package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.ServiceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.ServiceMapper;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceOrderDTO;
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

	public ServiceOrder save(ServiceOrderDTO serviceOrderDTO) {
		ServiceOrder serviceOrder = serviceMapper.toEntity(serviceOrderDTO);
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

	public List<ServiceOrder> findAllByDeviceIdIn(List<Long> deviceIdList) {
		return serviceRepository.findAllByDeviceIdIn(deviceIdList);
	}

	public ServiceOrder update(ServiceOrderDTO serviceOrderDTO) throws ResourceNotFoundException {
		ServiceOrder service = serviceRepository.findById(serviceOrderDTO.getId())
																						.orElseThrow(() -> new ResourceNotFoundException(AppConstants.SERVICE, "Id", serviceOrderDTO.getId()));
		serviceMapper.update(serviceOrderDTO, service);
		return serviceRepository.save(service);
	}
}
