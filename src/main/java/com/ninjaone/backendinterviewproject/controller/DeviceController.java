package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.security.AuthenticatedUser;
import com.ninjaone.backendinterviewproject.security.LoggedUser;
import com.ninjaone.backendinterviewproject.service.DeviceBO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	public static final String DEVICE_NOT_FOUND_OR_NOT_AUTHORIZED_ACCESS_MESSSAGE = "Device not found or you are not authorized to access it - deviceId=";
	private final DeviceBO deviceBO;

	public DeviceController(DeviceBO deviceBO) {
		this.deviceBO = deviceBO;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<DeviceDTO> findById(@LoggedUser AuthenticatedUser authUser, @PathVariable("id") Long id) {
		Device storedDevice = deviceBO.getById(id);
		if (storedDevice.getCustomer().getId().equals(authUser.getId())) {
			return ResponseEntity.ok(deviceBO.mapper().toDTO(storedDevice));
		}
		throw new InvalidRequestException(DEVICE_NOT_FOUND_OR_NOT_AUTHORIZED_ACCESS_MESSSAGE + id, null);
	}

	@GetMapping()
	public ResponseEntity<List<DeviceDTO>> findAll(@LoggedUser AuthenticatedUser authUser) {
		if (isAdmin(authUser)) {
			List<DeviceDTO> allDevices = getAllDevices();
			return ResponseEntity.ok(allDevices);
		}
		List<DeviceDTO> allDevicesFromAuthUser = getAllDevicesFrom(authUser);
		return ResponseEntity.ok(allDevicesFromAuthUser);
	}

	@PutMapping()
	public ResponseEntity<DeviceDTO> update(@LoggedUser AuthenticatedUser authUser, @RequestBody DeviceDTO deviceDTO) {
		Device storedDevice = deviceBO.getById(deviceDTO.getId());
		if (storedDevice.getCustomer().getId().equals(authUser.getId())) {
			Device updatedDevice = deviceBO.update(deviceDTO);
			return ResponseEntity.ok(deviceBO.mapper().toDTO(updatedDevice));
		}
		throw new InvalidRequestException(DEVICE_NOT_FOUND_OR_NOT_AUTHORIZED_ACCESS_MESSSAGE + deviceDTO.getId(), null);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@LoggedUser AuthenticatedUser authUser, @PathVariable("id") Long id) {
		Device storedDevice = deviceBO.getById(id);
		if (storedDevice.getCustomer().getId().equals(authUser.getId())) {
			deviceBO.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		throw new InvalidRequestException(DEVICE_NOT_FOUND_OR_NOT_AUTHORIZED_ACCESS_MESSSAGE + id, null);
	}

	private boolean isAdmin(AuthenticatedUser authUser) {
		return authUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
	}

	private List<DeviceDTO> getAllDevices() {
		return deviceBO.getAll().stream().map(deviceBO.mapper()::toDTO).collect(Collectors.toList());
	}

	private List<DeviceDTO> getAllDevicesFrom(AuthenticatedUser authUser) {
		return deviceBO.findAllByCustomerId(authUser.getId()).stream().map(deviceBO.mapper()::toDTO).collect(Collectors.toList());
	}
}
