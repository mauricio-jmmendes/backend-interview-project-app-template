package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	private final DeviceService deviceService;

	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	private static boolean isValid(DeviceDTO deviceDTO) {
		return !StringUtils.isBlank(deviceDTO.getType()) && !StringUtils.isBlank(
				deviceDTO.getSystemName());
	}

	@PostMapping
	public ResponseEntity<DeviceDTO> save(@RequestBody DeviceDTO deviceDTO) {
		if (isValid(deviceDTO)) {
			DeviceDTO savedDevice = deviceService.saveEntity(deviceDTO);
			return ResponseEntity.ok(savedDevice);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable("id") Long id) {
		DeviceDTO deviceDTO;
		try {
			deviceDTO = deviceService.getDeviceById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(deviceDTO);
	}

	@GetMapping()
	public ResponseEntity<List<DeviceDTO>> findAll() {
		return ResponseEntity.ok(deviceService.getAll());
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<String> updateDevice(@PathVariable("id") Long id,
																						 @RequestBody DeviceDTO deviceDTO) {
		try {
			deviceService.mergeEntity(id, deviceDTO);
			return ResponseEntity.ok("");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		deviceService.deleteDeviceById(id);
		return ResponseEntity.noContent().build();
	}
}
