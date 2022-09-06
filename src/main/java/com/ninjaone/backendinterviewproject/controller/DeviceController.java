package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.service.DeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
	private final DeviceService deviceService;

	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	private static boolean isValid(DeviceDTO deviceDTO) {
		return !StringUtils.isBlank(deviceDTO.getType()) && !StringUtils.isBlank(deviceDTO.getSystemName());
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody DeviceDTO deviceDTO) {
		if (isValid(deviceDTO)) {
			DeviceDTO savedDevice = deviceService.saveEntity(deviceDTO);
			return ResponseEntity.ok(savedDevice.getId());
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable("id") String id) {
		DeviceDTO deviceDTO;
		try {
			deviceDTO = deviceService.getDeviceById(id);
		} catch (DeviceNotFoundException e) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(deviceDTO);
	}

	@GetMapping()
	public ResponseEntity<List<DeviceDTO>> findAll() {
		return ResponseEntity.ok(deviceService.getAll());
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<String> updateDevice(@PathVariable("id") String id, @RequestBody DeviceDTO deviceDTO) {
		try {
			deviceService.mergeEntity(id, deviceDTO);
			return ResponseEntity.ok("");
		} catch (DeviceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id) {
		deviceService.deleteDeviceById(id);
		return ResponseEntity.noContent().build();
	}
}
