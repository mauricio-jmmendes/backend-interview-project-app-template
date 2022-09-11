package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import com.ninjaone.backendinterviewproject.service.ServiceManager;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {

	private final ServiceManager serviceManager;

	public ServiceController(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}


	@PostMapping
	public ResponseEntity<ServiceDTO> save(@Valid @RequestBody ServiceDTO serviceDTO, BindingResult errResult) {
		if (errResult.hasErrors()) {
			throw new InvalidRequestException(errResult);
		}

		ServiceDTO savedDevice = serviceManager.saveEntity(serviceDTO);
		return ResponseEntity.ok(savedDevice);

	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ServiceDTO> findById(@PathVariable("id") Long id) {
		ServiceDTO deviceDTO;
		try {
			deviceDTO = serviceManager.getServiceById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(deviceDTO);
	}

	@GetMapping()
	public ResponseEntity<List<ServiceDTO>> findAll() {
		return ResponseEntity.ok(serviceManager.getAll());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		serviceManager.deleteServiceById(id);
		return ResponseEntity.noContent().build();
	}
}
