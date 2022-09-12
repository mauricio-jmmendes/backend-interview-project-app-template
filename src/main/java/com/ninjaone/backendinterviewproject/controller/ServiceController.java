package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceOrderDTO;
import com.ninjaone.backendinterviewproject.security.AuthenticatedUser;
import com.ninjaone.backendinterviewproject.security.LoggedUser;
import com.ninjaone.backendinterviewproject.service.ServiceOrderBO;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
@RequestMapping("/services")
public class ServiceController {

	private final ServiceOrderBO serviceOrderBO;

	private final Function<ServiceOrder, ServiceOrderDTO> toDTO;

	public ServiceController(ServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
		this.toDTO = serviceOrderBO.mapper()::toDTO;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ServiceOrderDTO> findById(@PathVariable("id") Long id) {
		ServiceOrder serviceOrder = serviceOrderBO.getById(id);
		return ResponseEntity.ok(serviceOrderBO.mapper().toDTO(serviceOrder));
	}

	@GetMapping()
	public ResponseEntity<List<ServiceOrderDTO>> findAll(@LoggedUser AuthenticatedUser authUser) {
		if (isAdmin(authUser)) {
			return ResponseEntity.ok(getAllServiceOrders());
		}
		return ResponseEntity.ok(getAllServiceOrdersFrom(authUser));
	}

	@PutMapping()
	public ResponseEntity<ServiceOrderDTO> update(@LoggedUser AuthenticatedUser authUser, @RequestBody ServiceOrderDTO serviceOrderDTO) {
		ServiceOrder serviceOrder = serviceOrderBO.getById(serviceOrderDTO.getId());
		if (Objects.nonNull(serviceOrder) && Objects.equals(serviceOrder.getDevice().getCustomer().getId(), authUser.getId())) {
			ServiceOrder updatedServiceOrder = serviceOrderBO.update(serviceOrderDTO);
			return ResponseEntity.ok(serviceOrderBO.mapper().toDTO(updatedServiceOrder));
		}

			throw new InvalidRequestException("ServiceOrder not found or you are not authorized to access it - serviceId=" + serviceOrderDTO.getId(), null);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@LoggedUser AuthenticatedUser authUser, @PathVariable("id") Long id) {
		ServiceOrder serviceOrder = serviceOrderBO.getById(id);
		if (Objects.nonNull(serviceOrder) && Objects.equals(serviceOrder.getDevice().getCustomer().getId(), authUser.getId())) {
			serviceOrderBO.deleteById(id);
			return ResponseEntity.noContent().build();
		}

		throw new InvalidRequestException("ServiceOrder not found or you are not authorized to access it - serviceId=" + id, null);
	}

	private boolean isAdmin(AuthenticatedUser authUser) {
		return authUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
	}

	private List<ServiceOrderDTO> getAllServiceOrders() {
		return serviceOrderBO.getAll().stream().map(toDTO).collect(Collectors.toList());
	}

	private List<ServiceOrderDTO> getAllServiceOrdersFrom(AuthenticatedUser authUser) {
		List<Long> deviceIdList = authUser.getCustomer().getDevices().stream().map(Device::getId).collect(Collectors.toList());
		return serviceOrderBO.findAllByDeviceIdIn(deviceIdList).stream().map(toDTO).collect(Collectors.toList());
	}
}
