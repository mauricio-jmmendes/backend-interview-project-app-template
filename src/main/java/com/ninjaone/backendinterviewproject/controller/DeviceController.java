package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.security.AuthenticatedUser;
import com.ninjaone.backendinterviewproject.security.LoggedUser;
import com.ninjaone.backendinterviewproject.service.DeviceBO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	private final DeviceBO deviceBO;

	public DeviceController(DeviceBO deviceBO) {
		this.deviceBO = deviceBO;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DeviceDTO> findById(@LoggedUser AuthenticatedUser authUser, @PathVariable("id") Long id) {
		boolean noneMatch = authUser.getCustomer().getDevices().stream().noneMatch(d -> d.getId().equals(id));
		if (noneMatch) {
			throw new InvalidRequestException("Device not found or you are not authorized to access it - deviceId=" + id, null);
		}
		Device storedDevice = deviceBO.getById(id);
		return ResponseEntity.ok(deviceBO.mapper().toDTO(storedDevice));
	}

	@GetMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<DeviceDTO>> findAll() {
		return ResponseEntity.ok(deviceBO.getAll().stream().map(deviceBO.mapper()::toDTO).collect(Collectors.toList()));
	}

	@PutMapping()
	public ResponseEntity<DeviceDTO> update(@LoggedUser AuthenticatedUser authUser, @RequestParam DeviceDTO deviceDTO) {
		boolean noneMatch = authUser.getCustomer().getDevices().stream().noneMatch(d -> Objects.equals(d.getId(), deviceDTO.getId()));
		if (noneMatch) {
			throw new InvalidRequestException("Device not found or you are not authorized to access it - deviceId=" + deviceDTO.getId(), null);
		}
		Device updatedDevice = deviceBO.update(deviceDTO);
		return ResponseEntity.ok(deviceBO.mapper().toDTO(updatedDevice));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		deviceBO.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
