package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.service.ServiceOrderBO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

	private final ServiceOrderBO serviceOrderBO;

	public ServiceController(ServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ServiceDTO> findById(@PathVariable("id") Long id) {
		try {
			ServiceOrder serviceOrder = serviceOrderBO.getById(id);
			return ResponseEntity.ok(serviceOrderBO.mapper().toDTO(serviceOrder));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping()
	public ResponseEntity<List<ServiceDTO>> findAll() {
		return ResponseEntity.ok(serviceOrderBO.getAll().stream().map(serviceOrderBO.mapper()::toDTO).collect(Collectors.toList()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		serviceOrderBO.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
